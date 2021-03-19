package com.example.projetmtg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListFavorite extends AppCompatActivity {

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        adapter = new MyAdapter(getBaseContext());

        ListView listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        String querry = "https://api.magicthegathering.io/v1/cards?id=";

        FavCardsDml favCardsDml = new FavCardsDml(getApplicationContext());

        for (String id : favCardsDml.getAllFavCards()) {
            AsyncMTGDL(querry+id);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) adapter.getItem(position);
                Log.i("card", card.getName());
                Intent intent = new Intent(getApplicationContext(), ShowCardInfo.class);
                intent.putExtra("card", card);
                startActivity(intent);
            }
        });
    }

    protected void AsyncMTGDL(String querry){
        String[] filters = new String[5];

        new AsyncMtgJSONData(adapter, filters, filters, getApplicationContext()).execute(querry);
    }
}