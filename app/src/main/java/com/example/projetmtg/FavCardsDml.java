package com.example.projetmtg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class FavCardsDml {

    private FavCardsDbHelper favCardsDbHelper;
    private SQLiteDatabase db;

    public FavCardsDml(Context context) {
        favCardsDbHelper = new FavCardsDbHelper(context);
        db = favCardsDbHelper.getWritableDatabase();
    }

    public void closeConnection() {
        favCardsDbHelper.close();
    }

    public void addLine(String card){

        ContentValues values = new ContentValues();
        values.put(FavCards.favEntry.COLUMN_NAME_FAVCARD, card);

        long newRowId = db.insert(FavCards.favEntry.TABLE_NAME, null, values);
    }

    public ArrayList<String> getAllFavCards(){

        Cursor cursor = db.query(
                FavCards.favEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<String> favCards = new ArrayList<>();

        while (cursor.moveToNext()){
            String card = cursor.getString(cursor.getColumnIndexOrThrow(FavCards.favEntry.COLUMN_NAME_FAVCARD));
            favCards.add(card);
        }

        cursor.close();
        return favCards;
    }

    public void deleteFilteredTableContent(String... filter){

        String selection = FavCards.favEntry.COLUMN_NAME_FAVCARD + " LIKE ?";

        int deletedRows = db.delete(FavCards.favEntry.TABLE_NAME, selection, filter);
        Log.i("update db", "Deleted: " + deletedRows);
    }

    public void deleteAllTableContent(){
        int deletedRows = db.delete(FavCards.favEntry.TABLE_NAME, null, null);
        Log.i("update db", "Deleted: "+deletedRows);
    }
}
