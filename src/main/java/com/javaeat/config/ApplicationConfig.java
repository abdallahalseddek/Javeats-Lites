package com.javaeat.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("JavaEat")
                        .description("REST API for JavaEat Project.")
                        .version("v1.0"));
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
