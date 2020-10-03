package com.learn.mylistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.mylistview.R;
import com.learn.mylistview.model.Hero;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HeroAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Hero> heroes = new ArrayList<>();

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public HeroAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return heroes.size();
    }

    @Override
    public Object getItem(int position) {
        return heroes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_list_hero, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Hero hero = (Hero) getItem(position);
        viewHolder.bind(hero);
        return itemView;
    }

    private class ViewHolder {
        private TextView tvName,
                tvDescription;
        private CircleImageView imgPhoto;

        ViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvDescription = view.findViewById(R.id.tv_description);
            imgPhoto = view.findViewById(R.id.img_photo);
        }

        void bind(Hero hero) {
            tvName.setText(hero.getName());
            tvDescription.setText(hero.getDescription());
            imgPhoto.setImageResource(hero.getPhoto());
        }
    }
}
