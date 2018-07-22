package com.nenton.trehgornyinpocket.ui.screens.news;

import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsModel extends AbstractModel {

    public List<NewsDto> getNewsMock() {
        List<NewsDto> news = new ArrayList<>();

        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        news.add(new NewsDto("First", "FIRST NEW", new Date(System.currentTimeMillis()), images, ""));

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/812271/1934c8a2-a8f3-4b18-8ed5-7683e9842bfb/s1200");
        news.add(new NewsDto("Second", "SECOND NEW", new Date(System.currentTimeMillis()), images, ""));

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/70729/6b068f73-2c77-4d10-927e-9fd5b2ee2302/s1200");
        news.add(new NewsDto("Third", "THIRD NEW", new Date(System.currentTimeMillis()), images, ""));
        return news;
    }

    public List<NewsDto> getNewsFromSearch() {
        List<NewsDto> news = new ArrayList<>();

        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        news.add(new NewsDto("First", "FIRST NEW", new Date(System.currentTimeMillis()), images, ""));
        return news;
    }
}
