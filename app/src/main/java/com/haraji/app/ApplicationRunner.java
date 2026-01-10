package com.haraji.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.haraji.common","com.haraji.app", "com.haraji.security","com.haraji.baseinfo","com.haraji.baseinfo.mapper","com.haraji.baseinfo.service", "com.haraji.business"})
@EnableJpaRepositories(basePackages= {"com.haraji.baseinfo.database.repository","com.haraji.business.database.repository","com.haraji.security.database.repository"})
@EntityScan(basePackages= {"com.haraji.baseinfo.database.entity","com.haraji.business.database.entity", "com.haraji.security.database.entity"})
@ConfigurationPropertiesScan(basePackages = {"com.haraji.app", "com.haraji.baseinfo", "com.haraji.business", "com.haraji.security"})
@Slf4j
public class ApplicationRunner {

    public static void main(String[] args) {

        log.info("Application Starting...");

        SpringApplication application = new SpringApplication(ApplicationRunner.class);
        ConfigurableApplicationContext ctx = application.run(args);
        ctx.registerShutdownHook();

        log.info("Application Started on http://localhost:8081/swagger-ui/#/");
    }

}
