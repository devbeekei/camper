package com.ss.camper.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_code", uniqueConstraints = {
        @UniqueConstraint(columnNames = "auth_code")
})
public class AuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_code_id")
    private Long id;

    @Column(name = "auth_code", nullable = false)
    private String authCode;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "token", nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired", nullable = false)
    private Date expired;

    @Column(name = "return_uri")
    private String returnUri;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issued")
    private Date issued;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    public String issueToken() {
        this.authCode = "[ISSUED]" + this.authCode;
        this.issued = new Date();
        return this.token;
    }

}
