package com.learn.lastsubmission.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learn.lastsubmission.Model.UserMain;

import java.util.ArrayList;

import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.AVATAR;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.ID;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.TABLE_NAME;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.URL;
import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.USERNAME;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class GithubHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static GithubHelper INSTANCE;
    private static SQLiteDatabase database;
    private Activity mContext;

    public GithubHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static GithubHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GithubHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE,
                null,
                ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor getCount() {
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                USERNAME + " ASC",
                null);
        cursor.moveToFirst();
        return cursor;
    }

    public ArrayList<UserMain> getDataUser() {
        ArrayList<UserMain> userGithubList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                USERNAME + " ASC",
                null);
        cursor.moveToFirst();
        UserMain userGithub;
        if (cursor.getCount() > 0) {
            do {
                userGithub = new UserMain();
                userGithub.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                userGithub.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(USERNAME)));
                userGithub.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR)));
                userGithub.setHtmlUrl(cursor.getString(cursor.getColumnIndexOrThrow(URL)));
                userGithubList.add(userGithub);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return userGithubList;
    }

    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int deleteById(String id) {
        return database.delete(TABLE_NAME, ID + " ='" + id + "'", null);
    }

    public int deleteAll() {
        return database.delete(TABLE_NAME, null, null);
    }

    public int update(String id, ContentValues contentValues) {
        return database.update(TABLE_NAME, contentValues, ID + " =?", new String[]{id});
    }
}
