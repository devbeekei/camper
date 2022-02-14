package com.ss.camper.store.domain;

import com.ss.camper.uploadFile.domain.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuperBuilder
@Getter
@AllArgsConstructor
@DiscriminatorValue("STORE_PROFILE")
@Entity
public class StoreProfileImage extends UploadFile {

}
