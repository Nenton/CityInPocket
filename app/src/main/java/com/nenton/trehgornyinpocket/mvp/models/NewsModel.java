package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.NewsEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.news.NewsViewModel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NewsModel extends AbstractModel {

    public LiveData<List<NewsEntity>> getNewsAllObs(RootActivity lifecycle) {
        return ViewModelProviders.of(lifecycle).get(NewsViewModel.class).getNewsAll();
    }

    private void mockData() {
        List<String> images = new ArrayList<>();
        images.add("https://www.motto.net.ua/pic/201310/1280x768/motto.net.ua-61988.jpg");
        images.add("https://happy-new-year-2018-images.download/wp-content/uploads/2017/12/news-year-new-year-greetings-happy-new-year-2017-759.jpg");
        NewsEntity newsDto1 = new NewsEntity("Happy New Year!", "On behalf of the mayor Oksana Padina, the administration of the Central district of Trekhgorny began to discuss the concept of new year decoration of the city by 2019 with social partners.",
                new Date(123), images, "");

        images = new ArrayList<>();
        images.add("https://st.depositphotos.com/1032577/4101/i/950/depositphotos_41019987-stock-photo-tulips-with-8-march-international.jpg");
        images.add("http://leisureblog.ru/wp-content/uploads/2015/03/8марта.jpg");
        NewsEntity newsDto2 = new NewsEntity("How was the most feminine day",
                "Since March 8 the head of the city congratulated all women of the city. There were celebrations and celebrations on the streets of the city",
                new Date(123), images, "");

        images = new ArrayList<>();
        images.add("http://www.zapkor.ru/upload/iblock/4d7/P82002K0001E.jpg");
        images.add("http://www.fazan.info/upload/medialibrary/5b5/5b5494487798ab68c39524c607eb3251.jpg");
        images.add("http://i.imgur.com/7AIonOi.jpg");
        NewsEntity newsDto3 = new NewsEntity("The mayor of the city showed his car", "All residents of the city could watch a colorful car driving through the streets with beautiful music",
                new Date(123), images, "");

        images = new ArrayList<>();
        images.add("https://avatars.mds.yandex.net/get-pdb/770122/64c12f44-8ab3-40d5-bbf6-13f3fd526aa5/s1200?webp=false");
        images.add("https://i.simpalsmedia.com/999.md/BoardImages/900x900/f86576e54295ad3fcb8e4ffff6e2613c.jpg");
        images.add("http://www.softoolstore.de/proxy.php?image=http%3A%2F%2Fgeek-nose.com%2Fwp-content%2Fuploads%2F2018%2F05%2F1-18.png&hash=56db1793481ed01de49738a264f091e7");
        NewsEntity newsDto4 = new NewsEntity("Whose computer is more powerful?", "Yesterday there were competitions for the title of the owner of the most powerful computer in the city. Winner wins",
                new Date(123), images, "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.newsDao().insertNews(newsDto1);
                appDatabase.newsDao().insertNews(newsDto2);
                appDatabase.newsDao().insertNews(newsDto3);
                appDatabase.newsDao().insertNews(newsDto4);
                appDatabase.newsDao().insertNews(newsDto1);
                appDatabase.newsDao().insertNews(newsDto2);
                appDatabase.newsDao().insertNews(newsDto3);
                appDatabase.newsDao().insertNews(newsDto4);
            }
        }).start();
    }

    public LiveData<List<NewsEntity>> getNewsOnSearch(RootActivity context, String query) {
        return ViewModelProviders.of(context).get(NewsViewModel.class).getNewsByQuery(query);
    }
}