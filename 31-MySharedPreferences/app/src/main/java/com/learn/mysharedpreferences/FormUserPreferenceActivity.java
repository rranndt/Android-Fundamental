package com.learn.mysharedpreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.learn.mysharedpreferences.model.UserModel;

import org.w3c.dom.Text;

public class FormUserPreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtName, edtEmail, edtPhone, edtAge;
    private RadioGroup rgLoveMu;
    private RadioButton rbYes, rbNo;
    private Button btnSave;

    public static final String EXTRA_TYPE_FORM = "extra_type_form";
    public static final String EXTRA_RESULT = "extra_result";
    public static final int RESULT_CODE = 101;

    public static final int TYPE_ADD = 1;
    public static final int TYPE_EDIT = 2;

    private final String FIELD_REQUIRED = "Field tidak boleh kosong";
    private final String FIELD_DIGIT_ONLY = "Hanya boleh terisi numberik";
    private final String FIELD_IS_NOT_VALID = "Email tidak valid";

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user_preference);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAge = findViewById(R.id.edtAge);
        rgLoveMu = findViewById(R.id.rgLoveMu);
        rbYes = findViewById(R.id.rbYes);
        rbNo = findViewById(R.id.rbNo);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

        // Get Data From Intent
        userModel = getIntent().getParcelableExtra("USER");
        int formType = getIntent().getIntExtra(EXTRA_TYPE_FORM, 0);

        String actionBarTitle = "";
        String btnTitle = "";

        switch (formType) {
            case TYPE_ADD:
                actionBarTitle = "Tambah Baru";
                btnTitle = "Simpan";
                break;
            case TYPE_EDIT:
                actionBarTitle = "Ubah";
                btnTitle = "Update";
                showPreferenceInForm();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        btnSave.setText(btnTitle);

    }

    // Change data from intent to View
    private void showPreferenceInForm() {
        edtName.setText(userModel.getName());
        edtEmail.setText(userModel.getEmail());
        edtAge.setText(String.valueOf(userModel.getAge()));
        edtPhone.setText(userModel.getPhoneNumber());
        if (userModel.isLove()) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String age = edtAge.getText().toString().trim();
            String phoneNumber = edtPhone.getText().toString().trim();
            boolean isLoveMu = rgLoveMu.getCheckedRadioButtonId() == R.id.rbYes;

            if (TextUtils.isEmpty(name)) {
                edtName.setError(FIELD_REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(email)) {
                edtEmail.setError(FIELD_IS_NOT_VALID);
                return;
            }

            if (!isValidEmail(email)) {
                edtEmail.setError(FIELD_IS_NOT_VALID);
                return;
            }

            if (TextUtils.isEmpty(age)) {
                edtAge.setError(FIELD_REQUIRED);
                return;
            }

            if (TextUtils.isEmpty(phoneNumber)) {
                edtPhone.setError(FIELD_DIGIT_ONLY);
                return;
            }

            saveUser(name, email, age, phoneNumber, isLoveMu);
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_RESULT, userModel);
            setResult(RESULT_CODE, resultIntent);
            finish();
        }
    }

    private void saveUser(String name, String email, String age, String phone, boolean isLoveMu) {
        UserPreference userPreference = new UserPreference(this);

        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setAge(Integer.parseInt(age));
        userModel.setPhoneNumber(phone);
        userModel.setLove(isLoveMu);

        userPreference.setUser(userModel);
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}