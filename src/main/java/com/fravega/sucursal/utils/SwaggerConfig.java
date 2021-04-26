package com.fravega.sucursal.utils;


import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SwaggerConfig {
    @Value("${swagger.doc.title}")
    private String title;
    @Value("${swagger.doc.description}")
    private String description;
    @Value("${swagger.doc.version}")
    private String version;
    @Value("${swagger.doc.url}")
    private String url;
    @Value("${swagger.doc.contact.name}")
    private String name;
    @Value("${swagger.doc.contact.email}")
    private String email;
    @Value("${swagger.doc.license}")
    private String license;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fravega.sucursal.services"))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                title,
                description,
                version,
                url,
                new Contact(name, url, email),
                license,
                license,
                Collections.emptyList()
        );
    }
}
