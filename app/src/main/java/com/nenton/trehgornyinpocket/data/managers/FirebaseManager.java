package com.nenton.trehgornyinpocket.data.managers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private static FirebaseManager instance;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public DatabaseReference getReference() {
        return reference;
    }
}
