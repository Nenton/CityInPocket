package com.nenton.trehgornyinpocket.data.storage.dto;

import java.util.Date;
import java.util.List;

public class NewsDto {
    private String description;
    private String fullNew;
    private Date date;
    private List<String> imagesUrl;
    private String videoUrl;

    public NewsDto(String description, String fullNew, Date date, List<String> imagesUrl, String videoUrl) {
        this.description = description;
        this.fullNew = fullNew;
        this.date = date;
        this.imagesUrl = imagesUrl;
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getFullNew() {
        return fullNew;
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
