package com.haraji.app.config.swagger;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ClientCredentialsGrant;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Configuration("Haraji-SwaggerConfig")
@EnableSwagger2
@PropertySource("classpath:application.yml")
@Conditional(SwaggerEnabledCondition.class)
@Slf4j
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String apiTitle;

    @Value("${swagger.api.description}")
    private String apiDescription;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Value("${security.oauth2.token-uri}")
    private String TOKEN_URL;

    @PostConstruct
    private void InitLog() {
        log.info("Swagger enabled");
    }

    @Bean
    public Docket customImplementation() {
        List<Parameter> parameterBuildersList = new ArrayList<>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder
                .name("trackerId")
                .parameterType("query")
                .description("Used for tracking requests by UUID String that provided by clients")
                .modelRef(new ModelRef("uuid"))
                .required(false);
        parameterBuildersList.add(parameterBuilder.build());

        ClientCredentialsGrant clientCredentialsGrant = new ClientCredentialsGrant(TOKEN_URL);

        AuthorizationScope[] scopes = {
                new AuthorizationScope("openid", "Getting access token")
        };

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .globalOperationParameters(parameterBuildersList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.haraji"))
                .paths(PathSelectors.any())
                .build()
//                .directModelSubstitute(LocalDate.class, String.class)
//                .directModelSubstitute(LocalDateTime.class, String.class)
////                .securitySchemes(Arrays.asList(oAuth))
////                .securityContexts(Arrays.asList(keycloak))
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDescription)
//                .version(buildProperties != null ? buildProperties.getVersion() : null)
                .build();
    }


    @Bean
    @Profile("dev")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}

