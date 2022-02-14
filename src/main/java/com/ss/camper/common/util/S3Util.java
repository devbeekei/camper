package com.ss.camper.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.uploadFile.exception.FileUploadFailException;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.assertj.core.util.Arrays;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final String serverUploadDir = "src/main/resources/upload";
    private final AmazonS3 amazonS3;
    private final ModelMapper modelMapper;

    @Setter
    @Getter
    @Builder
    private static class ConvertFileDTO {
        private String originName;
        private String uploadName;
        private String fullPath;
        private String path;
        private long size;
        private String ext;
        private File file;
    }

    public List<UploadFileDTO> upload(String dirName, List<MultipartFile> multipartFileList) {
        List<ConvertFileDTO> files = new ArrayList<>();
        try {
            // 파일 Convert
            for (MultipartFile multipartFile : multipartFileList) {
                File file = convert(multipartFile).orElseThrow(FileUploadFailException::new);
                String originFileName = multipartFile.getOriginalFilename();
                String uploadFileName =  file.getName();
                String path = dirName + "/" + uploadFileName;
                long size = file.length();
                String ext = originFileName != null ? originFileName.substring(originFileName.lastIndexOf(".") + 1).toUpperCase() : "";
                files.add(ConvertFileDTO.builder()
                        .originName(originFileName).uploadName(uploadFileName)
                        .path(path).size(size).ext(ext)
                        .file(file).build());
            }
            // S3 업로드
            if (files.size() > 0) {
                for (ConvertFileDTO file : files) {
                    amazonS3.putObject(new PutObjectRequest(bucket, file.getPath(), file.getFile()).withCannedAcl(CannedAccessControlList.PublicRead));
                    file.setFullPath(amazonS3.getUrl(bucket, file.getPath()).toString());
                }
            }
            // Convert 파일 삭제
            convertFileDelete();

            return modelMapper.map(files, new TypeToken<List<UploadFileDTO>>(){}.getType());
        } catch (IOException e) {
            convertFileDelete();
            e.printStackTrace();
            throw new FileUploadFailException();
        }
    }

    // S3 파일 업로드
    public UploadFileDTO upload(String dirName, MultipartFile multipartFile) {
        List<UploadFileDTO> uploadFileDTOList = upload(dirName, new ArrayList<>(){{ add(multipartFile); }});
        return uploadFileDTOList.get(0);
    }

    // S3 파일 삭제
    public void delete(String path) {
        amazonS3.deleteObject(bucket, path);
    }

    // 서버에 convert file 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        final File convertFile = new File(serverUploadDir + "/" + timestamp.getTime() + UUID.randomUUID() + "_" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    // 서버에 업로드 한 convert file 삭제
    private void convertFileDelete() {
        File path = new File(serverUploadDir);
        File[] fileList = path.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                file.delete();
            }
        }
    }

}
