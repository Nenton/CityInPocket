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
import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;

import java.util.ArrayList;
import java.util.List;

public class NewsIntentService extends IntentService {
    private ValueEventListener newsValueEventListener;

    public NewsIntentService() {
        super("NewsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DatabaseReference reference = FirebaseManager.getInstance().getReference();
        DatabaseReference newsRef = reference.child("news");
        observeNews(newsRef);

        Handler handler = new Handler();
        handler.postDelayed(() -> newsRef.removeEventListener(newsValueEventListener),
                10000);
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
        new Thread(() -> AppDatabase.getInstance(NewsIntentService.this).newsDao().insertNews(list)).start();
    }
}