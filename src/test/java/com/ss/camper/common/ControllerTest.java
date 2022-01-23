package com.ss.camper.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.camper.common.util.JWTUtil;
import com.ss.camper.oauth2.application.CustomOAuth2UserService;
import com.ss.camper.oauth2.application.CustomUserDetailsService;
import com.ss.camper.oauth2.application.HttpCookieOAuth2AuthorizationRequestRepository;
import com.ss.camper.oauth2.handler.CustomAuthenticationEntryPoint;
import com.ss.camper.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.ss.camper.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class ControllerTest {

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @MockBean
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @MockBean
    private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

}
