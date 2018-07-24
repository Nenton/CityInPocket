package com.nenton.trehgornyinpocket.ui.screens.annoncements;

import com.nenton.trehgornyinpocket.data.storage.dto.AnnouncementDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class AnnouncementsModel extends AbstractModel {

    public Observable<AnnouncementDto> getAnnouncementsAllObs() {
        try {
            // TODO: 22.07.2018 implement me with DB
            String first = "01 Jan 2019";
            String second = "08 Mar 2019";
            String third = "10 Jul 2019";
            String fourth = "31 Aug 2019";
            SimpleDateFormat formatFrom = new SimpleDateFormat("dd MMM yyyy");

            List<String> images = new ArrayList<>();
            images.add("https://www.motto.net.ua/pic/201310/1280x768/motto.net.ua-61988.jpg");
            images.add("https://happy-new-year-2018-images.download/wp-content/uploads/2017/12/news-year-new-year-greetings-happy-new-year-2017-759.jpg");
            AnnouncementDto announcementDto1 = new AnnouncementDto("New year celebration coming soon", "New year celebration coming soon",
                    formatFrom.parse(first), images, "");

            images = new ArrayList<>();
            images.add("https://st.depositphotos.com/1032577/4101/i/950/depositphotos_41019987-stock-photo-tulips-with-8-march-international.jpg");
            images.add("http://leisureblog.ru/wp-content/uploads/2015/03/8марта.jpg");
            AnnouncementDto announcementDto2 = new AnnouncementDto("The schedule of the celebration of March 8", "The schedule of the celebration of March 8",
                    formatFrom.parse(second), images, "");

            images = new ArrayList<>();
            images.add("http://www.zapkor.ru/upload/iblock/4d7/P82002K0001E.jpg");
            images.add("http://www.fazan.info/upload/medialibrary/5b5/5b5494487798ab68c39524c607eb3251.jpg");
            images.add("http://i.imgur.com/7AIonOi.jpg");
            AnnouncementDto announcementDto3 = new AnnouncementDto("The mayor said he'll show his car soon.", "The mayor said he'll show his car soon.",
                    formatFrom.parse(third), images, "");

            images = new ArrayList<>();
            images.add("https://avatars.mds.yandex.net/get-pdb/770122/64c12f44-8ab3-40d5-bbf6-13f3fd526aa5/s1200?webp=false");
            images.add("https://i.simpalsmedia.com/999.md/BoardImages/900x900/f86576e54295ad3fcb8e4ffff6e2613c.jpg");
            images.add("http://www.softoolstore.de/proxy.php?image=http%3A%2F%2Fgeek-nose.com%2Fwp-content%2Fuploads%2F2018%2F05%2F1-18.png&hash=56db1793481ed01de49738a264f091e7");
            AnnouncementDto announcementDto4 = new AnnouncementDto("When will the competition be held?", "When will the competition be held?",
                    formatFrom.parse(fourth), images, "");
            return Observable.just(announcementDto1, announcementDto2, announcementDto3, announcementDto4);
        } catch (ParseException e) {
            return Observable.empty();
        }
    }

    public Observable<AnnouncementDto> getAnnouncementsOnSearch(String query) {
        // TODO: 22.07.2018 implement me with DB
        return getAnnouncementsAllObs();
    }
}
