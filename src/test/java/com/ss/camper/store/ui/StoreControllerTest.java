package com.ss.camper.store.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.payload.PageDTO;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ss.camper.common.ApiDocumentAttributes.storeTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.store.StoreMock.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest extends ControllerTest {

    @MockBean
    private StoreService storeService;

    @Test
    void 매장_등록() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        given(storeService.registerStore(any(StoreDTO.class))).willReturn(storeDTO);

        // When
        final RegisterStorePayload.Request request = RegisterStorePayload.Request.builder()
            .storeType(STORE_TYPE)
            .storeName(STORE_NAME)
            .zipCode(ADDRESS.getZipCode())
            .defaultAddress(ADDRESS.getDefaultAddress())
            .detailAddress(ADDRESS.getDetailAddress())
            .latitude(ADDRESS.getLatitude())
            .longitude(ADDRESS.getLongitude())
            .tel(TEL)
            .homepageUrl(HOMEPAGE_URL)
            .reservationUrl(RESERVATION_URL)
            .introduction(INTRODUCTION)
            .tags(new HashSet<>(){{
                add(TAG_TITLE1);
                add(TAG_TITLE2);
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
                    fieldWithPath("storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeTypeAttribute()),
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

    @Test
    void 매장_정보_수정() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        given(storeService.modifyStore(anyLong(), any(StoreDTO.class))).willReturn(storeDTO);

        // When
        final ModifyStorePayload.Request request = ModifyStorePayload.Request.builder()
            .storeName(STORE_NAME)
            .zipCode(ADDRESS.getZipCode())
            .defaultAddress(ADDRESS.getDefaultAddress())
            .detailAddress(ADDRESS.getDetailAddress())
            .latitude(ADDRESS.getLatitude())
            .longitude(ADDRESS.getLongitude())
            .tel(TEL)
            .homepageUrl(HOMEPAGE_URL)
            .reservationUrl(RESERVATION_URL)
            .introduction(INTRODUCTION)
            .tags(new HashSet<>(){{
                add(TAG_TITLE1);
                add(TAG_TITLE2);
            }})
            .build();
        final ResultActions result = mockMvc.perform(
            put("/store/{storeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("store/modify",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
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

    @Test
    void 매장_정보_조회() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, TAG_TITLE1));
            add(initStoreTagDTO(2L, TAG_TITLE2));
        }});
        given(storeService.getStoreInfo(anyLong())).willReturn(storeDTO);

        // When
        final ResultActions result = mockMvc.perform(
            get("/store/{storeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("store/info",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("storeId").description("매장 고유번호")
                ),
                responseFields(
                    dataResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("매장 고유번호"),
                        fieldWithPath("storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeTypeAttribute()),
                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("매장 명"),
                        fieldWithPath("address").type(JsonFieldType.OBJECT).description("매장 주소 정보"),
                        fieldWithPath("address.zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                        fieldWithPath("address.defaultAddress").type(JsonFieldType.STRING).description("기본 주소"),
                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING).optional().description("상세 주소"),
                        fieldWithPath("address.latitude").type(JsonFieldType.NUMBER).description("위도"),
                        fieldWithPath("address.longitude").type(JsonFieldType.NUMBER).description("경도"),
                        fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처"),
                        fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("홈페이지 URL"),
                        fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("예약 사이트 URL"),
                        fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("매장 소개"),
                        fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("태그") ,
                        fieldWithPath("tags[].id").type(JsonFieldType.NUMBER).optional().description("태그 고유번호"),
                        fieldWithPath("tags[].title").type(JsonFieldType.STRING).optional().description("태그 타이틀")
                    )
                )
            ));
    }

    @Test
    void 매장_목록_조회() throws Exception {
        // Given
        final List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{TAG_TITLE1, TAG_TITLE2}));
            add(initStoreListDTO(2L, new String[]{TAG_TITLE1, TAG_TITLE2}));
        }};
        final long size = 10;
        final long page = 1;
        PageDTO<StoreListDTO> storeListPage = new PageDTO<>(storeList, storeList.size(), size, page, 1);
        given(storeService.getStoreListPage(anyInt(), anyInt())).willReturn(storeListPage);

        // When
        final ResultActions result = mockMvc.perform(
            get("/store")
                .param("size", String.valueOf(size))
                .param("page", String.valueOf(page))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Then
        result.andExpect(status().isOk())
            .andDo(document("store/list",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                    parameterWithName("size").description("한 페이지에 보일 데이터 수"),
                    parameterWithName("page").description("조회할 페이지")
                ),
                responseFields(
                    pagingResponseFields(
                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("매장 고유번호"),
                        fieldWithPath("storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeTypeAttribute()),
                        fieldWithPath("storeName").type(JsonFieldType.STRING).description("매장 명"),
                        fieldWithPath("address").type(JsonFieldType.OBJECT).description("매장 주소 정보"),
                        fieldWithPath("address.zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                        fieldWithPath("address.defaultAddress").type(JsonFieldType.STRING).description("기본 주소"),
                        fieldWithPath("address.detailAddress").type(JsonFieldType.STRING).optional().description("상세 주소"),
                        fieldWithPath("address.latitude").type(JsonFieldType.NUMBER).description("위도"),
                        fieldWithPath("address.longitude").type(JsonFieldType.NUMBER).description("경도"),
                        fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처"),
                        fieldWithPath("homepageUrl").type(JsonFieldType.STRING).optional().description("홈페이지 URL"),
                        fieldWithPath("reservationUrl").type(JsonFieldType.STRING).optional().description("예약 사이트 URL"),
                        fieldWithPath("introduction").type(JsonFieldType.STRING).optional().description("매장 소개"),
                        fieldWithPath("tags[]").type(JsonFieldType.ARRAY).optional().description("태그")
                    )
                )
            ));
    }

}