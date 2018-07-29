package com.nenton.trehgornyinpocket.data.storage.room;

import com.nenton.trehgornyinpocket.data.storage.dto.OrganizationDto;

public class ContactEntity {

    private String contact;
    private ContactType type;

    public ContactEntity(String contact, ContactType type) {
        this.contact = contact;
        this.type = type;
    }


    public ContactEntity(OrganizationDto.Contact contact) {
        this.contact = contact.getContactWay();
        switch (contact.getType()) {
            case CONTACT_TELEGRAM:
                this.type = ContactType.CONTACT_TELEGRAM;
                break;
            case CONTACT_PHONE:
                this.type = ContactType.CONTACT_PHONE;
                break;
            case CONTACT_EMAIL:
                this.type = ContactType.CONTACT_EMAIL;
                break;
            default:
                this.type = ContactType.CONTACT_PHONE;
                break;
        }
    }

    public String getContact() {
        return contact;
    }

    public ContactType getType() {
        return type;
    }
}
