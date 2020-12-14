package com.learn.lastsubmission.Ui.Activity.Favorite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.lastsubmission.Adapter.FavoriteAdapter;
import com.learn.lastsubmission.Adapter.UserAdapter;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Ui.Activity.Setting.SettingActivity;
import com.learn.lastsubmission.Database.GithubHelper;

import java.util.ArrayList;
import java.util.List;

import static com.learn.lastsubmission.Database.DatabaseContract.GithubColumn.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    private Cursor listUser;
    private TextView tvNoData;

    private RecyclerView rvUser;
    private GithubHelper githubHelper;
    private List<UserMain> userMainList = new ArrayList<>();
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        githubHelper = new GithubHelper(getApplicationContext());
        githubHelper.open();
        userMainList = githubHelper.getDataUser();
        initViews();
        setRecyclerView();

        if (savedInstanceState == null) {
            new LoadFavoriteItem().execute();
        }
    }

    private void initViews() {
        tvNoData = findViewById(R.id.tvNoData);
        rvUser = findViewById(R.id.rvUser);
    }

    private void setRecyclerView() {
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);
        rvUser.smoothScrollToPosition(0);
        adapter = new FavoriteAdapter(getApplicationContext(), listUser);
        rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class LoadFavoriteItem extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            listUser = cursor;
            adapter.setCursor(listUser);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnBack(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnSettingFavorite(View view) {
        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void btnDeleteAll(View view) {
        if (githubHelper.getCount().getCount() > 0) {
            alertDeleteAll();
        } else {
            Toast.makeText(this, "No Data Yet", Toast.LENGTH_SHORT).show();
        }
    }

    private void alertDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove All Favorite");
        builder.setMessage("Are you sure ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                githubHelper.deleteAll();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}