package com.rest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
            .info(new Info().title("Springboot Study")
            .description("jpa, rest api study in springboot")
            .version("v1")
            .contact(new Contact().name("jbyun").url("https://velog.io/@orol116").email("orol116@naver.com"))
            .license(new License().name("").url("https://velog.io/@orol116")));
    }
	
}
