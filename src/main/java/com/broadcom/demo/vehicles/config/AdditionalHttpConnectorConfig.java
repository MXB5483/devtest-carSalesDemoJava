package com.broadcom.demo.vehicles.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdditionalHttpConnectorConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> customizer() {
        return factory -> {
            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            connector.setScheme("http");
            connector.setPort(8081);
            connector.setSecure(false);
            connector.setProperty("bindOnInit", "false");
            try {
                factory.addAdditionalTomcatConnectors(connector);
            } catch (Exception e) {
                // Log the exception but don't fail startup if additional connector fails
                System.err.println("Warning: Failed to add additional HTTP connector: " + e.getMessage());
            }
        };
    }
}
