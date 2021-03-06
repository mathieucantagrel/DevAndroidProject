package com.example.projetmtg;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    Vector<Card> vector;
    Context context;

    public MyAdapter(Context context) {
        this.vector = new Vector<Card>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return vector.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i("adapter vet view", vector.get(position).getName());

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.text_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        textView.setText(vector.get(position).getName());

        return convertView;
    }

    public void add(Card card){
        vector.add(card);
        Log.i("add", card.getName());
    }

    public boolean checkDoublon(String checkName){
        for (Card card : this.vector) {
            if (card.getName().equals(checkName))
                return false;
        }
        return true;
    }
}
