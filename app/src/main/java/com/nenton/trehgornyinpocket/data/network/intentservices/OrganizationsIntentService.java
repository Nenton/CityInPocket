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
import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsIntentService extends IntentService {
    private ValueEventListener organizationsValueEventListener;

    public OrganizationsIntentService() {
        super("OrganizationsIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        DatabaseReference reference = FirebaseManager.getInstance().getReference();
        DatabaseReference organizationsRef = reference.child("organizations");
        observeOrganizations(organizationsRef);
        Handler handler = new Handler();
        handler.postDelayed(() ->
                        organizationsRef.removeEventListener(organizationsValueEventListener),
                10000);
    }

    private void observeOrganizations(DatabaseReference organizations) {
        organizationsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<OrganizationEntity> organizations = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(OrganizationDto.class) != null) {
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

    private void insertOrganization(List<OrganizationEntity> list) {
        new Thread(() -> AppDatabase.getInstance(OrganizationsIntentService.this).organizationsDao().insertOrganization(list)).start();
    }
}