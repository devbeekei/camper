package com.ss.camper.common.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.camper.common.util.ParsingUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DefaultApiResponse {

    private int code = ApiResponseType.SUCCESS.getCode();
    private String message = ApiResponseType.SUCCESS.getMessage();

    public static DefaultApiResponse error(ApiResponseType apiResponseType) {
        return new DefaultApiResponse(apiResponseType.getCode(), apiResponseType.getMessage());
    }

    public static void response(ApiResponseType apiResponseType) {
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.setStatus(apiResponseType.getStatus().value());
            response.getWriter().write("{\n" +
                "    \"code\": " + apiResponseType.getCode() + ",\n" +
                "    \"message\": \"" + apiResponseType.getMessage() + "\"\n" +
                "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}