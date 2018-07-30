package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.utils.ViewHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolderAnnouncement> {

    @Inject
    Picasso picasso;
    @Inject
    AnnouncementsScreen.AnnouncementsPresenter presenter;

    private List<AnnouncementEntity> announcementList = new ArrayList<>();

    public void reloadAdapter(List<AnnouncementEntity> announcementList) {
        this.announcementList = announcementList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        DaggerService.<AnnouncementsScreen.Component>getDaggerComponent(recyclerView.getContext()).inject(this);
    }

    @NonNull
    @Override
    public ViewHolderAnnouncement onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_announcement, parent, false);
        return new ViewHolderAnnouncement(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnnouncement holder, int position) {
        AnnouncementEntity announcement = announcementList.get(position);
        holder.announcementTitle.setText(announcement.getTitle());
        holder.announcementDate.setText(ViewHelper.getDateFromPattern(announcement.getDate()));

        picasso.load(announcement.getImagesUrl().get(0))
                .placeholder(R.drawable.ic_newspaper)
                .resize(200, 150)
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.announcementImage, new Callback.EmptyCallback() {
                    @Override
                    public void onError(Exception e) {
                        picasso.load(announcement.getImagesUrl().get(0))
                                .placeholder(R.drawable.ic_newspaper)
                                .resize(200, 150)
                                .centerCrop()
                                .into(holder.announcementImage);
                    }
                });

        holder.itemView.setOnClickListener(view -> presenter.clickOnAnnouncement(announcement));
    }

    @Override
    public int getItemCount() {
        if (announcementList == null) {
            return 0;
        }
        return announcementList.size();
    }

    class ViewHolderAnnouncement extends RecyclerView.ViewHolder {
        private ImageView announcementImage;
        private TextView announcementDate;
        private TextView announcementTitle;

        private ViewHolderAnnouncement(View itemView) {
            super(itemView);
            announcementImage = itemView.findViewById(R.id.announcement_image_iv);
            announcementDate = itemView.findViewById(R.id.announcement_date_tv);
            announcementTitle = itemView.findViewById(R.id.organization_desc_tv);
        }
    }
}
