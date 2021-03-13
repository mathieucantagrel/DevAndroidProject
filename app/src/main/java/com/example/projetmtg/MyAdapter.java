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

    Vector<String> vector;
    Context context;

    public MyAdapter(Context context) {
        this.vector = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return vector.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.text_layout, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.textView);

        textView.setText(vector.get(position));

        Log.i("adapter", "here");

        return convertView;
    }

    public void add(String name){
        vector.add(name);
        Log.i("add", name);
    }
}
