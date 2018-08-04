package com.nenton.trehgornyinpocket.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.BROKEN_CLOUDS;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.CLEAR_SKY;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.FEW_CLOUDS;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.MIST;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.RAIN;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.SCATTERED_CLOUDS;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.SHOWER_RAIN;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.SNOW;
import static com.nenton.trehgornyinpocket.data.storage.room.WeatherType.THUNDERSTORM;

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

    public static String getWeatherTextFromType(WeatherType weatherType) {
        switch (weatherType) {
            case MIST:
                return "Mist";
            case CLEAR_SKY:
                return "Clear sky";
            case FEW_CLOUDS:
                return "Few clouds";
            case SHOWER_RAIN:
                return "Shower rain";
            case THUNDERSTORM:
                return "Thunderstorm";
            case BROKEN_CLOUDS:
                return "Broken clouds";
            case SCATTERED_CLOUDS:
                return "Scattered clouds";
            case RAIN:
                return "Rain";
            case SNOW:
                return "Snow";
            default:
                return "Partly cloudy";
        }
    }

    public static int getWeatherImageFromType(WeatherType weatherType) {
        switch (weatherType) {
            case MIST:
                return R.drawable.ic_mist;
            case CLEAR_SKY:
                return R.drawable.ic_clear_sky;
            case FEW_CLOUDS:
                return R.drawable.ic_few_clouds;
            case SHOWER_RAIN:
                return R.drawable.ic_shower_rain;
            case THUNDERSTORM:
                return R.drawable.ic_thunderstorm;
            case BROKEN_CLOUDS:
                return R.drawable.ic_broken_clouds;
            case SCATTERED_CLOUDS:
                return R.drawable.ic_scattered_clouds;
            case RAIN:
                return R.drawable.ic_rain;
            case SNOW:
                return R.drawable.ic_snow;
            default:
                return R.drawable.ic_few_clouds;
        }
    }

    public static WeatherType getWeatherTypeFromNet(String main) {
        switch (main.toLowerCase()) {
            case "mist":
                return MIST;
            case "clear sky":
                return CLEAR_SKY;
            case "few clouds":
                return FEW_CLOUDS;
            case "shower rain":
                return SHOWER_RAIN;
            case "thunderstorm":
                return THUNDERSTORM;
            case "broken clouds":
                return BROKEN_CLOUDS;
            case "scattered clouds":
                return SCATTERED_CLOUDS;
            case "rain":
                return RAIN;
            case "snow":
                return SNOW;
            default:
                return FEW_CLOUDS;
        }
    }

    public interface OnMeasureCallback {
        void onMeasure(View view, int width, int height);
    }
}
