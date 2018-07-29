package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;

import com.nenton.trehgornyinpocket.data.storage.room.AnnouncementEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.annoncements.AnnouncementsViewModel;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnnouncementsModel extends AbstractModel {

    public LiveData<List<AnnouncementEntity>> getAnnouncementsAllObs(RootActivity lifecycle) {
//        mock();
        return ViewModelProviders.of(lifecycle).get(AnnouncementsViewModel.class).getAnnouncementsAll();
    }

    private void mock() {
        try {
            String first = "01 Jan 2019";
            String second = "08 Mar 2019";
            String third = "10 Jul 2019";
            String fourth = "31 Aug 2019";
            SimpleDateFormat formatFrom = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

            List<String> images = new ArrayList<>();
            images.add("https://www.motto.net.ua/pic/201310/1280x768/motto.net.ua-61988.jpg");
            images.add("https://happy-new-year-2018-images.download/wp-content/uploads/2017/12/news-year-new-year-greetings-happy-new-year-2017-759.jpg");
            AnnouncementEntity announcementDto1 = new AnnouncementEntity("New year celebration coming soon", "New year celebration coming soon",
                    new Date(formatFrom.parse(first).getTime()), images, "");

            images = new ArrayList<>();
            images.add("https://st.depositphotos.com/1032577/4101/i/950/depositphotos_41019987-stock-photo-tulips-with-8-march-international.jpg");
            images.add("http://leisureblog.ru/wp-content/uploads/2015/03/8марта.jpg");
            AnnouncementEntity announcementDto2 = new AnnouncementEntity("The schedule of the celebration of March 8", "The schedule of the celebration of March 8",
                    new Date(formatFrom.parse(second).getTime()), images, "");

            images = new ArrayList<>();
            images.add("http://www.zapkor.ru/upload/iblock/4d7/P82002K0001E.jpg");
            images.add("http://www.fazan.info/upload/medialibrary/5b5/5b5494487798ab68c39524c607eb3251.jpg");
            images.add("http://i.imgur.com/7AIonOi.jpg");
            AnnouncementEntity announcementDto3 = new AnnouncementEntity("The mayor said he'll show his car soon.", "The mayor said he'll show his car soon.",
                    new Date(formatFrom.parse(third).getTime()), images, "");

            images = new ArrayList<>();
            images.add("https://avatars.mds.yandex.net/get-pdb/770122/64c12f44-8ab3-40d5-bbf6-13f3fd526aa5/s1200?webp=false");
            images.add("https://i.simpalsmedia.com/999.md/BoardImages/900x900/f86576e54295ad3fcb8e4ffff6e2613c.jpg");
            images.add("http://www.softoolstore.de/proxy.php?image=http%3A%2F%2Fgeek-nose.com%2Fwp-content%2Fuploads%2F2018%2F05%2F1-18.png&hash=56db1793481ed01de49738a264f091e7");
            AnnouncementEntity announcementDto4 = new AnnouncementEntity("When will the competition be held?", "When will the competition be held?",
                    new Date(formatFrom.parse(fourth).getTime()), images, "");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto1);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto2);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto3);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto4);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto1);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto2);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto3);
                    appDatabase.announcementsDao().insertAnnouncement(announcementDto4);
                }
            }).start();
        } catch (ParseException e) {
            Log.e(AnnouncementsModel.class.getName(), "Error", e);
        }
    }

    public LiveData<List<AnnouncementEntity>> getAnnouncementsOnSearch(RootActivity lifecycle, String query) {
        return ViewModelProviders.of(lifecycle).get(AnnouncementsViewModel.class).getAnnouncementsByQuery(query);
    }
}
