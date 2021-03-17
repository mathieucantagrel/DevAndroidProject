package com.example.projetmtg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;



        try {
            url = new URL(strings[0]);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); //connection to the url to download the image
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(in); //converting the response to a bitmap image

                return bm;
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.i("download", "done");
    }
}