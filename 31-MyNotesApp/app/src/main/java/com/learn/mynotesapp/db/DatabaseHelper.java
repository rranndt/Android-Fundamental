package com.learn.mynotesapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbnoteapp";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format(
            "CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME,
            DatabaseContract.NoteColumn._ID,
            DatabaseContract.NoteColumn.TITLE,
            DatabaseContract.NoteColumn.DESCRIPTION,
            DatabaseContract.NoteColumn.DATE
    );

    public DatabaseHelper(Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME);
        onCreate(db);
    }
}
