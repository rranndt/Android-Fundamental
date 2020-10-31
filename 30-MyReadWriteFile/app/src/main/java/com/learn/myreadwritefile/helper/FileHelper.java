package com.learn.myreadwritefile.helper;

import android.content.Context;
import android.util.Log;

import com.learn.myreadwritefile.model.FileModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {

    private static final String TAG = "FileHelper";

    public static void writeToFile(FileModel fileModel, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileModel.getFilename(),Context.MODE_PRIVATE));
            outputStreamWriter.write(fileModel.getData());
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed : ", e);
        }
    }

    public static FileModel readFromFile(Context context, String filename) {
        FileModel fileModel = new FileModel();

        try {
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                fileModel.setData(stringBuilder.toString());
                fileModel.setFilename(filename);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found : ", e);
        } catch (IOException e) {
            Log.e(TAG, "Cannot read file : ", e);
        }

        return fileModel;
    }
}
