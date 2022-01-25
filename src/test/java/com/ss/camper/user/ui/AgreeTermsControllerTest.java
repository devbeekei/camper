package com.ss.camper.user.ui;

import com.ss.camper.common.ControllerTest;
import com.ss.camper.common.WithMockCustomUser;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.user.application.AgreeTermsService;
import com.ss.camper.user.domain.TermsType;
import com.ss.camper.user.ui.payload.AgreeTermsPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.ss.camper.common.ApiDocumentAttributes.termsTypeAttribute;
import static com.ss.camper.common.ApiDocumentUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgreeTermsController.class)
class AgreeTermsControllerTest extends ControllerTest {

    @MockBean
    private AgreeTermsService agreeTermsService;

    @Test
    @WithMockCustomUser
    void 약관_동의() throws Exception {
        // Given
        willDoNothing().given(agreeTermsService).agreeTerms(anyLong(), any(TermsType.class), anyBoolean());

        // When
        final AgreeTermsPayload.Request request = new AgreeTermsPayload.Request(TermsType.USE, true);
        final ResultActions result = mockMvc.perform(
                post("/agree-terms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .header(JWTUtil.AUTHORIZATION_HEADER, JWTUtil.BEARER_PREFIX + "{token}")
        );

        // Then
        result.andExpect(status().isOk())
                .andDo(document("agree-terms/register",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("termsType").type(JsonFieldType.STRING).description("약관 유형").attributes(termsTypeAttribute()),
                                fieldWithPath("agree").type(JsonFieldType.BOOLEAN).description("동의 여부")
                        ),
                        responseFields(
                                defaultResponseFields()
                        )
                ));
    }

}