package net.atpco.rnd.customerprofile.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)//
				.select().apis(RequestHandlerSelectors.basePackage("net.atpco.rnd.customerprofile.rest"))
				.paths(PathSelectors.any())//
				.build()//
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Customer Service", "www.atpco.net", "ndc@atpco.net");
		Collection<VendorExtension> vendorExtensions =  Collections.emptyList();
		return new ApiInfo("NDC BlockChain REST API",
				"Traveler hosted profile information, to enable tailored responses from Airlines.", "0.0.1",
				"Terms of service URL", contact, "License of API", "API license URL", vendorExtensions);
	}

}