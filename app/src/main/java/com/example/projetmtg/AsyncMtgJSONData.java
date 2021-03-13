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
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncMtgJSONData extends AsyncTask<String, Void, JSONObject> {

    MyAdapter adapter;

    public AsyncMtgJSONData(MyAdapter adapter) {
        this.adapter = adapter;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
                String name = jsonArray.getJSONObject(i).getString("name");
                String manaCost = jsonArray.getJSONObject(i).getString("manaCost");
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
}
