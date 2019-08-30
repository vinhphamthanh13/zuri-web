package com.ocha.boc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build()
                .apiInfo(metaData())
                .securitySchemes(Arrays.asList(apiKey()));
    }


    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header"); //`apiKey` is the name of the APIKey, `Authorization` is the key in the request header
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "BOC Rest APIs",
                "Rest APIs Developed By BOC",
                "1.0",
                "Terms of service",
                 "Vinh Pham",
                "Copyright 2018 - 2019 BOC, All rights reserved.",
                "");
        return apiInfo;
    }
    
}