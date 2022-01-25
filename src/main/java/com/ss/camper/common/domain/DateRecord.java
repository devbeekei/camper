package com.ss.camper.common.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@ToString
@Getter
@Embeddable
public class DateRecord {

    @Column(name = "created", nullable = false, updatable = false, insertable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @Column(name = "modified", columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date modified;

}
