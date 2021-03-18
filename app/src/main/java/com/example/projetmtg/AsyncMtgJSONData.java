package com.example.projetmtg;

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

public class AsyncMtgJSONData extends AsyncTask<String, Void, JSONObject> {

    MyAdapter adapter;
    String[] colorFilter;
    String[] colorIdentityFilter;

    public AsyncMtgJSONData(MyAdapter adapter, String[] colorFilter, String[] colorIdentityFilter) {
        this.adapter = adapter;
        this.colorFilter = colorFilter;
        this.colorIdentityFilter = colorIdentityFilter;
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
            
            for (int i=0; i<jsonArray.length(); i++){
                addCard(jsonArray.getJSONObject(i));
            }

            adapter.notifyDataSetChanged();

            Log.i("postURL", "notified");

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

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

    private void addCard(JSONObject current) throws JSONException {
        String name = current.getString("name");

        if (!adapter.checkDoublon(name))
            return;

        String manaCost = current.has("manaCost")?current.getString("manaCost"):"";
        int cmc = current.getInt("cmc");

        String[] colors = current.has("colors")?createTab(current.getJSONArray("colors")):null;
        if (colors!=null){
            if (!CheckFilter(colorFilter, colors))
                return;
        }

        String[] colorIdentity = current.has("colorIdentity")?createTab(current.getJSONArray("colorIdentity")):null;
        if (colorIdentity!=null){
            if (!CheckFilter(colorIdentityFilter, colorIdentity))
                return;
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

        Boolean isFavorite = false;

        Card card = new Card(name, manaCost, cmc, colors, colorIdentity, superTypes, types, subtypes, rarity, setName, text, flavor, power, toughness, imageURL, legalities, rules, id, isFavorite);

        adapter.add(card);
    }

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

    private boolean CheckFilter(String[] filter, String[] colors){

        if (filter[0]==null)
            return true;

        boolean check;

        for (String colorToCheck : colors) {
            check = false;

            for (String color : filter) {

                if (color.equals(""))
                    continue;

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

    private String safeURL(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(4, 's');
        return stringBuilder.toString();
    }

    private Rule[] createRulesTab(JSONArray jsonArray) throws JSONException {
        Rule[] tab = new Rule[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tab[i] = new Rule(jsonObject.getString("date"), jsonObject.getString("text"));
        }

        return tab;
    }

    private String[] createLegalitiesTab(JSONArray jsonArray) throws JSONException {
        String[] tab = new String[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            tab[i] = jsonObject.getString("format");
        }

        return tab;
    }
}
