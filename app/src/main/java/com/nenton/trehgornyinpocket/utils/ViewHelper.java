package com.nenton.trehgornyinpocket.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by serge on 25.03.2017.
 */
public class ViewHelper {

    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static List<View> getChildExludeView(ViewGroup container, @IdRes int... excludeChild) {
        ArrayList<View> childs = new ArrayList<>();

        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            for (int exlude : excludeChild) {
                if (child.getId() != exlude) {
                    childs.add(child);
                }
            }
        }
        return childs;
    }

    public static void waitForMeasure(View view, OnMeasureCallback callback) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (width > 0 && height > 0) {
            callback.onMeasure(view, width, height);
            return;
        }

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                final ViewTreeObserver observer = view.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.removeOnPreDrawListener(this);
                }

                callback.onMeasure(view, view.getWidth(), view.getHeight());
                return true;
            }
        });

    }

    public static String getDateFromPattern(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        return format.format(date);
    }

    public static String getWeatherTextFromType(WeatherDto.WeatherType weatherType) {
        switch (weatherType) {
            case SUMMER:
                return "Clear";
            case RAIN:
                return "Rain";
            case SNOW:
                return "Snow";
            case SLEET:
                return "Sleet";
            case STORM:
                return "Storm";
            case WINDY:
                return "Windy";
            case CLOUDS:
                return "Cloud";
            case HEAVY_RAIN:
                return "Heavy rain";
            case RAIN_CLOUD:
                return "Rain cloud";
            case PARTLY_CLOUDY:
                return "Partly cloudy";
            default:
                return "Partly cloudy";
        }
    }

    public static int getWeatherImageFromType(WeatherDto.WeatherType weatherType) {
        switch (weatherType) {
            case SUMMER:
                return R.drawable.ic_weather_summer;
            case RAIN:
                return R.drawable.ic_weather_rain;
            case SNOW:
                return R.drawable.ic_weather_snow;
            case SLEET:
                return R.drawable.ic_weather_sleet;
            case STORM:
                return R.drawable.ic_weather_storm;
            case WINDY:
                return R.drawable.ic_weather_windy_weather;
            case CLOUDS:
                return R.drawable.ic_weather_clouds;
            case HEAVY_RAIN:
                return R.drawable.ic_weather_heavy_rain;
            case RAIN_CLOUD:
                return R.drawable.ic_weather_rain_cloud;
            case PARTLY_CLOUDY:
                return R.drawable.ic_weather_partly_cloudy_day;
            default:
                return R.drawable.ic_weather_partly_cloudy_day;
        }
    }

    public interface OnMeasureCallback {
        void onMeasure(View view, int width, int height);
    }
}
