package com.ss.camper.user.ui;

import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.util.SecurityUtil;
import com.ss.camper.user.application.AgreeTermsService;
import com.ss.camper.user.ui.payload.AgreeTermsPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "agree-terms")
public class AgreeTermsController {

    private final AgreeTermsService agreeTermsService;

    @PostMapping(name = "약관 동의")
    public DefaultApiResponse agreeTerms(@RequestBody @Valid AgreeTermsPayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        agreeTermsService.agreeTerms(userId, request.getTermsType(), request.isAgree());
        return new DefaultApiResponse();
    }

}
