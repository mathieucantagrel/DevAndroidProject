package com.example.projetmtg;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncMtgJSONData extends AsyncTask<String, Void, JSONObject> {

    MyAdapter adapter;
    String[] colorFilter;
    String[] colorIdentityFilter;
    ArrayList<String> favorites;

    public AsyncMtgJSONData(MyAdapter adapter, String[] colorFilter, String[] colorIdentityFilter, Context context) {
        this.adapter = adapter;
        this.colorFilter = colorFilter;
        this.colorIdentityFilter = colorIdentityFilter;
        FavCardsDml favCardsDml = new FavCardsDml(context);
        favorites = favCardsDml.getAllFavCards();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //connecting to the url to get the json data
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                return new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        try{

            JSONArray jsonArray = jsonObject.getJSONArray("cards");

            Log.i("postURL", String.valueOf(jsonArray.length()));

//            parsing the json to create a card
            for (int i=0; i<jsonArray.length(); i++){
                addCard(jsonArray.getJSONObject(i));
            }

            adapter.notifyDataSetChanged(); //refesh the adapter to change the layout

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

//    read the InputStream and convert it into a string
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

//    parsing the json and create a card object and add it to the vector of the adaptor
    private void addCard(JSONObject current) throws JSONException {

        String name = current.getString("name");

//        we do not want any doublon in the vector
        if (!adapter.checkDoublon(name))
            return;

        String manaCost = current.has("manaCost")?current.getString("manaCost"):"";
        int cmc = current.getInt("cmc");

        String[] colors = current.has("colors")?createTab(current.getJSONArray("colors")):null;
        if (colors!=null){
//            check the filter in order to correspond to what the user want in term of color in the card
            if (!CheckFilter(colorFilter, colors)) {
                Log.i("add card", "color");
                return;
            }
        }

        String[] colorIdentity = current.has("colorIdentity")?createTab(current.getJSONArray("colorIdentity")):null;
        if (colorIdentity!=null){
//            check the filter in order to correspond to what the user want in term of color identity in the card
            if (!CheckFilter(colorIdentityFilter, colorIdentity)) {
                Log.i("add card", "color identity");
                return;
            }
        }

        String[] superTypes = current.has("supertypes")?createTab(current.getJSONArray("supertypes")):null;

        String[] types = createTab(current.getJSONArray("types"));

        String[] subtypes = current.has("subtypes")?createTab(current.getJSONArray("subtypes")):null;

        String rarity = current.getString("rarity");
        String setName = current.getString("setName");

        String text = current.has("text")?current.getString("text"):"";

        String flavor = current.has("flavor")?current.getString("flavor"):"";

        String power = current.has("power")?current.getString("power"):"";

        String toughness = current.has("toughness")?current.getString("toughness"):"";

        String imageURL = current.has("imageUrl")?safeURL(current.getString("imageUrl")):"";

        String[] legalities = current.has("legalities")?createLegalitiesTab(current.getJSONArray("legalities")):null;

        Rule[] rules = current.has("rulings")? createRulesTab(current.getJSONArray("rulings")):null;

        String id = current.getString("id");

        Card card = new Card(name, manaCost, cmc, colors, colorIdentity, superTypes, types, subtypes, rarity, setName, text, flavor, power, toughness, imageURL, legalities, rules, id);

        adapter.add(card);
    }

//    convert a jsonarray to an array of string
    private String[] createTab(JSONArray jsonArray){
        try{
        String[] tab = new String[jsonArray.length()];
            for (int j=0; j<jsonArray.length(); j++) {
                tab[j] = (String) jsonArray.get(j);
            }

            return tab;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

//    check the filter
    private boolean CheckFilter(String[] filter, String[] colors){

        if (filter[0]==null)
            return true;

        boolean check;

        for (String colorToCheck : colors) {
            check = false;

            for (String color : filter) {

                if (color.equals("")) {
                    continue;
                }

                if (colorToCheck.equals(color)){
                    check=true;
                    break;
                }
            }

            if (!check){
                return false;
            }
        }

        return true;
    }

//    convert the http to https
    private String safeURL(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(4, 's');
        return stringBuilder.toString();
    }

//    convert a jsonarray to an array of rule
    private Rule[] createRulesTab(JSONArray jsonArray) throws JSONException {
        Rule[] tab = new Rule[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tab[i] = new Rule(jsonObject.getString("date"), jsonObject.getString("text"));
        }

        return tab;
    }

//    convert a jsonarray to an array of string
    private String[] createLegalitiesTab(JSONArray jsonArray) throws JSONException {
        String[] tab = new String[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tab[i] = jsonObject.getString("format");
        }

        return tab;
    }
}
