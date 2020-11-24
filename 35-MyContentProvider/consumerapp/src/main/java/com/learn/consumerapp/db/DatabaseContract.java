package com.learn.consumerapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseContract {

    public static final String AUTHORITY = "com.learn.mycontentprovider";
    private static final String SCHEME = "content";

    public static String TABLE_NAME = "note";

    public DatabaseContract() {
    }

    public static final class NoteColumn implements BaseColumns {

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";


        // untuk membuat URI content://com.dicoding.picodiploma.mynotesapp/note
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
