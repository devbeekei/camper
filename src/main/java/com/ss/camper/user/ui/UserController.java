package com.ss.camper.user.ui;

import com.ss.camper.common.payload.DataApiResponse;
import com.ss.camper.common.payload.DefaultApiResponse;
import com.ss.camper.common.util.SecurityUtil;
import com.ss.camper.oauth2.dto.UserDTO;
import com.ss.camper.user.application.UserService;
import com.ss.camper.user.ui.payload.SignUpPayload;
import com.ss.camper.user.ui.payload.UpdateUserInfoPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(name = "회원 정보 조회")
    public DataApiResponse<UserDTO> getUserInfo() {
        final long userId = SecurityUtil.getUserId();
        final UserDTO userDTO = userService.getUserInfo(userId);
        return new DataApiResponse<>(userDTO);
    }

    @PutMapping(name = "회원 정보 수정")
    public DataApiResponse<UserDTO> updateUserInfo(@Valid @RequestBody final UpdateUserInfoPayload.Request request) {
        final long userId = SecurityUtil.getUserId();
        final UserDTO userDTO = userService.updateUserInfo(userId, request.convertUserDTO());
        return new DataApiResponse<>(userDTO);
    }

    @DeleteMapping(name = "회원 탈퇴")
    public DefaultApiResponse withdrawUser() {
        final long userId = SecurityUtil.getUserId();
        userService.withdrawUser(userId);
        return new DefaultApiResponse();
    }

}
