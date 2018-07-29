package com.nenton.trehgornyinpocket.data.storage.dto;

import java.util.Map;

public class OrganizationDto {
    private int id;
    private String title;
    private String description;
    private String imagesUrl;
    private Map<String, Contact> contacts;

    public OrganizationDto() {
        //For Firebase Database
    }

    public int getId() {
        return id;
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

    public Map<String, Contact> getContacts() {
        return contacts;
    }

    public enum ContactType {
        CONTACT_PHONE,
        CONTACT_EMAIL,
        CONTACT_TELEGRAM;

        ContactType() {
        }

        public String getName() {
            return this.name();
        }
    }

    public static class Contact {
        private ContactType type;
        private String contactWay;

        public Contact() {
        }

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
