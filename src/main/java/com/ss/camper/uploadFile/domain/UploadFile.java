package com.ss.camper.uploadFile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "file_type")
@Entity
@Table(name = "upload_file")
public abstract class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", length = 50, nullable = false, insertable = false, updatable = false)
    private FileType fileType;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "upload_name", nullable = false)
    private String uploadName;

    @Column(name = "full_path", columnDefinition = "TEXT", nullable = false)
    private String fullPath;

    @Column(name = "path", columnDefinition = "TEXT", nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "ext", length = 10, nullable = false)
    private String ext;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false, insertable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date created;

}