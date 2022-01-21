package com.ss.camper.auth.ui;

import com.ss.camper.auth.application.AuthCodeService;
import com.ss.camper.auth.application.AuthService;
import com.ss.camper.auth.ui.payload.GetTokenPayload;
import com.ss.camper.auth.ui.payload.SignInPayload;
import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.oauth2.dto.UserDTO;
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
        return "error=" + message;
    }

    @PostMapping(name = "인증 토큰 발급", value = "/token")
    public DataApiResponse<String> issueToken(final @Valid @RequestBody GetTokenPayload.Request request) {
        final String token = authCodeService.issueAuthToken(request.getCode());
        return new DataApiResponse<>(token);
    }

    @PostMapping(name = "로그인", value = "/authorization")
    public DefaultApiResponse signIn(final @Valid @RequestBody SignInPayload.Request request) throws AuthenticationException {
        UserDTO userDTO = authService.signIn(request.getEmail().trim(), request.getPassword().trim());
        return new DefaultApiResponse();
    }

}