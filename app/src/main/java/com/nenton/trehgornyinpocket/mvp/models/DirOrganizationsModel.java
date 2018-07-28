package com.nenton.trehgornyinpocket.mvp.models;

import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class DirOrganizationsModel extends AbstractModel {
    public Observable<OrganizationDto> getOrganizationAllObs() {
// TODO: 23.07.2018 implement me
        List<OrganizationDto.Contact> contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "+7-999-99-99"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "+7-888-99-99"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "google@google.google"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@google"));
        OrganizationDto organization1 = new OrganizationDto("Google",
                "Google everywhere",
                "https://yt3.ggpht.com/a-/AJLlDp33ljSzC7MycJTBh5G1wTfrblHW9TB2TrD01g=s900-mo-c-c0xffffffff-rj-k-no",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "yandex@yandex.yandex"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@yandex"));
        OrganizationDto organization2 = new OrganizationDto("Yandex",
                "Yandex almost everywhere",
                "https://load-soft.com/wp-content/uploads/2015/07/yandex-browser-thumb.png",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "prob@prob.prob"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "org@org.org"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@org"));
        OrganizationDto organization3 = new OrganizationDto("Organization",
                "Organization for city main role in building towers",
                "http://trapedhost.com/wp-content/uploads/2015/02/company.png",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "1-111-111-11-11"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "2-222-222-22-22"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "bmw@bmw.bmw"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@bmw"));
        OrganizationDto organization4 = new OrganizationDto("BMW",
                "BMW selling cars in city",
                "http://clipart-library.com/image_gallery/n1933106.jpg",
                contacts);
        contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "transport@transport.transport"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@transport"));
        OrganizationDto organization5 = new OrganizationDto("Transport company",
                "The transport company will deliver any goods to any point of the city",
                "http://евро-палет.рф/wp-content/uploads/2015/12/Грузоперевозки.png",
                contacts);
        return Observable.just(organization1, organization2, organization3, organization4, organization5);
    }

    public Observable<OrganizationDto> getOrganizationsOnSearch(String query) {
        // TODO: 23.07.2018 implement me
        List<OrganizationDto.Contact> contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "+7-999-99-99"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "test@test.test"));
        OrganizationDto organization1 = new OrganizationDto("Home",
                "Home sweet home! With " + query,
                "https://st.depositphotos.com/1472772/2580/i/950/depositphotos_25801069-stock-photo-home-sweet-home-3d-letters.jpg",
                contacts);

        return Observable.just(organization1);
    }
}
