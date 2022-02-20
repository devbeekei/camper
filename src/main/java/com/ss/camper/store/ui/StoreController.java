package com.ss.camper.store.ui;

import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.common.util.SecurityUtil;
import com.ss.camper.store.application.StoreProfileImageService;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.domain.StoreType;
import com.ss.camper.store.ui.payload.DeleteStoreProfileImagesPayload;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.MultipartFileCountValid;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreProfileImageService storeProfileImageService;

    @PostMapping(name = "매장 등록")
    public DefaultApiResponse registerStore(@Valid @RequestBody final RegisterStorePayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        storeService.registerStore(userId, request.convertStoreDTO());
        return new DefaultApiResponse();
    }

    @PutMapping(name = "매장 정보 수정", value = "{storeId}")
    public DefaultApiResponse modifyStore(@PathVariable final long storeId,
                                          @Valid @RequestBody final ModifyStorePayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        storeService.modifyStore(userId, storeId, request.convertStoreDTO());
        return new DefaultApiResponse();
    }

    @DeleteMapping(name = "매장 삭제", value = "{storeId}")
    public DefaultApiResponse deleteStore(@PathVariable final long storeId) {
        final long userId = SecurityUtil.getUserId();
        storeService.deleteStore(userId, storeId);
        return new DefaultApiResponse();
    }

    @GetMapping(name = "매장 정보 조회", value = "{storeId}")
    public DataApiResponse<StoreDTO> getStoreInfo(@PathVariable final long storeId) {
        final StoreDTO storeDTO = storeService.getStoreInfo(storeId);
        return new DataApiResponse<>(storeDTO);
    }

    @GetMapping(name = "회원 별 매장 목록 조회", value = "user/{userId}")
    public DataApiResponse<PageDTO<StoreListDTO>> getStoreListByUserId(@PathVariable final long userId,
                                                                       @RequestParam final int size,
                                                                       @RequestParam final int page) {
        final PageDTO<StoreListDTO> storeList = storeService.getStoreListByUserId(userId, size, page);
        return new DataApiResponse<>(storeList);
    }

    @GetMapping(name = "매장 유형 별 매장 목록 조회", value = "type/{type}")
    public DataApiResponse<PageDTO<StoreListDTO>> getStoreListByType(@PathVariable final StoreType type,
                                                                     @RequestParam final int size,
                                                                     @RequestParam final int page) {
        final PageDTO<StoreListDTO> storeList = storeService.getStoreListByType(type, size, page);
        return new DataApiResponse<>(storeList);
    }

    @PostMapping(name = "프로필 이미지 등록", value = "profile-image/{storeId}")
    public DefaultApiResponse updateProfileImage(@PathVariable final long storeId,
                                                 @RequestPart(value="files", required = false)
                                                 @MultipartFileCountValid(max = 10) final List<MultipartFile> multipartFiles) {
        final long userId = SecurityUtil.getUserId();
        storeProfileImageService.updateProfileImages(userId, storeId, multipartFiles);
        return new DefaultApiResponse();
    }

    @DeleteMapping(name = "프로필 이미지 삭제", value = "profile-image/{storeId}")
    public DefaultApiResponse deleteProfileImages(@PathVariable final long storeId,
                                                  @RequestBody @Valid final DeleteStoreProfileImagesPayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        storeProfileImageService.deleteProfileImages(userId, storeId, request.getFileIds());
        return new DefaultApiResponse();
    }

}
