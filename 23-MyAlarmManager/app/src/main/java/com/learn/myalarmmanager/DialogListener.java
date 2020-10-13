package com.learn.myalarmmanager;

public interface DialogListener {
    void onDialogDateSet(String tag, int year, int month, int dayOfTheMonth);
    void onDialogTimeSet(String tag, int hourOfDay, int minute);
}
