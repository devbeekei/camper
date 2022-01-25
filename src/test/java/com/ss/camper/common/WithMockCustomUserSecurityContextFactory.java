package com.ss.camper.common;


import com.ss.camper.oauth2.dto.UserPrincipal;
import com.ss.camper.user.application.dto.UserInfoDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        final UserPrincipal userPrincipal = UserPrincipal.create(UserInfoDTO.builder().id(Long.parseLong(annotation.name())).email(annotation.username()).build());
        final Authentication auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}

