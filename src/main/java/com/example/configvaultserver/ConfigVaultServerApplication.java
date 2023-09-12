package com.example.configvaultserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.configvaultserver.security.RecaptchaProperties;

@SpringBootApplication
@EnableConfigurationProperties(RecaptchaProperties.class)
public class ConfigVaultServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigVaultServerApplication.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}

}
