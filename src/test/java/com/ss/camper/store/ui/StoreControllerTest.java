package com.ss.camper.store.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.dto.StoreDTO;
import com.ss.camper.store.application.dto.StoreListDTO;
import com.ss.camper.store.ui.payload.ModifyStorePayload;
import com.ss.camper.store.ui.payload.RegisterStorePayload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.ss.camper.common.ApiDocumentAttributes.storeType;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static com.ss.camper.store.StoreMockData.address;
import static com.ss.camper.store.StoreMockData.homepageUrl;
import static com.ss.camper.store.StoreMockData.initStoreDTO;
import static com.ss.camper.store.StoreMockData.initStoreListDTO;
import static com.ss.camper.store.StoreMockData.initStoreTagDTO;
import static com.ss.camper.store.StoreMockData.introduction;
import static com.ss.camper.store.StoreMockData.reservationUrl;
import static com.ss.camper.store.StoreMockData.storeName;
import static com.ss.camper.store.StoreMockData.storeType;
import static com.ss.camper.store.StoreMockData.tagTitle1;
import static com.ss.camper.store.StoreMockData.tagTitle2;
import static com.ss.camper.store.StoreMockData.tel;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
public class StoreControllerTest extends ControllerTest {

    @SpyBean
    private ModelMapper modelMapper;

    @MockBean
    private StoreService storeService;

    @Test
    @DisplayName("매장 등록")
    public void registerStore() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, tagTitle1));
            add(initStoreTagDTO(2L, tagTitle2));
        }});
        given(storeService.registerStore(any(StoreDTO.class))).willReturn(storeDTO);

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
            .tags(new HashSet<>(){{
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
                    fieldWithPath("storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeType()),
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
    @DisplayName("매장 정보 수정")
    public void modifyStore() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, tagTitle1));
            add(initStoreTagDTO(2L, tagTitle2));
        }});
        given(storeService.modifyStore(anyLong(), any(StoreDTO.class))).willReturn(storeDTO);

        // When
        final ModifyStorePayload.Request request = ModifyStorePayload.Request.builder()
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
            .tags(new HashSet<>(){{
                add(tagTitle1);
                add(tagTitle2);
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
    @DisplayName("매장 정보 조회")
    public void getStoreInfo() throws Exception {
        // Given
        final StoreDTO storeDTO = initStoreDTO(1L, new HashSet<>(){{
            add(initStoreTagDTO(1L, tagTitle1));
            add(initStoreTagDTO(2L, tagTitle2));
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
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("매장 고유번호"),
                        fieldWithPath("data.storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeType()),
                        fieldWithPath("data.storeName").type(JsonFieldType.STRING).description("매장 명"),
                        fieldWithPath("data.address").type(JsonFieldType.OBJECT).description("매장 주소 정보"),
                        fieldWithPath("data.address.zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                        fieldWithPath("data.address.defaultAddress").type(JsonFieldType.STRING).description("기본 주소"),
                        fieldWithPath("data.address.detailAddress").type(JsonFieldType.STRING).optional().description("상세 주소"),
                        fieldWithPath("data.address.latitude").type(JsonFieldType.NUMBER).description("위도"),
                        fieldWithPath("data.address.longitude").type(JsonFieldType.NUMBER).description("경도"),
                        fieldWithPath("data.tel").type(JsonFieldType.STRING).description("연락처"),
                        fieldWithPath("data.homepageUrl").type(JsonFieldType.STRING).optional().description("홈페이지 URL"),
                        fieldWithPath("data.reservationUrl").type(JsonFieldType.STRING).optional().description("예약 사이트 URL"),
                        fieldWithPath("data.introduction").type(JsonFieldType.STRING).optional().description("매장 소개"),
                        fieldWithPath("data.tags[]").type(JsonFieldType.ARRAY).optional().description("태그") ,
                        fieldWithPath("data.tags[].id").type(JsonFieldType.NUMBER).optional().description("태그 고유번호"),
                        fieldWithPath("data.tags[].title").type(JsonFieldType.STRING).optional().description("태그 타이틀")
                    )
                )
            ));
    }

    @Test
    @DisplayName("매장 목록 조회")
    public void getStoreListPage() throws Exception {
        // Given
        List<StoreListDTO> storeList = new ArrayList<>(){{
            add(initStoreListDTO(1L, new String[]{tagTitle1, tagTitle2}));
            add(initStoreListDTO(2L, new String[]{tagTitle1, tagTitle2}));
        }};
        Page<StoreListDTO> storeListPage = new PageImpl<>(storeList);
        given(storeService.getStoreListPage(anyInt(), anyInt())).willReturn(storeListPage);

        // When
        final ResultActions result = mockMvc.perform(
            get("/store")
                .param("size", "10")
                .param("page", "1")
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
                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("매장 고유번호"),
                        fieldWithPath("data[].storeType").type(JsonFieldType.STRING).description("매장 유형").attributes(storeType()),
                        fieldWithPath("data[].storeName").type(JsonFieldType.STRING).description("매장 명"),
                        fieldWithPath("data[].address").type(JsonFieldType.OBJECT).description("매장 주소 정보"),
                        fieldWithPath("data[].address.zipCode").type(JsonFieldType.STRING).description("우편 번호"),
                        fieldWithPath("data[].address.defaultAddress").type(JsonFieldType.STRING).description("기본 주소"),
                        fieldWithPath("data[].address.detailAddress").type(JsonFieldType.STRING).optional().description("상세 주소"),
                        fieldWithPath("data[].address.latitude").type(JsonFieldType.NUMBER).description("위도"),
                        fieldWithPath("data[].address.longitude").type(JsonFieldType.NUMBER).description("경도"),
                        fieldWithPath("data[].tel").type(JsonFieldType.STRING).description("연락처"),
                        fieldWithPath("data[].homepageUrl").type(JsonFieldType.STRING).optional().description("홈페이지 URL"),
                        fieldWithPath("data[].reservationUrl").type(JsonFieldType.STRING).optional().description("예약 사이트 URL"),
                        fieldWithPath("data[].introduction").type(JsonFieldType.STRING).optional().description("매장 소개"),
                        fieldWithPath("data[].tags[]").type(JsonFieldType.ARRAY).optional().description("태그")
                    )
                )
            ));
    }

}