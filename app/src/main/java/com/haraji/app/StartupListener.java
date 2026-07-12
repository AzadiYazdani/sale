package com.haraji.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupListener {

    @EventListener(ApplicationReadyEvent.class)
    public void onReady(ApplicationReadyEvent event) {

        Environment env = event.getApplicationContext().getEnvironment();

        String port = env.getProperty("local.server.port");
        if (port == null) {
            port = env.getProperty("server.port", "8081");
        }

        log.info("Application Started on http://localhost:{}/swagger-ui/index.html", port);
    }
}
