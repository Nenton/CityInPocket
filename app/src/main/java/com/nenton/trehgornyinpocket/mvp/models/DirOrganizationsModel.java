package com.nenton.trehgornyinpocket.mvp.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;

import com.nenton.trehgornyinpocket.data.storage.room.ContactEntity;
import com.nenton.trehgornyinpocket.data.storage.room.ContactType;
import com.nenton.trehgornyinpocket.data.storage.room.OrganizationEntity;
import com.nenton.trehgornyinpocket.ui.activities.RootActivity;
import com.nenton.trehgornyinpocket.ui.screens.dirorganization.DirOrganizationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class DirOrganizationsModel extends AbstractModel {
    public LiveData<List<OrganizationEntity>> getOrganizationAllObs(RootActivity lifecycle) {
//        mockData();
        return ViewModelProviders.of(lifecycle).get(DirOrganizationsViewModel.class).getOrganizationsAll();
    }

    private void mockData() {
        List<ContactEntity> contacts = new ArrayList<>();
        contacts.add(new ContactEntity("+7-999-99-99", ContactType.CONTACT_PHONE));
        contacts.add(new ContactEntity("+7-888-99-99", ContactType.CONTACT_PHONE));
        contacts.add(new ContactEntity("google@google.google", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("@google", ContactType.CONTACT_TELEGRAM));
        OrganizationEntity organization1 = new OrganizationEntity("Google",
                "Google everywhere",
                "https://yt3.ggpht.com/a-/AJLlDp33ljSzC7MycJTBh5G1wTfrblHW9TB2TrD01g=s900-mo-c-c0xffffffff-rj-k-no",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new ContactEntity("yandex@yandex.yandex", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("@yandex", ContactType.CONTACT_TELEGRAM));
        OrganizationEntity organization2 = new OrganizationEntity("Yandex",
                "Yandex almost everywhere",
                "https://load-soft.com/wp-content/uploads/2015/07/yandex-browser-thumb.png",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new ContactEntity("prob@prob.prob", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("org@org.org", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("@org", ContactType.CONTACT_TELEGRAM));
        OrganizationEntity organization3 = new OrganizationEntity("Organization",
                "Organization for city main role in building towers",
                "http://trapedhost.com/wp-content/uploads/2015/02/company.png",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new ContactEntity("1-111-111-11-11", ContactType.CONTACT_PHONE));
        contacts.add(new ContactEntity("2-222-222-22-22", ContactType.CONTACT_PHONE));
        contacts.add(new ContactEntity("bmw@bmw.bmw", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("@bmw", ContactType.CONTACT_TELEGRAM));
        OrganizationEntity organization4 = new OrganizationEntity("BMW",
                "BMW selling cars in city",
                "http://clipart-library.com/image_gallery/n1933106.jpg",
                contacts);
        contacts = new ArrayList<>();
        contacts.add(new ContactEntity("transport@transport.transport", ContactType.CONTACT_EMAIL));
        contacts.add(new ContactEntity("@transport", ContactType.CONTACT_TELEGRAM));
        OrganizationEntity organization5 = new OrganizationEntity("Transport company",
                "The transport company will deliver any goods to any point of the city",
                "http://евро-палет.рф/wp-content/uploads/2015/12/Грузоперевозки.png",
                contacts);
        new Thread(() -> {
            appDatabase.organizationsDao().insertOrganization(organization1);
            appDatabase.organizationsDao().insertOrganization(organization2);
            appDatabase.organizationsDao().insertOrganization(organization3);
            appDatabase.organizationsDao().insertOrganization(organization4);
            appDatabase.organizationsDao().insertOrganization(organization5);
        }).start();
    }

    public LiveData<List<OrganizationEntity>> getOrganizationsOnSearch(RootActivity lifecycle, String query) {
        return ViewModelProviders.of(lifecycle).get(DirOrganizationsViewModel.class).getOrganizationsByQuery(query);
    }
}
