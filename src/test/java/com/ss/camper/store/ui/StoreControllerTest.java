package com.ss.camper.store.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.LinkedHashSet;

import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.store.StoreMockData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
public class StoreControllerTest extends ControllerTest {

    @MockBean
    private StoreService storeService;

    @Test
    @DisplayName("매장 등록")
    public void registerStore() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, storeType, new LinkedHashSet<>(){{
            add(initStoreTagDTO(1L, storeType, tagTitle1));
            add(initStoreTagDTO(2L, storeType, tagTitle2));
        }});
        given(storeService.register(any(StoreDTO.class))).willReturn(storeDTO);

        // When
        final RegisterStorePayload.Request request = RegisterStorePayload.Request.builder()
            .storeType(storeType)
            .storeName(storeName)
            .zipCode(address.getZipCode())
            .defaultAddress(address.getDefaultAddress())
            .detailAddress(address.getDetailAddress())
            .latitude(address.getLatitude())
            .longitude(address.getLongitude())
            .tel(tel)
            .homepageUrl(homepageUrl)
            .reservationUrl(reservationUrl)
            .introduction(introduction)
            .tags(new LinkedHashSet<>(){{
                add(tagTitle1);
                add(tagTitle2);
            }})
            .build();
        final ResultActions result = mockMvc.perform(
            post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("store/register",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("storeType").type(JsonFieldType.STRING).description("매장 유형"),
                    fieldWithPath("storeName").type(JsonFieldType.STRING).description("매장 명"),
                    fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                    fieldWithPath("defaultAddress").type(JsonFieldType.STRING).description("기본 주소"),
                    fieldWithPath("detailAddress").type(JsonFieldType.STRING).optional().description("상세 주소"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도"),
                    fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처"),
                    fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("홈페이지 URL"),
                    fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("예약 사이트 URL"),
                    fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("매장 소개"),
                    fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("태그")
                ),
                responseFields(
                    defaultResponseFields()
                )
            ));
    }

}