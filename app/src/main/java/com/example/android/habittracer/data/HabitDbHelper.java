package com.example.android.habittracer.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracer.data.HabitContract.HabitEntry;


public class HabitDbHelper extends SQLiteOpenHelper {


        public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();
       private static final String DATABASE_NAME = "info.db";
                private static final int DATABASE_VERSION = 1;
                public HabitDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String SQL_CREATE_ACTIVITIES_TABLE =  "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                    + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HabitEntry.COLUMN_ACTIVITY_DESCRIPTION + " TEXT NOT NULL, "
                    + HabitEntry.COLUMN_ACTIVITY_CATEGORY + " INTEGER NOT NULL, "
                    + HabitEntry.COLUMN_ACTIVITY_MOMENT + " INTEGER NOT NULL, "
                    + HabitEntry.COLUMN_ACTIVITY_DURATION + " INTEGER NOT NULL DEFAULT 1, "
                    + HabitEntry.COLUMN_ACTIVITY_PLACE + " TEXT NOT NULL);";
            db.execSQL(SQL_CREATE_ACTIVITIES_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
