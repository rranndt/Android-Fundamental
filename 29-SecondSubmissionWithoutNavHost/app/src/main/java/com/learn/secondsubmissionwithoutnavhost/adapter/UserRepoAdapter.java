package com.learn.secondsubmissionwithoutnavhost.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.secondsubmissionwithoutnavhost.R;
import com.learn.secondsubmissionwithoutnavhost.model.UserRepo;

import java.util.List;

public class UserRepoAdapter extends RecyclerView.Adapter<UserRepoAdapter.UserRepoViewHolder> {

    private List<UserRepo> mUserRepo;
    private Activity mActivity;

    public UserRepoAdapter(List<UserRepo> mUserRepo, Activity mActivity) {
        this.mUserRepo = mUserRepo;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public UserRepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_repo, parent, false);
        return new UserRepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRepoViewHolder holder, int position) {
        holder.bind(mUserRepo.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserRepo.size();
    }

    public class UserRepoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRepoName,
                tvRepoDesc,
                tvRepoLang;

        public UserRepoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
            tvRepoDesc = itemView.findViewById(R.id.tv_repo_desc);
            tvRepoLang = itemView.findViewById(R.id.tv_repo_language);
        }

        private void bind(UserRepo userRepo) {
            tvRepoName.setText(userRepo.getName());

            if (userRepo.getDescription() == null && userRepo.getLanguage() == null) {
                tvRepoDesc.setVisibility(View.GONE);
                tvRepoLang.setVisibility(View.GONE);
            } else if (userRepo.getDescription() == null || userRepo.getLanguage() == null) {
                if (userRepo.getDescription() == null) {
                    tvRepoDesc.setVisibility(View.GONE);
                    tvRepoLang.setText(userRepo.getDescription());
                } else if (userRepo.getLanguage() == null) {
                    tvRepoLang.setVisibility(View.GONE);
                    tvRepoDesc.setText(userRepo.getDescription());
                }
            } else {
                tvRepoDesc.setText(userRepo.getDescription());
                tvRepoLang.setText(userRepo.getLanguage());
            }
        }
    }

    public void setData(List<UserRepo> mUser) {
        this.mUserRepo = mUser;
        notifyDataSetChanged();
    }
}
