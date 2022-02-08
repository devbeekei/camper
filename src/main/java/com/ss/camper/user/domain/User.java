package com.ss.camper.user.domain;

import com.ss.camper.uploadFile.domain.UploadFile;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@SuperBuilder
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", columnDefinition = "VARCHAR(30)")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SecondaryTable(name = "social_auth", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id"))
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 30, nullable = false, insertable = false, updatable = false)
    private UserType userType;

    @Column(name = "email", length = 200, nullable = false, updatable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", length = 100, nullable = false)
    private String nickname;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "withdrawal", columnDefinition = "TINYINT DEFAULT 0")
    private boolean withdrawal;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinTable(name = "user_profile_image", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_id", insertable = false, updatable = false))
    @JoinColumn(table = "user_profile_image", name = "user_id", referencedColumnName = "user_id")
    private UserProfileImage profileImage;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy(value = "id DESC")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<AgreeTermsHistory> agreeTermsHistories;

    @Transient
    private AgreeTermsHistory useAgreeTerms;

    @Transient
    private AgreeTermsHistory privacyPolicyAgreeTerms;

    @Embedded
    private SocialAuth socialAuth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date modified;

    @Version
    private Long ver;

    public AgreeTermsHistory getUseAgreeTerms() {
        if (this.agreeTermsHistories == null) return null;
        Optional<AgreeTermsHistory> agreeTermsHistory = this.agreeTermsHistories.stream()
            .filter(a -> a.getTermsType().equals(TermsType.USE)).findFirst();
        return agreeTermsHistory.orElse(null);
    }

    public AgreeTermsHistory getPrivacyPolicyAgreeTerms() {
        if (this.agreeTermsHistories == null) return null;
        Optional<AgreeTermsHistory> agreeTermsHistory = this.agreeTermsHistories.stream()
            .filter(a -> a.getTermsType().equals(TermsType.PRIVACY_POLICY)).findFirst();
        return agreeTermsHistory.orElse(null);
    }

    public void updateInfo(String nickname, String phone) {
        this.nickname = nickname;
        this.phone = phone;
    }

    public void withdraw() {
        this.withdrawal = true;
    }

    public void agreeTerms(TermsType termsType, boolean agree) {
        if (this.agreeTermsHistories == null) this.agreeTermsHistories = new ArrayList<>();
        this.agreeTermsHistories.add(AgreeTermsHistory.builder()
            .user(this)
            .termsType(termsType)
            .agree(agree)
            .build());
    }

    public void clearProfileImage() {
        this.profileImage = null;
    }

    public void updateProfileImage(UploadFileDTO uploadFileDTO) {
        this.profileImage = UserProfileImage.builder()
                .originName(uploadFileDTO.getOriginName())
                .uploadName(uploadFileDTO.getUploadName())
                .fullPath(uploadFileDTO.getFullPath())
                .path(uploadFileDTO.getPath())
                .size(uploadFileDTO.getSize())
                .ext(uploadFileDTO.getExt())
                .build();
    }

}
