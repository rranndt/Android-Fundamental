package com.learn.lastsubmission.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.learn.lastsubmission.Const;
import com.learn.lastsubmission.Model.UserMain;
import com.learn.lastsubmission.R;
import com.learn.lastsubmission.Ui.Activity.Detail.DetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserMainViewHolder> {

    private Activity mActivity;
    private List<UserMain> userMainList;

    public UserAdapter(Activity mContext, List<UserMain> userMainList) {
        this.mActivity = mContext;
        this.userMainList = userMainList;
    }

    @NonNull
    @Override
    public UserMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMainViewHolder holder, int position) {
        holder.bind(userMainList.get(position));
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMain userMain = userMainList.get(holder.getAdapterPosition());
                Intent intentDetail = new Intent(mActivity, DetailActivity.class);
                intentDetail.putExtra(Const.PARCELABLE, userMain);
                v.getContext().startActivity(intentDetail);
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userMainList.size();
    }

    public class UserMainViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContainer;
        private CircleImageView ivAvatar;
        private TextView tvUsername, tvHtmlUrl;

        public UserMainViewHolder(@NonNull View itemView) {
            super(itemView);

            cvContainer = itemView.findViewById(R.id.cvContainer);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvHtmlUrl = itemView.findViewById(R.id.tvHtmlUrl);
        }

        private void bind(UserMain userMain) {
            Glide.with(itemView.getContext())
                    .load(userMain.getAvatarUrl())
                    .into(ivAvatar);
            tvUsername.setText(userMain.getLogin());
            tvHtmlUrl.setText(userMain.getHtmlUrl());
        }
    }

    public void setData(List<UserMain> setData) {
        this.userMainList = setData;
        notifyDataSetChanged();
    }

    public void clearList(List<UserMain> clearList) {
        this.userMainList = clearList;
        this.userMainList.clear();
        notifyDataSetChanged();
    }
}
