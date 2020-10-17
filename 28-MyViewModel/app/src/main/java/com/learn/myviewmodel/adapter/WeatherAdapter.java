package com.learn.myviewmodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learn.myviewmodel.R;
import com.learn.myviewmodel.model.WeatherItems;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<WeatherItems> mData = new ArrayList<>();

    public void setData(ArrayList<WeatherItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_items, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView tvViewCity;
        TextView tvViewTemperature;
        TextView tvViewDescription;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvViewCity = itemView.findViewById(R.id.text_city);
            tvViewTemperature = itemView.findViewById(R.id.text_temp);
            tvViewDescription = itemView.findViewById(R.id.text_desc);
        }

        void bind(WeatherItems weatherItems) {
            tvViewCity.setText(weatherItems.getName());
            tvViewTemperature.setText(weatherItems.getTemperature());
            tvViewDescription.setText(weatherItems.getDescription());
        }
    }
}
