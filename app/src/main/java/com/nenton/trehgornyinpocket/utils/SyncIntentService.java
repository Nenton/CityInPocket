package com.nenton.trehgornyinpocket.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.managers.FirebaseManager;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;

import java.util.ArrayList;
import java.util.List;

public class SyncIntentService extends IntentService {
    private ValueEventListener newsValueEventListener;
    private ValueEventListener announcementsValueEventListener;
    private ValueEventListener organizationsValueEventListener;
    private final Object object = new Object();

    public SyncIntentService() {
        super("SyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DatabaseReference reference = FirebaseManager.getInstance().getReference();
        DatabaseReference newsRef = reference.child("news");
        DatabaseReference announcementsRef = reference.child("announcements");
        DatabaseReference organizationsRef = reference.child("organizations");
        observeNews(newsRef);
        observeAnnouncements(announcementsRef);
        observeOrganizations(organizationsRef);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            newsRef.removeEventListener(newsValueEventListener);
            announcementsRef.removeEventListener(announcementsValueEventListener);
            organizationsRef.removeEventListener(organizationsValueEventListener);
        }, 10000);
    }

    private void observeOrganizations(DatabaseReference organizations) {
        organizationsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<OrganizationEntity> organizations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(NewsDto.class) != null) {
                        organizations.add(new OrganizationEntity(snapshot.getValue(OrganizationDto.class)));
                    }
                }
                insertOrganization(organizations);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nothing
            }
        };
        organizations.addValueEventListener(organizationsValueEventListener);
    }

    private void observeAnnouncements(DatabaseReference announcements) {
        announcementsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<AnnouncementEntity> announcements = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(NewsDto.class) != null) {
                        announcements.add(new AnnouncementEntity(snapshot.getValue(AnnouncementDto.class)));
                    }
                }
                insertAnnouncement(announcements);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nothing
            }
        };
        announcements.addValueEventListener(announcementsValueEventListener);
    }

    private void observeNews(DatabaseReference news) {
        newsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NewsEntity> news = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(NewsDto.class) != null) {
                        news.add(new NewsEntity(snapshot.getValue(NewsDto.class)));
                    }
                }
                insertNews(news);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Nothing
            }
        };
        news.addValueEventListener(newsValueEventListener);
    }

    private void insertNews(List<NewsEntity> list) {
        new Thread(() -> {
            synchronized (object) {
                AppDatabase.getInstance(SyncIntentService.this).newsDao().insertNews(list);
            }
        }).start();
    }

    private void insertAnnouncement(List<AnnouncementEntity> list) {
        new Thread(() -> {
            synchronized (object) {
                AppDatabase.getInstance(SyncIntentService.this).announcementsDao().insertAnnouncement(list);
            }
        }).start();
    }

    private void insertOrganization(List<OrganizationEntity> list) {
        new Thread(() -> {
            synchronized (object) {
                AppDatabase.getInstance(SyncIntentService.this).organizationsDao().insertOrganization(list);
            }
        }).start();
    }
}
