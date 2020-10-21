package com.learn.secondsubmission.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.learn.secondsubmission.OnItemClick;
import com.learn.secondsubmission.R;
import com.learn.secondsubmission.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListUserSearchAdapter extends RecyclerView.Adapter<ListUserSearchAdapter.ListViewSearchHolder> {

    private List<User> mUser;
    private Activity mActivity;
    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public ListUserSearchAdapter(List<User> mUser, Activity mActivity) {
        this.mUser = mUser;
        this.mActivity = mActivity;
    }

    public void setData(List<User> mUser) {
        this.mUser = mUser;
        notifyDataSetChanged();
    }

    public void clearList(List<User> clearListUser) {
        this.mUser = clearListUser;
        this.mUser.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewSearchHolder holder, int position) {
        holder.bind(mUser.get(position));
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mActivity, mUser.get(holder.getAdapterPosition()).getName(),
//                        Toast.LENGTH_SHORT).show();
                onItemClick.onItemClicked(mUser.get(holder.getAdapterPosition()),position, v);
//                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_userDetailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ListViewSearchHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private CircleImageView ivAvatar;
        private TextView tvUsername,
                tvHtmlUrl;

        public ListViewSearchHolder(@NonNull View itemView) {
            super(itemView);

            cvContainer = itemView.findViewById(R.id.item_container);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvUsername = itemView.findViewById(R.id.tv_username);
            tvHtmlUrl = itemView.findViewById(R.id.tv_html_url);
        }

        private void bind(User user) {
            Glide.with(itemView.getContext())
                    .load(user.getAvatarUrl())
                    .apply(new RequestOptions().override(55,55))
                    .into(ivAvatar);
            tvUsername.setText(user.getName());
            tvHtmlUrl.setText(user.getUrl());
        }
    }
}
