package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherDto> weathers = new ArrayList<>();

    public void reloadAdapter(List<WeatherDto> weathers) {
        if (this.weathers != null) {
            this.weathers.clear();
            this.weathers = weathers;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherDto weather = weathers.get(position);
    }

    @Override
    public int getItemCount() {
        if (weathers == null) {
            return 0;
        }
        return weathers.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        public WeatherViewHolder(View itemView) {
            super(itemView);
        }
    }
}
