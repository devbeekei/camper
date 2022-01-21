package com.ss.camper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    public static final String[] STATIC_PATH = new String[]{
            "/docs/**", "/favicon.ico"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        long MAX_AGE_SECS = 3600;
        registry
                .addMapping("/**")
                .allowedOrigins("*") // 외부에서 들어오는 모든 url을 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용되는 Method
                .allowedHeaders("*") // 허용되는 헤더
                .allowCredentials(true) // 자격증명 허용
                .maxAge(MAX_AGE_SECS); // 허용 시간
    }

}
