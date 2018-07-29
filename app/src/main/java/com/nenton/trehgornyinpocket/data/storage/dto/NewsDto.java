package com.nenton.trehgornyinpocket.data.storage.dto;

import java.io.Serializable;
import java.util.Map;

public class NewsDto implements Serializable {
    private int id;
    private String title;
    private String description;
    private long date;
    private Map<String, String> imagesUrl;
    private String videoUrl;

    public NewsDto() {
        //For Firebase Database
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

    public long getDate() {
        return date;
    }

    public Map<String, String> getImagesUrl() {
        return imagesUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
