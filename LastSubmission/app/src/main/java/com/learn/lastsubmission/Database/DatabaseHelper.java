package com.learn.lastsubmission.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.TABLE_NAME;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbgithub";
    private static final int DB_VERSION = 3;

    private static final String SQL_CREATE_TABLE_USER = String.format(
            "CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            TABLE_NAME,
            DatabaseContract.GithubColumn.ID,
            DatabaseContract.GithubColumn.AVATAR,
            DatabaseContract.GithubColumn.USERNAME,
            DatabaseContract.GithubColumn.URL
    );

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
