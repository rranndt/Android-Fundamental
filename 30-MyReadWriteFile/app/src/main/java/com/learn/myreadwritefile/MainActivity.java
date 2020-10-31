package com.learn.myreadwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.learn.myreadwritefile.databinding.ActivityMainBinding;
import com.learn.myreadwritefile.helper.FileHelper;
import com.learn.myreadwritefile.model.FileModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnNew.setOnClickListener(this);
        binding.btnOpen.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnNew:
                newFile();
                break;
            case R.id.btnOpen:
                showList();
                break;
            case R.id.btnSave:
                saveFile();
                break;
        }
    }

    private void newFile() {
        binding.edtTitle.setText("");
        binding.edtContent.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getFilesDir().list());
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang dingginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this,title);
        binding.edtTitle.setText(fileModel.getFilename());
        binding.edtContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    private void saveFile() {
        if (binding.edtTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (binding.edtContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Content harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = binding.edtTitle.getText().toString();
            String text = binding.edtContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving", Toast.LENGTH_SHORT).show();
        }
    }
}