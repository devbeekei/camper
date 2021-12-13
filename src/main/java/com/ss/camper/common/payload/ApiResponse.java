package com.ss.camper.common.payload;

import com.ss.camper.common.util.ParsingUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private int code = ApiResponseType.SUCCESS.getCode();
    private String message = ApiResponseType.SUCCESS.getMessage();

    public static ApiResponse error(ApiResponseType apiResponseType) {
        return new ApiResponse(apiResponseType.getCode(), apiResponseType.getMessage());
    }

    public static void response(ApiResponseType apiResponseType) {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(apiResponseType.getStatus().value());
            response.getWriter().write(Objects.requireNonNull(ParsingUtil.writeValueAsString(ApiResponse.error(apiResponseType))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}