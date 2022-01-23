package com.ss.camper.user.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "social_auth")
public class SocialAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_auth_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider", length = 20, nullable = false)
    private SocialProvider provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "birthday", length = 10)
    private String birthday;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Embedded
    private DateRecord dateRecord;

    public void infoUpdate(String name, String phone, String birthday, String profileImage) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }

}