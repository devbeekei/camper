package com.ss.camper;

import com.ss.camper.oauth2.config.OAuth2Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OAuth2Properties.class)
public class CamperApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamperApplication.class, args);
	}

}
