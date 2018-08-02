package com.nenton.trehgornyinpocket.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;
import com.nenton.trehgornyinpocket.data.storage.room.WeatherEntity;

import static com.nenton.trehgornyinpocket.ui.widget.AppWidget.PREFS_NAME;
import static com.nenton.trehgornyinpocket.ui.widget.AppWidget.PREF_WIDGET_ID;

public class WeatherIntentService extends IntentService {
    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherEntity currentDayWeather = AppDatabase.getInstance(this.getApplication()).weatherDao().getCurrentDayWeather();
        WeatherDto weatherDto = new WeatherDto(currentDayWeather);
        int widgetId = getSharedPreferences(PREFS_NAME, 0).getInt(PREF_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        AppWidget.saveTitlePref(this, widgetId, weatherDto);
        AppWidget.updateAppWidget(this, AppWidgetManager.getInstance(this), widgetId);
    }
}
