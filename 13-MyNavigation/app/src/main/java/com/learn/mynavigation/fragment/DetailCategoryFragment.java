package com.learn.mynavigation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.learn.mynavigation.R;

public class DetailCategoryFragment extends Fragment {

    private TextView tvCategoryName, tvCategoryDescription;
    private Button btnProfile;
    private String dataName;
    private long dataDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCategoryName = view.findViewById(R.id.tv_category_name);
        tvCategoryDescription = view.findViewById(R.id.tv_category_description);


        if (getArguments() != null) {
            // Dengan Bundle
//            dataName = getArguments().getString(CategoryFragment.EXTRA_NAME);
//            dataDescription = getArguments().getLong(CategoryFragment.EXTRA_STOCK);

            // Dengan SafeArgs
            dataName = DetailCategoryFragmentArgs.fromBundle(getArguments()).getName();
            dataDescription = DetailCategoryFragmentArgs.fromBundle(getArguments()).getStock();
        } else {
            Toast.makeText(getContext(), "No Data Available", Toast.LENGTH_SHORT).show();
        }

        tvCategoryName.setText(dataName);
        tvCategoryDescription.setText("Stock " + dataDescription);

        btnProfile = view.findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_detailCategoryFragment_to_homeFragment, null)
        );
    }
}