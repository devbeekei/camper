package com.ss.camper.user.domain;

import com.ss.camper.uploadFile.domain.UploadFile;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;


@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("USER_PROFILE")
@SecondaryTable(name = "user_profile_image", pkJoinColumns = @PrimaryKeyJoinColumn(name = "file_id"))
@Entity
public class UserProfileImage extends UploadFile {

    @Column(table = "user_profile_image", name = "user_id", nullable = false)
    private Long userId;

//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(table = "user_profile_image", name = "user_id", nullable = false)
//    private User user;

//    protected void disconnectUser() {
//        this.user = null;
//    }

}
