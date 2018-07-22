package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nenton.trehgornyinpocket.R;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.di.DaggerService;
import com.nenton.trehgornyinpocket.utils.ViewHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolderAnnouncement> {

    @Inject
    Picasso picasso;
    @Inject
    AnnouncementsScreen.AnnouncementsPresenter presenter;

    private List<AnnouncementDto> announcementList = new ArrayList<>();

    public void clearAdapter() {
        announcementList.clear();
        announcementList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addAnnouncement(AnnouncementDto announcement) {
        if (announcementList != null) {
            announcementList.add(announcement);
            notifyDataSetChanged();
        }
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
        AnnouncementDto announcement = announcementList.get(position);
        holder.announcementTitle.setText(announcement.getTitle());
        holder.announcementDate.setText(ViewHelper.getDateFromPattern(announcement.getDate()));

        if (announcement.getImagesUrl() != null && announcement.getImagesUrl().size() > 0) {
            picasso.load(announcement.getImagesUrl().get(0))
                    .into(holder.announcementImage);
        }
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
