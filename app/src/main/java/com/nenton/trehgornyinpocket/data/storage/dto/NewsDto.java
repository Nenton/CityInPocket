package com.nenton.trehgornyinpocket.data.storage.dto;

import java.util.Date;
import java.util.List;

public class NewsDto {
    private String title;
    private String description;
    private Date date;
    private List<String> imagesUrl;
    private String videoUrl;

    public NewsDto(String description, String fullNew, Date date, List<String> imagesUrl, String videoUrl) {
        this.title = description;
        this.description = fullNew;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
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
