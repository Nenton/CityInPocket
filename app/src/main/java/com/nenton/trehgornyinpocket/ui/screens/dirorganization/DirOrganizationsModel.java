package com.nenton.trehgornyinpocket.ui.screens.dirorganization;

import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;
import com.nenton.trehgornyinpocket.mvp.models.AbstractModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class DirOrganizationsModel extends AbstractModel {
    public Observable<OrganizationDto> getOrganizationAllObs() {
// TODO: 23.07.2018 implement me
        List<OrganizationDto.Contact> contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_PHONE, "+7-999-99-99"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "test@test.test"));
        OrganizationDto organization1 = new OrganizationDto("Home",
                "Home sweet home!",
                "https://st.depositphotos.com/1472772/2580/i/950/depositphotos_25801069-stock-photo-home-sweet-home-3d-letters.jpg",
                contacts);

        contacts = new ArrayList<>();
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_EMAIL, "prob@prob.prob"));
        contacts.add(new OrganizationDto.Contact(OrganizationDto.ContactType.CONTACT_TELEGRAM, "@test"));
        OrganizationDto organization2 = new OrganizationDto("Job",
                "Job =>",
                "https://conceptdraw.com/a1706c3/p32/preview/640/pict--job-vacancy-business-people---vector-stencils-library.png--diagram-flowchart-example.png",
                contacts);
        return Observable.just(organization1, organization2);
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
