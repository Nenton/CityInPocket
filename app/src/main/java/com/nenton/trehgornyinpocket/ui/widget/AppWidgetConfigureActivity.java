package com.nenton.trehgornyinpocket.ui.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration screen for the {@link AppWidget AppWidget} AppWidget.
 */
public class AppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.nenton.trehgornyinpocket.ui.widget.AppWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public AppWidgetConfigureActivity() {
        super();
    }

    static void saveTitlePref(Context context, int appWidgetId, WeatherDto weatherDto) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        Gson gson = new Gson();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, gson.toJson(weatherDto));
        prefs.apply();
    }

    static WeatherDto loadTitlePref(Context context, int appWidgetId) {
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
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.app_widget_configure);
        List<String> list = new ArrayList<>();
        list.add("News");
        list.add("Announcements");
        list.add("All weather");
        list.add("Weather one day");
        RecyclerView recyclerView = findViewById(R.id.widget_config_rv);
        AppWidgetAdapter adapter = new AppWidgetAdapter(list, id -> {

            // TODO: 01.08.2018 Выбрать какая позиция пришла и по такой позиции загрузить данные в saveTitlePref
//            saveTitlePref(this, mAppWidgetId, );

            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK);
            finish();
        });
        recyclerView.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
    }

    public interface ClickOnItemWidget {
        void clickEvent(int id);
    }
}

