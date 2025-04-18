package com.ainapapy.aigle.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info();
        info.setDescription("Documentation interactive de l'API Aigle");
        info.setTitle("Aigle API Documentation");
        info.setVersion("1.0.0");
        
        return new OpenAPI().info(info);
    }
}
