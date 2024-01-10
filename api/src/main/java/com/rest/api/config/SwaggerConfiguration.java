package com.rest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.rest.api.controller")) // 해당 경로 하단의 Controller 내용을 읽어 mapping된 resource들을 문서화 시킴
																					  // 다음과 같이 작성해서 v1으로 시작하는 resource들만 문서화 시킬 수 있다.
																					  // => PathSelectors.ant("/v1/**")
																					  // swaggerInfo를 세팅하면 문서에 대한 설명과 작성자 정보를 노출시킬 수 있음
				.paths(PathSelectors.any())	
				.build()
				.useDefaultResponseMessages(false); // 기본으로 세팅되는 200, 404, 403, 404 메시지를 표시하지 않음
	}
	
	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder().title("Spring API Documentation")
				.description("앱 개발 시 사용되는 서버 API에 대한 연동 문서")
				.license("").licenseUrl("").version("1")
				.build();
	}

}
