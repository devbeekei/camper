package com.ss.camper;

import com.ss.camper.oauth2.config.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties.class)
public class CamperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamperApplication.class, args);
	}

}
