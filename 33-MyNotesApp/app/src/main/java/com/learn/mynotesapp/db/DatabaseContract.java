package com.learn.mynotesapp.db;

import android.provider.BaseColumns;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseContract {

    public static String TABLE_NAME = "note";

    public static final class NoteColumn implements BaseColumns {

        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";

    }
}
