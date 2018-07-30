package com.nenton.trehgornyinpocket.ui.screens.curannoncement;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CurAnnouncementAdapter extends PagerAdapter {
    @Inject
    Picasso picasso;
    @Inject
    CurAnnouncementScreen.CurAnnouncementPresenter presenter;
    private List<String> urlsPhotos = new ArrayList<>();

    @Override
    public int getCount() {
        return urlsPhotos.size();
    }

    public void addUrl(String url) {
        urlsPhotos.add(url);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        urlsPhotos.clear();
        urlsPhotos = new ArrayList<>();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        DaggerService.<CurAnnouncementScreen.Component>getDaggerComponent(container.getContext()).inject(this);
        super.startUpdate(container);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String url = urlsPhotos.get(position);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager_image, container, false);
        ImageView imageView = view.findViewById(R.id.item_pager_iv);
        picasso.load(url)
                .placeholder(R.drawable.ic_newspaper)
                .resize(400, 300)
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback.EmptyCallback() {
                    @Override
                    public void onError(Exception e) {
                        picasso.load(url)
                                .placeholder(R.drawable.ic_newspaper)
                                .resize(400, 300)
                                .centerCrop()
                                .into(imageView);
                    }
                });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
