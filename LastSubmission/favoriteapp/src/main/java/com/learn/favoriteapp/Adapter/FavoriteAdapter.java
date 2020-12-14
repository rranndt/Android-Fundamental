package com.learn.favoriteapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.favoriteapp.Model.UserMain;
import com.learn.favoriteapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    private Cursor cursor;

    public FavoriteAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public UserMain getUserMain(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("position null");
        }
        return new UserMain(cursor);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        final UserMain userMain = getUserMain(position);
        Glide.with(holder.itemView.getContext())
                .load(userMain.getAvatarUrl())
                .into(holder.ivAvatar);
        holder.tvUsername.setText(userMain.getLogin());
        holder.tvHtmlUrl.setText(userMain.getHtmlUrl());
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private CircleImageView ivAvatar;
        private TextView tvUsername, tvHtmlUrl;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cvContainer);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvHtmlUrl = itemView.findViewById(R.id.tvHtmlUrl);
        }
    }
}
