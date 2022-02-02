package com.ss.camper.user.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAuth {

    @Enumerated(value = EnumType.STRING)
    @Column(table = "social_auth", name = "provider", length = 20, nullable = false)
    private SocialProvider provider;

    @Column(table = "social_auth", name = "provider_id", nullable = false)
    private String providerId;

    @Column(table = "social_auth", name = "email", length = 200, nullable = false)
    private String email;

    @Column(table = "social_auth", name = "name", length = 50, nullable = false)
    private String name;

    @Column(table = "social_auth", name = "birthday", length = 10)
    private String birthday;

    @Column(table = "social_auth", name = "phone", length = 20)
    private String phone;

    @Column(table = "social_auth", name = "profile_image")
    private String profileImage;

    public void infoUpdate(String name, String phone, String birthday, String profileImage) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }

}
