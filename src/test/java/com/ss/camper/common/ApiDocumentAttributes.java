package com.ss.camper.common;

import com.ss.camper.store.domain.StoreStatus;
import com.ss.camper.store.domain.StoreType;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.domain.UserType;
import org.springframework.restdocs.snippet.Attributes;

import java.time.DayOfWeek;

import static org.springframework.restdocs.snippet.Attributes.key;

public class ApiDocumentAttributes {

    static Attributes.Attribute attribute(String value) {
        return key("attribute").value(value);
    }

    public static Attributes.Attribute userTypeAttribute() {
        StringBuilder value = new StringBuilder();
        for (UserType userType : UserType.values()) {
            value.append(userType).append(" : ").append(userType.getName()).append(" / ");
        }
        return attribute(value.substring(0, value.length() - 3));
    }

    public static Attributes.Attribute openingDaysAttribute() {
        StringBuilder value = new StringBuilder();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            value.append(dayOfWeek).append(" / ");
        }
        return attribute(value.substring(0, value.length() - 3));
    }

    public static Attributes.Attribute storeTypeAttribute() {
        StringBuilder value = new StringBuilder();
        for (StoreType storeType : StoreType.values()) {
            value.append(storeType).append(" : ").append(storeType.getName()).append(" / ");
        }
        return attribute(value.substring(0, value.length() - 3));
    }
    public static Attributes.Attribute storeStatusAttribute() {
        StringBuilder value = new StringBuilder();
        for (StoreStatus storeStatus : StoreStatus.values()) {
            value.append(storeStatus).append(" : ").append(storeStatus.getName()).append(" / ");
        }
        return attribute(value.substring(0, value.length() - 3));
    }

}
