package com.learn.firstsubmission.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn.firstsubmission.R;
import com.learn.firstsubmission.activity.DetailActivity;
import com.learn.firstsubmission.model.User;
import com.learn.firstsubmission.network.UrlServer;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private User user;
    private Activity activity;

    public UserAdapter(List<User> userList, Activity activity) {
        this.userList = userList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
        holder.container.setOnClickListener(v -> {
            user = userList.get(position);
//            Toast.makeText(activity, userList.get(holder.getAdapterPosition()).getLogin(),
//                    Toast.LENGTH_SHORT).show();
            Intent intentDetail = new Intent(activity, DetailActivity.class);
            intentDetail.putExtra(UrlServer.PARCEL, user);
            activity.startActivity(intentDetail);
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivAvatar;
        private TextView tvUsername,
                tvUrl;
        private CardView container;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvUrl = itemView.findViewById(R.id.tv_url);
            container = itemView.findViewById(R.id.item_container);
        }

        private void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .apply(new RequestOptions().override(55,55))
                    .into(ivAvatar);
            tvUsername.setText(user.getLogin());
            tvUrl.setText(user.getHtmlUrl());
        }
    }
}
