package com.ss.camper.user.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Getter
@SuperBuilder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", columnDefinition = "VARCHAR(30)")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 30, nullable = false, insertable = false, updatable = false)
    private UserType userType;

    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", length = 100, nullable = false)
    private String nickname;

    @Column(name = "phone", length = 50)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private SocialAuth socialAuth;

    @Embedded
    private DateRecord dateRecord;

}
