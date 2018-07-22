package com.nenton.trehgornyinpocket.ui.screens.news;

import com.nenton.trehgornyinpocket.data.storage.dto.NewsDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

public class NewsModel extends AbstractModel {

    public Observable<NewsDto> getNewsAllObs() {
        // TODO: 22.07.2018 implement me with DB
        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        NewsDto newsDto1 = new NewsDto("First", "FIRST NEW", new Date(System.currentTimeMillis()), images, "");

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/812271/1934c8a2-a8f3-4b18-8ed5-7683e9842bfb/s1200");
        NewsDto newsDto2 = new NewsDto("Second", "SECOND NEW", new Date(System.currentTimeMillis()), images, "");

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/70729/6b068f73-2c77-4d10-927e-9fd5b2ee2302/s1200");
        NewsDto newsDto3 = new NewsDto("Third", "THIRD NEW", new Date(System.currentTimeMillis()), images, "");
        return Observable.just(newsDto1, newsDto2, newsDto3);
    }

    public Observable<NewsDto> getNewsOnSearch(String query) {
        // TODO: 22.07.2018 implement me with DB
        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        NewsDto newsDto1 = new NewsDto("First" + " " + query, "FIRST NEW", new Date(System.currentTimeMillis()), images, "");

        return Observable.just(newsDto1);
    }
}
