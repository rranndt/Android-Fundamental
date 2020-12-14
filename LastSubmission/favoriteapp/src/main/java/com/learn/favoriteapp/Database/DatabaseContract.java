package com.learn.favoriteapp.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseContract {

    public static final String AUTHORITY = "com.learn.lastsubmission";
    private static final String SCHEME = "content";

    public DatabaseContract() {
    }

    public static final class GithubColumn implements BaseColumns {
        public static final String TABLE_NAME = "github";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String URL = "url";
        public static final String AVATAR = "avatar";

        // URI content
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static int columnInt(Cursor cursor, String  columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static String columnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

}
