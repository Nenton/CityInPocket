package com.nenton.trehgornyinpocket.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nenton.trehgornyinpocket.R;

import java.util.ArrayList;
import java.util.List;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<String> data;
    private Context context;
    private int widgetId;

    public WidgetFactory(Context context, Intent intent) {
        this.context = context;
        widgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        if (widgetId != INVALID_APPWIDGET_ID) {
            data = new ArrayList<>(AppWidgetConfigureActivity.loadTitlePref(context, widgetId));
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item_widget); // TODO: 01.08.2018 Это не тот итем
        view.setTextViewText(R.id.appwidget_item_text_tv, data.get(i));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
