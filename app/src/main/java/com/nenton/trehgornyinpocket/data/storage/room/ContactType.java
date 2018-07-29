package com.nenton.trehgornyinpocket.data.storage.room;

import java.io.Serializable;

public enum ContactType implements Serializable {
    CONTACT_PHONE,
    CONTACT_EMAIL,
    CONTACT_TELEGRAM;

    public String getType() {
        return this.name();
    }
}
