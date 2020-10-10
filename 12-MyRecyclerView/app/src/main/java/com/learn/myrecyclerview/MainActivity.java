package com.learn.myrecyclerview;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.myrecyclerview.adapter.CardViewHeroAdapter;
import com.learn.myrecyclerview.adapter.GridHeroAdapter;
import com.learn.myrecyclerview.adapter.ListHeroAdapter;
import com.learn.myrecyclerview.model.Hero;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Hero> list = new ArrayList<>();

    private String title = "Mode List";

    // Saved Instance
    private final String STATE_TITLE = "state_string";
    private final String STATE_LIST = "state_list";
    private final String STATE_MODE = "state_mode";
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_heroes);
        recyclerView.setHasFixedSize(true);


        if (savedInstanceState == null) {
            setActionBarTitle(title);
            list.addAll(getListHeroes());
            showRecyclerList();
            mode = R.id.action_list;
        } else {
            title = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Hero> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            int stateMode = savedInstanceState.getInt(STATE_MODE);

            setActionBarTitle(title);
            if (stateList != null) {
                list.addAll(stateList);
            }
            setMode(stateMode);
        }

        setActionBarTitle(title);
    }

    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public ArrayList<Hero> getListHeroes() {
        String[] dataName = getResources().getStringArray(R.array.data_name);
        String[] dataDescription = getResources().getStringArray(R.array.data_description);
        String[] dataPhoto = getResources().getStringArray(R.array.data_photo);

        ArrayList<Hero> listHero = new ArrayList<>();
        for (int i = 0; i < dataName.length; i++) {
            Hero hero = new Hero();
            hero.setName(dataName[i]);
            hero.setDescription(dataDescription[i]);
            hero.setPhoto(dataPhoto[i]);

            listHero.add(hero);
        }
        return listHero;
    }

    private void showRecyclerList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListHeroAdapter listHeroAdapter = new ListHeroAdapter(list);
        recyclerView.setAdapter(listHeroAdapter);

        listHeroAdapter.setOnItemClickCallback(new OnItemClickCallback() {
            @Override
            public void onItemClicked(Hero hero) {
                showSelectedHero(hero);
            }
        });
    }

    private void showRecyclerGrid() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        GridHeroAdapter gridHeroAdapter = new GridHeroAdapter(list);
        recyclerView.setAdapter(gridHeroAdapter);

        gridHeroAdapter.setOnItemClickCallback(new OnItemClickCallback() {
            @Override
            public void onItemClicked(Hero hero) {
                showSelectedHero(hero);
            }
        });
    }

    private void showRecyclerCardView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CardViewHeroAdapter cardViewHeroAdapter = new CardViewHeroAdapter(list);
        recyclerView.setAdapter(cardViewHeroAdapter);
    }

    private void showSelectedHero(Hero hero) {
        Toast.makeText(this, "Kamu memilih " + hero.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_LIST, title);
        outState.putParcelableArrayList(STATE_LIST, list);
        outState.putInt(STATE_MODE, mode);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.action_list:
                title = "Mode List";
                showRecyclerList();
                break;
            case R.id.action_grid:
                title = "Mode Grid";
                showRecyclerGrid();
                break;
            case R.id.action_card_view:
                title = "Mode CardView";
                showRecyclerCardView();
                break;
        }
        mode = selectedMode;
        setActionBarTitle(title);
    }
}