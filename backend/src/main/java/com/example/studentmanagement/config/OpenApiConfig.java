package com.example.studentmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ═══════════════════════════════════════════════════════════════
 *  OpenAPI / Swagger Configuration
 * ═══════════════════════════════════════════════════════════════
 *
 *  This configures the interactive API documentation.
 *  Once the app is running, open:
 *  http://localhost:8080/swagger-ui.html
 *
 *  You'll see every endpoint, be able to send test requests,
 *  and see example requests/responses — all without Postman.
 * ═══════════════════════════════════════════════════════════════
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Student Management System API")
                .description(
                    "REST API for managing students, subjects, and attendance. " +
                    "Built with Spring Boot + MySQL."
                )
                .version("1.0.0")
                .contact(new Contact()
                    .name("Student Management Team")
                    .email("support@studentmgmt.com")
                )
            );
    }
}
