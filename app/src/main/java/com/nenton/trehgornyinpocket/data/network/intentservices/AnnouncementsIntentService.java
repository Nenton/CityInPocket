package com.nenton.trehgornyinpocket.data.network.intentservices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nenton.trehgornyinpocket.data.managers.AppDatabase;
import com.nenton.trehgornyinpocket.data.managers.FirebaseManager;
import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsIntentService extends IntentService {
    private ValueEventListener announcementsValueEventListener;

    public AnnouncementsIntentService() {
        super("AnnouncementsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DatabaseReference reference = FirebaseManager.getInstance().getReference();
        DatabaseReference announcementsRef = reference.child("announcements");
        observeAnnouncements(announcementsRef);
        Handler handler = new Handler();
        handler.postDelayed(() -> announcementsRef.removeEventListener(announcementsValueEventListener),
                10000);
    }

    private void observeAnnouncements(DatabaseReference announcements) {
        announcementsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<AnnouncementEntity> announcements = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(AnnouncementDto.class) != null) {
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

    private void insertAnnouncement(List<AnnouncementEntity> list) {
        new Thread(() -> {
            AppDatabase.getInstance(AnnouncementsIntentService.this).announcementsDao().insertAnnouncement(list);
        }).start();
    }
}