package com.nenton.trehgornyinpocket.ui.screens.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.utils.ViewHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    @Inject
    WeatherScreen.WeatherPresenter presenter;
    @Inject
    Picasso picasso;

    private List<WeatherDto> weathers = new ArrayList<>();

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        DaggerService.<WeatherScreen.Component>getDaggerComponent(recyclerView.getContext()).inject(this);
    }

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
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
        holder.dayText.setText(format.format(weather.getDate()));
        holder.maxTempText.setText(weather.getTemperatureMax());
        holder.minTempText.setText(weather.getTemperatureMin());

        holder.typeText.setText(ViewHelper.getWeatherTextFromType(weather.getWeatherType()));

        picasso.load(ViewHelper.getWeatherImageFromType(weather.getWeatherType()))
                .into(holder.weatherImage);
    }

    @Override
    public int getItemCount() {
        if (weathers == null) {
            return 0;
        }
        return weathers.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView weatherImage;
        private TextView dayText;
        private TextView typeText;
        private TextView maxTempText;
        private TextView minTempText;

        WeatherViewHolder(View itemView) {
            super(itemView);
            weatherImage = itemView.findViewById(R.id.weather_image_iv);
            dayText = itemView.findViewById(R.id.weather_day_tv);
            typeText = itemView.findViewById(R.id.weather_type_tv);
            maxTempText = itemView.findViewById(R.id.weather_max_tv);
            minTempText = itemView.findViewById(R.id.weather_min_tv);
        }
    }
}
