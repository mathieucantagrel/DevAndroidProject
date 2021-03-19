package com.example.projetmtg;

import android.provider.BaseColumns;

public class FavCards {

    private FavCards(){}

    public static class favEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_NAME_FAVCARD = "favcard";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + favEntry.TABLE_NAME + " ("+
                        favEntry._ID + "INTEGER PRIMARY KEY," +
                        favEntry.COLUMN_NAME_FAVCARD + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + favEntry.TABLE_NAME;
    }

}
