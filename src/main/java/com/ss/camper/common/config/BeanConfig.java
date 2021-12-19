package com.ss.camper.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.camper.store.application.StoreService;
import com.ss.camper.store.application.StoreServiceV1;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

}
