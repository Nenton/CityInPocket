package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "organizations")
public class OrganizationEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String imagesUrl;
    private List<ContactEntity> contacts;

    public OrganizationEntity(int id, String title, String description, String imagesUrl, List<ContactEntity> contacts) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagesUrl = imagesUrl;
        this.contacts = contacts;
    }

    @Ignore
    public OrganizationEntity(String title, String description, String imagesUrl, List<ContactEntity> contacts) {
        this.title = title;
        this.description = description;
        this.imagesUrl = imagesUrl;
        this.contacts = contacts;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public List<ContactEntity> getContacts() {
        return contacts;
    }
}
