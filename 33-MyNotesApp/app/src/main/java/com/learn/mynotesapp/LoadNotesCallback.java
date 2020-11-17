package com.learn.mynotesapp;

import com.learn.mynotesapp.entity.Note;

import java.util.ArrayList;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Note> notes);
}
