package com.learn.secondsubmissionwithoutnavhost.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserViewHolder> {

    private List<User> userList;
    private Activity mActivity;

    public ListUserAdapter(List<User> userList, Activity mActivity) {
        this.userList = userList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
        holder.cvContainer.setOnClickListener(v ->
                onItemClickCallback.onItemClicked(userList.get(holder.getAdapterPosition()), position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private CircleImageView ivAvatar;
        private TextView tvUsername,
                tvHtmlUrl;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            cvContainer = itemView.findViewById(R.id.cv_container);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvUsername = itemView.findViewById(R.id.tv_repo_desc);
            tvHtmlUrl = itemView.findViewById(R.id.tv_repo_language);
        }

        private void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .apply(new RequestOptions().override(55, 55))
                    .into(ivAvatar);
            tvUsername.setText(user.getName());
            tvHtmlUrl.setText(user.getUrl());
        }
    }

    public void setData(List<User> mUser) {
        this.userList = mUser;
        notifyDataSetChanged();
    }

    public void clearList(List<User> clearList) {
        this.userList = clearList;
        this.userList.clear();
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(User user, int posistion);
    }
}
