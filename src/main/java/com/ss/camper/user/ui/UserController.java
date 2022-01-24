package com.ss.camper.user.ui;

import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.ui.payload.SignUpPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    @PostMapping(name = "사용자 회원 회원가입", value = "client")
    public DefaultApiResponse signUpClientUser(@Valid @RequestBody final SignUpPayload.Request request) {
        userService.signUpClientUser(request.convertUserDTO(), request.getPassword(), request.getPasswordCheck());
        return new DefaultApiResponse();
    }

    @PostMapping(name = "사업자 회원 회원가입", value = "business")
    public DefaultApiResponse signUpBusinessUser(@Valid @RequestBody final SignUpPayload.Request request) {
        userService.signUpBusinessUser(request.convertUserDTO(), request.getPassword(), request.getPasswordCheck());
        return new DefaultApiResponse();
    }

}
