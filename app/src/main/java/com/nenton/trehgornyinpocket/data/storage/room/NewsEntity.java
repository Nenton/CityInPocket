package com.nenton.trehgornyinpocket.data.storage.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "news")
public class NewsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private Date date;
    private List<String> imagesUrl;
    private String videoUrl;

    @Ignore
    public NewsEntity(String title, String description, Date date, List<String> imagesUrl, String videoUrl) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
    }

    public NewsEntity(int id, String title, String description, Date date, List<String> imagesUrl, String videoUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
    }

    @Ignore
    public NewsEntity(NewsDto value) {
        this.id = value.getId();
        this.title = value.getTitle();
        this.description = value.getDescription();
        this.date = new Date(value.getDate());
        imagesUrl = new ArrayList<>();
        imagesUrl.addAll(value.getImagesUrl().values());
        this.videoUrl = value.getVideoUrl();
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
