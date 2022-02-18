package com.ss.camper.store.application;

import com.ss.camper.common.util.S3Util;
import com.ss.camper.store.application.exception.NotFoundStoreException;
import com.ss.camper.store.domain.Store;
import com.ss.camper.store.domain.StoreRepository;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreProfileImageService {

    private final static String DIR_NAME = "StoreProfileImage";

    private final ModelMapper modelMapper;
    private final S3Util s3Util;
    private final StoreRepository storeRepository;

    @Transactional
    public void updateProfileImages(final long userId, final long storeId, final List<MultipartFile> multipartFiles) {
        final Store store = storeRepository.findByUserIdAndId(userId, storeId).orElseThrow(NotFoundStoreException::new);
        List<UploadFileDTO> uploadFileDTOList = s3Util.upload(DIR_NAME, multipartFiles);
        store.updateProfileImages(uploadFileDTOList);
    }

    @Transactional
    public void deleteProfileImages(final long userId, final long storeId, final Long[] fileIds) {
        final Store store = storeRepository.findByUserIdAndId(userId, storeId).orElseThrow(NotFoundStoreException::new);
        store.deleteProfileImages(fileIds);
    }

}
