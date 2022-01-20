package com.ss.camper.store.ui;

import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping(name = "매장 등록")
    public DefaultApiResponse registerStore(@Valid @RequestBody RegisterStorePayload.Request request) {
        storeService.registerStore(request.convertStoreDTO());
        return new DefaultApiResponse();
    }

    @PutMapping(name = "매장 정보 수정", value = "{storeId}")
    public DefaultApiResponse modifyStore(@PathVariable long storeId, @Valid @RequestBody ModifyStorePayload.Request request) {
        storeService.modifyStore(storeId, request.convertStoreDTO());
        return new DefaultApiResponse();
    }

    @GetMapping(name = "매장 정보 조회", value = "{storeId}")
    public DataApiResponse<StoreDTO> getStoreInfo(@PathVariable long storeId) {
        final StoreDTO storeDTO = storeService.getStoreInfo(storeId);
        return new DataApiResponse<>(storeDTO);
    }

    @GetMapping(name = "매장 목록 조회")
    public DataApiResponse<PageDTO<StoreListDTO>> getStoreListPage(@RequestParam int size, @RequestParam int page) {
        final PageDTO<StoreListDTO> storeList = storeService.getStoreListPage(size, page);
        return new DataApiResponse<>(storeList);
    }

}
