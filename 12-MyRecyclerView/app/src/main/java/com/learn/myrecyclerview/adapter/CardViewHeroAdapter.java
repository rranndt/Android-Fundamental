package com.learn.myrecyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn.myrecyclerview.R;
import com.learn.myrecyclerview.model.Hero;

import java.util.ArrayList;

public class CardViewHeroAdapter extends RecyclerView.Adapter<CardViewHeroAdapter.CardViewHolder> {

    private ArrayList<Hero> listHero;

    public CardViewHeroAdapter(ArrayList<Hero> listHero) {
        this.listHero = listHero;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_hero, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder holder, int position) {

        Hero hero = listHero.get(position);

        Glide.with(holder.itemView.getContext())
                .load(hero.getPhoto())
                .apply(new RequestOptions().override(350,350))
                .into(holder.imgPhoto);
        holder.tvName.setText(hero.getName());
        holder.tvDescription.setText(hero.getDescription());

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Favorite" +
                        listHero.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Share" +
                        listHero.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " + listHero.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listHero.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgPhoto;
        private TextView tvName, tvDescription;
        private Button btnFavorite, btnShare;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            btnFavorite = itemView.findViewById(R.id.btn_set_favorite);
            btnShare = itemView.findViewById(R.id.btn_set_share);
        }
    }
}
