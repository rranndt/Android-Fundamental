package com.learn.mypreloaddata.database;

import android.provider.BaseColumns;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class DatabaseContract {

    public static final String TABLE_NAME = "table_mahasiswa";

    public static final class MahasiswaColumns implements BaseColumns {
        public static String NAMA = "nama";
        public static String NIM = "nim";
    }
}
