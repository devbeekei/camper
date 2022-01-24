package com.ss.camper.common.util;

import com.ss.camper.oauth2.dto.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static long getUserId(){
        final UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Long.parseLong(userPrincipal.getName());
    }

}
