package com.gohenry.coding.challenge.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "GoHenry Coding Challenge",
                "A simple service for GoHenry coding challenge",
                "1.0",
                "http://gohenry.com",
                new Contact("Rui Wang", "https://github.com/wangrui-uk/coding-challenge", "urumqi.wang@gmail.com"),
                "private license",
                "http://gohenry.com/license",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gohenry.coding.challenge"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.apiInfo());
    }

}
