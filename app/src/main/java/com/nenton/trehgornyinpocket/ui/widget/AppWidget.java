package com.nenton.trehgornyinpocket.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.WeatherDto;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidgetConfigureActivity AppWidgetConfigureActivity}
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        WeatherDto weatherDto = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_weather);
        views.setTextViewText(R.id.widget_city_tv, "БАЛДА");
        views.setTextViewText(R.id.widget_max_tv, "БАЛДА");
        views.setTextViewText(R.id.widget_min_tv, "БАЛДА");
        views.setTextViewText(R.id.widget_type_weather_tv, "БАЛДА");
        views.setTextViewText(R.id.widget_date_tv, "БАЛДА");
        views.setImageViewResource(R.id.widget_weather_iv, R.drawable.ic_weather_clouds);

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.widget_weather);

//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
//        setUpdateRv(views, context, appWidgetId);
//        setList(views, context, appWidgetId);
//
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
    }

//    private static void setList(RemoteViews views, Context context, int appWidgetId) {
//        Log.e("Widget", "setList");
//        Intent intent = new Intent(context, MyService.class);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        views.setRemoteAdapter(R.id.widget_list, intent);
//    }
//
//    private static void setUpdateRv(RemoteViews views, Context context, int appWidgetId) {
//        Log.e("Widget", "setUpdateRv");
//        Intent intent = new Intent(context, Widget.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
//        views.setOnClickPendingIntent(R.id.widget_tv, pendingIntent);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            AppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
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

