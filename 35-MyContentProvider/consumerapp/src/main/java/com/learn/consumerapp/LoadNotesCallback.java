package com.learn.consumerapp;

import com.learn.consumerapp.entity.Note;

import java.util.ArrayList;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public interface LoadNotesCallback {
    void preExecute();
    void postExecute(ArrayList<Note> notes);
}
