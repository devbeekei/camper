package com.ss.camper.user.domain;

import com.ss.camper.common.domain.DateRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

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

    @Column(name = "withdrawal", columnDefinition = "TINYINT DEFAULT 0")
    private boolean withdrawal;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private SocialAuth socialAuth;

    @OrderBy(value = "id DESC")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private List<AgreeTermsHistory> agreeTermsHistories;

    @Transient
    private AgreeTermsHistory useAgreeTerms;
    public AgreeTermsHistory getUseAgreeTerms() {
        if (this.agreeTermsHistories == null) return null;
        Optional<AgreeTermsHistory> agreeTermsHistory = this.agreeTermsHistories.stream()
                .filter(a -> a.getTermsType().equals(TermsType.USE)).findFirst();
        return agreeTermsHistory.orElse(null);
    }

    @Transient
    private AgreeTermsHistory privacyPolicyAgreeTerms;
    public AgreeTermsHistory getPrivacyPolicyAgreeTerms() {
        if (this.agreeTermsHistories == null) return null;
        Optional<AgreeTermsHistory> agreeTermsHistory = this.agreeTermsHistories.stream()
                .filter(a -> a.getTermsType().equals(TermsType.PRIVACY_POLICY)).findFirst();
        return agreeTermsHistory.orElse(null);
    }

    @Embedded
    private DateRecord dateRecord;

    public void updateInfo(String nickname, String phone) {
        this.nickname = nickname;
        this.phone = phone;
    }

    public void withdraw() {
        this.withdrawal = true;
    }

}
