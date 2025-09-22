package com.workflow.system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI workflowSystemOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Workflow System API")
                        .description("Configurable 5-stage workflow system with Camunda BPM integration")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Workflow System")
                                .email("admin@workflow-system.com")));
    }
}
