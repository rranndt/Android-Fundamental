package com.learn.favoriteapp.Ui.Activity.Favorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.favoriteapp.Adapter.FavoriteAdapter;
import com.learn.favoriteapp.Model.UserMain;
import com.learn.favoriteapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.learn.favoriteapp.Database.DatabaseContract.GithubColumn.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    private Cursor listUser;
    private TextView tvNoData;

    private RecyclerView rvUser;
    private List<UserMain> userMainList = new ArrayList<>();
    private FavoriteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

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
}