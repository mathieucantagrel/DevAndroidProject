package com.example.projetmtg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class List extends AppCompatActivity {

    MyAdapter adapter;
    String querry;
    String[] colorFilter;
    String[] colorIdentityFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        querry = getIntent().getExtras().getString("querry"); // getting the qurry to send to the api
        Log.i("querry", querry);
        colorFilter = (String[]) getIntent().getExtras().get("colorFilter"); //getting the color filter
        colorIdentityFilter = (String[]) getIntent().getExtras().get("colorIdentityFilter"); //getting the color identity

        adapter = new MyAdapter(getBaseContext());

        ListView listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        AsyncMTGDl(); // seting up the layout with the result of the api


//        set up the onclick to display the information about the card
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

    protected void AsyncMTGDl(){
        new AsyncMtgJSONData(adapter, colorFilter, colorIdentityFilter, getApplicationContext()).execute("https://api.magicthegathering.io/v1/cards"+querry);
    }
}