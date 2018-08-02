package com.nenton.trehgornyinpocket.ui.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;

import java.util.List;

public class AppWidgetAdapter extends RecyclerView.Adapter<AppWidgetAdapter.ViewHolder> {
    private List<String> list;
    private AppWidgetConfigureActivity.ClickOnItemWidget listener;

    public AppWidgetAdapter(List<String> list, AppWidgetConfigureActivity.ClickOnItemWidget listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_widget, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = list.get(position);
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.appwidget_item_text_tv);
        }
    }
}
