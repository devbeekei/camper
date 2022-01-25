package com.ss.camper.auth.ui;

import com.ss.camper.auth.application.AuthCodeService;
import com.ss.camper.auth.application.AuthService;
import com.ss.camper.auth.ui.payload.GetTokenPayload;
import com.ss.camper.auth.ui.payload.SignInPayload;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.user.application.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;
    private final AuthCodeService authCodeService;

    @GetMapping(name = "인증 Error", value = "/error")
    public String error(@RequestParam final String message) {
        return message;
    }

    @GetMapping(name = "인증 코드", value = "/code")
    public String code(@RequestParam final String code) {
        return "인증 코드 : " + code + "<br/>해당 인증 코드로 인증 토큰을 발급해주세요.";
    }

    @PostMapping(name = "이메일 로그인", value = "/authorization")
    public DataApiResponse<SignInPayload.Response> signIn(final @Valid @RequestBody SignInPayload.Request request) throws AuthenticationException {
        final UserInfoDTO userInfoDTO = authService.signIn(request.getEmail().trim(), request.getPassword().trim());
        final String code = authCodeService.issueAuthCode(userInfoDTO);
        final String token = authCodeService.issueAuthToken(code);
        return new DataApiResponse<>(new SignInPayload.Response(userInfoDTO, token));
    }

    @PostMapping(name = "인증 토큰 발급", value = "/token")
    public DataApiResponse<GetTokenPayload.Response> issueToken(final @Valid @RequestBody GetTokenPayload.Request request) {
        final String token = authCodeService.issueAuthToken(request.getCode());
        return new DataApiResponse<>(new GetTokenPayload.Response(token));
    }

}