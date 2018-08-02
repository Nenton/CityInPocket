package com.nenton.trehgornyinpocket.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.utils.ViewHelper;

import java.util.Date;

public class AppWidget extends AppWidgetProvider {

    public static final String PREFS_NAME = "com.nenton.trehgornyinpocket.ui.widget.AppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    public static final String PREF_WIDGET_ID = "PREF_WIDGET_ID";

    static void saveTitlePref(Context context, int appWidgetId, WeatherDto weatherDto) {
        Log.e("saveTitlePref", String.valueOf(appWidgetId));
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        Gson gson = new Gson();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, gson.toJson(weatherDto));
        prefs.apply();
    }

    static WeatherDto loadTitlePref(Context context, int appWidgetId) {
        Log.e("loadTitlePref", String.valueOf(appWidgetId));
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        WeatherDto weatherDto = gson.fromJson(titleValue, WeatherDto.class);
        if (weatherDto != null) {
            return weatherDto;
        } else {
            return null;
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        Log.e("deleteTitlePref", String.valueOf(appWidgetId));
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e("updateAppWidget", String.valueOf(appWidgetId));
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();

        WeatherDto weatherDto = loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
        if (weatherDto != null) {
            views.setTextViewText(R.id.widget_city_tv, "Trehgorniy");
            views.setTextViewText(R.id.widget_min_max_tv, weatherDto.getTemperatureMin() + "° - " + weatherDto.getTemperatureMax() + "°");
            views.setTextViewText(R.id.widget_type_weather_tv, ViewHelper.getWeatherTextFromType(weatherDto.getWeatherType()));
            views.setTextViewText(R.id.widget_date_tv, ViewHelper.getDateFromPattern(new Date(weatherDto.getDate())));
            views.setImageViewResource(R.id.widget_weather_iv, ViewHelper.getWeatherImageFromType(weatherDto.getWeatherType()));
            Intent clickIntent = new Intent(context, WeatherIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, clickIntent, 0);
            views.setOnClickPendingIntent(R.id.widget_weather_iv, pendingIntent);
        }

        if (weatherDto == null) {
            prefs.putInt(PREF_WIDGET_ID, appWidgetId);
            prefs.apply();
            Intent intent = new Intent(context, WeatherIntentService.class);
            context.startService(intent);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.widget_weather);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

