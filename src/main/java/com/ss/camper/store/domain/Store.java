package com.ss.camper.store.domain;

import com.ss.camper.uploadFile.dto.UploadFileDTO;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString
@Getter
@Builder
@Entity
@Table(name = "store")
@Where(clause = "deleted IS NULL")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "AUTO_INCREMENT")
    @Column(name = "store_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "store_name", length = 100, nullable = false)
    private String storeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_type", length = 30, nullable = false)
    private StoreType storeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "store_status", length = 30, nullable = false, columnDefinition = "VARCHAR(30) NOT NULL DEFAULT 'OPEN'")
    private StoreStatus storeStatus;

    @Embedded
    private Address address;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "homepage_url", columnDefinition = "TEXT")
    private String homepageUrl;

    @Column(name = "reservation_url", columnDefinition = "TEXT")
    private String reservationUrl;

    @Column(name = "introduction", columnDefinition = "TEXT")
    private String introduction;

    @Convert(converter = StoreOpeningDaysConverter.class)
    @Column(name = "opening_days")
    private Set<DayOfWeek> openingDays;

    @Temporal(TemporalType.TIME)
    @Column(name = "open_time", columnDefinition = "TIME DEFAULT NULL")
    private Date openTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "close_time", columnDefinition = "TIME DEFAULT NULL")
    private Date closeTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified", insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Date modified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted", columnDefinition = "DATETIME DEFAULT NULL")
    private Date deleted;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinTable(name = "tag_of_store", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "store_tag_id"))
    private Set<StoreTag> tags;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OrderBy(value = "id ASC")
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinTable(name = "store_profile_image", joinColumns = @JoinColumn(name = "store_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<StoreProfileImage> profileImages;

    @Version
    private Long ver;

    public void updateInfo(StoreStatus storeStatus, String storeName, Address address, String tel, String homepageUrl, String reservationUrl, String introduction, Set<DayOfWeek> openingDays, Date openTime, Date closeTime) {
        this.storeStatus = storeStatus;
        this.storeName = storeName;
        this.address = address;
        this.tel = tel;
        this.homepageUrl = homepageUrl;
        this.reservationUrl = reservationUrl;
        this.introduction = introduction;
        this.openingDays = openingDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public void updateTags(Set<StoreTag> updateTags) {
        this.tags = updateTags;
        if (updateTags != null) {
            StoreTag[] titles = updateTags.toArray(StoreTag[]::new);
            tags.removeIf(tag -> !Arrays.asList(titles).contains(tag));
        }
    }

    public void delete() {
        this.deleted = new Date();
    }

    public void updateProfileImages(List<UploadFileDTO> uploadFileDTOList) {
        for (UploadFileDTO uploadFileDTO : uploadFileDTOList) {
            if (uploadFileDTO.getId() != null) continue;
            if (this.profileImages == null) this.profileImages = new ArrayList<>();
            this.profileImages.add(StoreProfileImage.builder()
                    .originName(uploadFileDTO.getOriginName())
                    .uploadName(uploadFileDTO.getUploadName())
                    .fullPath(uploadFileDTO.getFullPath())
                    .path(uploadFileDTO.getPath())
                    .size(uploadFileDTO.getSize())
                    .ext(uploadFileDTO.getExt())
                    .build());
        }
    }

    public void deleteProfileImages(Long[] ids) {
        if (ids == null || ids.length == 0) return;
        if (this.profileImages == null || this.profileImages.size() == 0) return;
        this.profileImages.removeIf(o -> Arrays.asList(ids).contains(o.getId()));
    }

}
