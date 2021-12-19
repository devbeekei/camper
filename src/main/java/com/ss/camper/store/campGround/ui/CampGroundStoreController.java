package com.ss.camper.store.campGround.ui;

import com.ss.camper.common.payload.ApiResponse;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.store.campGround.application.CampGroundStoreService;
import com.ss.camper.store.campGround.application.dto.CampGroundStoreDTO;
import com.ss.camper.store.campGround.ui.payload.GetCampGroundStorePayload;
import com.ss.camper.store.campGround.ui.payload.RegisterCampGroundStorePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "camp-ground/store")
@RequiredArgsConstructor
public class CampGroundStoreController {

    private final CampGroundStoreService campGroundStoreService;

    @PostMapping(name = "캠핑장 등록")
    public ApiResponse registerCampGroundStore(@Valid @RequestBody RegisterCampGroundStorePayload.Request request) {
        campGroundStoreService.register(1, request.getCampGroundStoreDTO());
        return new ApiResponse();
    }

    @PutMapping(name = "캠핑장 정보 수정")
    public void modifyCampGroundStore() {

    }

    @GetMapping(name = "캠핑장 정보 조회", value = "{id}")
    public DataApiResponse<GetCampGroundStorePayload.Response>getCampGroundStore(@PathVariable long id) {
        final CampGroundStoreDTO campGroundStoreDTO = campGroundStoreService.getInfo(id);
        return new DataApiResponse<>(new GetCampGroundStorePayload.Response(campGroundStoreDTO));
    }

    @GetMapping(name = "캠핑장 목록 조회")
    public void getCampGroundStoreList() {

    }

}
