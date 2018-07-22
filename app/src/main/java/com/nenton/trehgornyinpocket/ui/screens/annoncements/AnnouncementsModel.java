package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;

public class AnnouncementsModel extends AbstractModel {

    public Observable<AnnouncementDto> getAnnouncementsAllObs() {
        // TODO: 22.07.2018 implement me with DB
        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        AnnouncementDto announcementDto1 = new AnnouncementDto("First", "FIRST NEW", new Date(System.currentTimeMillis()), images, "");

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/812271/1934c8a2-a8f3-4b18-8ed5-7683e9842bfb/s1200");
        AnnouncementDto announcementDto2 = new AnnouncementDto("Second", "SECOND NEW", new Date(System.currentTimeMillis()), images, "");

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/70729/6b068f73-2c77-4d10-927e-9fd5b2ee2302/s1200");
        AnnouncementDto announcementDto3 = new AnnouncementDto("Third", "THIRD NEW", new Date(System.currentTimeMillis()), images, "");
        return Observable.just(announcementDto1, announcementDto2, announcementDto3);
    }

    public Observable<AnnouncementDto> getAnnouncementsOnSearch(String query) {
        // TODO: 22.07.2018 implement me with DB
        List<String> images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/25978/a6b1eff3-6894-434c-947e-cc0b69309565/s1200");
        AnnouncementDto announcementDto1 = new AnnouncementDto("First" + " " + query, "FIRST NEW", new Date(System.currentTimeMillis()), images, "");

        return Observable.just(announcementDto1);
    }
}
