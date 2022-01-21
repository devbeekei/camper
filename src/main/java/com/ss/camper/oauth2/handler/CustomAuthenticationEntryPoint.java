package com.ss.camper.oauth2.handler;

import com.ss.camper.common.payload.ApiResponseType;
import com.ss.camper.common.payload.DefaultApiResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        DefaultApiResponse.response(ApiResponseType.UNAUTHORIZED);
    }

}
