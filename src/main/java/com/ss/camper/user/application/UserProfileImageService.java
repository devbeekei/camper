package com.ss.camper.user.application;

import com.ss.camper.common.util.S3Util;
import com.ss.camper.uploadFile.dto.UploadFileDTO;
import com.ss.camper.user.application.exception.NotFoundUserException;
import com.ss.camper.user.domain.User;
import com.ss.camper.user.domain.UserProfileImage;
import com.ss.camper.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserProfileImageService {

    private final static String DIR_NAME = "UserProfileImage";

    private final ModelMapper modelMapper;
    private final S3Util s3Util;
    private final UserRepository userRepository;

    @Transactional
    public void updateProfileImage(final long userId, final MultipartFile multipartFile) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        final UploadFileDTO uploadFileDTO = s3Util.upload(DIR_NAME, multipartFile);
        user.clearProfileImage();
        user.updateProfileImage(uploadFileDTO);
    }

    @Transactional
    public void deleteProfileImage(final long userId) {
        final User user = userRepository.findById(userId).orElseThrow(NotFoundUserException::new);
        user.clearProfileImage();
    }

}
