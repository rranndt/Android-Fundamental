package com.learn.mynotesapp.helper;

import android.database.Cursor;

import com.learn.mynotesapp.db.DatabaseContract;
import com.learn.mynotesapp.entity.Note;

import java.util.ArrayList;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class MappingHelper {

    public static ArrayList<Note> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Note> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumn._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumn.TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumn.DESCRIPTION));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumn.DATE));
            notesList.add(new Note(id, title, description, date));
        }

        return notesList;
    }
}
