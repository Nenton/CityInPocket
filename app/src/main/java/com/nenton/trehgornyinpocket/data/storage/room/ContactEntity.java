package com.nenton.trehgornyinpocket.data.storage.room;

public class ContactEntity {

    private String contact;
    private ContactType type;

    public ContactEntity(String contact, ContactType type) {
        this.contact = contact;
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public ContactType getType() {
        return type;
    }
}
