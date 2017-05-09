package com.zuipin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @Title: SwaggerConfig
 * @Package: com.zuipin.conf
 * @author: zengxinchao  
 * @date: 2017年5月4日 下午3:50:55
 * @Description: SwaggerConfig,apiInfo方法可以配置ui页面显示的基本信息
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("API", "测试4", "API TOS", "Terms of service", new Contact("zxc", "", "canon37@sina.com"), "License of API", "API license URL");
		return apiInfo;
	}
}
