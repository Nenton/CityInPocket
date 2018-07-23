package com.nenton.trehgornyinpocket.data.storage.dto;

import java.util.List;

public class OrganizationDto {
    private String title;
    private String description;
    private String imagesUrl;
    private List<Contact> contacts;

    public OrganizationDto(String title, String description, String imagesUrl, List<Contact> contacts) {
        this.title = title;
        this.description = description;
        this.imagesUrl = imagesUrl;
        this.contacts = contacts;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagesUrl() {
        return imagesUrl;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public enum ContactType {
        CONTACT_PHONE,
        CONTACT_EMAIL,
        CONTACT_TELEGRAM
    }

    public static class Contact {
        private ContactType type;
        private String contactWay;

        public Contact(ContactType type, String contactWay) {
            this.type = type;
            this.contactWay = contactWay;
        }

        public ContactType getType() {
            return type;
        }

        public String getContactWay() {
            return contactWay;
        }
    }
}
