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
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;

public class SyncIntentService extends IntentService {
    private ValueEventListener newsValueEventListener;
    private ValueEventListener announcementsValueEventListener;
    private ValueEventListener organizationsValueEventListener;

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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    insertOrganization(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        organizations.addValueEventListener(organizationsValueEventListener);
    }

    private void observeAnnouncements(DatabaseReference announcements) {
        announcementsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    insertAnnouncement(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        announcements.addValueEventListener(announcementsValueEventListener);
    }

    private void observeNews(DatabaseReference news) {
        newsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    insertNews(snapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        news.addValueEventListener(newsValueEventListener);
    }

    private void insertNews(DataSnapshot snapshot) {
        AppDatabase.getInstance(SyncIntentService.this).newsDao().insertNews(snapshot.getValue(NewsEntity.class));
    }

    private void insertAnnouncement(DataSnapshot snapshot) {
        AppDatabase.getInstance(SyncIntentService.this).announcementsDao().insertAnnouncement(snapshot.getValue(AnnouncementEntity.class));
    }

    private void insertOrganization(DataSnapshot snapshot) {
        AppDatabase.getInstance(SyncIntentService.this).organizationsDao().insertOrganization(snapshot.getValue(OrganizationEntity.class));
    }
}
