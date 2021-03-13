package com.example.projetmtg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class List extends AppCompatActivity {

    MyAdapter adapter;
    String querry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        querry = getIntent().getExtras().getString("querry");
        Log.i("querry", querry);

        adapter = new MyAdapter(getBaseContext());

        ListView listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        AsyncMTGDl();
    }

    protected void AsyncMTGDl(){

        new AsyncMtgJSONData(adapter).execute("https://api.magicthegathering.io/v1/cards"+querry);
    }
}