package com.weblabs.lab3server.module.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI().info(
            Info().title("Мини-почта API").version("v1.0")
        )
    }
}