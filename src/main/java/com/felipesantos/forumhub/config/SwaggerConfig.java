package com.felipesantos.forumhub.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new io.swagger.v3.oas.models.info.Info().title("ForumHub API").version("v1.0")
                .license(new io.swagger.v3.oas.models.info.License().name("MIT")));
    }
}
