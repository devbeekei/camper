package com.ss.camper.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthCodeUtil {

    public String creatAuthCode(long id) {
        int length = 9;
        int start = (int)(Math.random() * (27 - length));
        return id + UUID.randomUUID().toString().replace("-", "").substring(start, start + length) + "" + new Date().getTime();
    }

}
