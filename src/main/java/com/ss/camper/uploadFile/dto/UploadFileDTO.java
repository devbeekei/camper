package com.ss.camper.uploadFile.dto;

import lombok.*;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO {
    private String originName;
    private String uploadName;
    private String fullPath;
    private String path;
    private long size;
    private String ext;
}