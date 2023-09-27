package com.example.configvaultserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.configvaultserver.config.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class ConfigVaultServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigVaultServerApplication.class, args);
	}

}
