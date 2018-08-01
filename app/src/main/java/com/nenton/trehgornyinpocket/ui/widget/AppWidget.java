package com.nenton.trehgornyinpocket.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.nenton.trehgornyinpocket.R;

import java.util.Set;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidgetConfigureActivity AppWidgetConfigureActivity}
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Set<String> widgetText = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.appwidget_text, "БАЛДА");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

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
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
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

