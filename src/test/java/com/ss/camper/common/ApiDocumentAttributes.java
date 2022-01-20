package com.ss.camper.common;

import com.ss.camper.store.domain.StoreType;
import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public class ApiDocumentAttributes {

    static Attributes.Attribute attribute(String value) {
        return key("attribute").value(value);
    }

    public static Attributes.Attribute storeTypeAttribute() {
        StringBuilder value = new StringBuilder();
        for (StoreType storeType : StoreType.values()) {
            value.append(storeType).append(" : ").append(storeType.getName()).append(" / ");
        }
        return attribute(value.substring(0, value.length() - 3));
    }

}
