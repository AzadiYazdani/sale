
package com.haraji.app.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class OpenApiConfig {

    @Value("${swagger.api.title}")
    private String apiTitle;

    @Value("${swagger.api.description}")
    private String apiDescription;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .version(apiVersion)
                        .description(apiDescription))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer pageableOpenApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getParameters() == null) {
                        return;
                    }

                    operation.getParameters().forEach(parameter -> {
                        if (!"query".equals(parameter.getIn())) {
                            return;
                        }

                        switch (parameter.getName()) {
                            case "page" -> parameter.setSchema(
                                    new IntegerSchema()._default(0).minimum(BigDecimal.valueOf(0))
                            );
                            case "size" -> parameter.setSchema(
                                    new IntegerSchema()._default(20).minimum(BigDecimal.valueOf(1))
                            );
                            case "sort" -> parameter.setSchema(
                                    new StringSchema()._default("id,desc")
                            );
                        }
                    });
                })
        );
    }
}
