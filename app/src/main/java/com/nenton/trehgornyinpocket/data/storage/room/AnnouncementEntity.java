package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;
import java.util.List;

@Entity(tableName = "announcements")
public class AnnouncementEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Date date;
    private List<String> imagesUrl;
    private String videoUrl;

    public AnnouncementEntity(int id, String title, String description, Date date, List<String> imagesUrl, String videoUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
    }

    @Ignore
    public AnnouncementEntity(String title, String description, Date date, List<String> imagesUrl, String videoUrl) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
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

    public Date getDate() {
        return date;
    }

    public List<String> getImagesUrl() {
        return imagesUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}