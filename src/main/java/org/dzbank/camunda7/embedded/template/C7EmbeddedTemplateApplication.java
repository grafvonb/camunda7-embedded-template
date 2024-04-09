package org.dzbank.camunda7.embedded.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.dzbank.camunda7.embedded"})
public class C7EmbeddedTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(C7EmbeddedTemplateApplication.class, args);
    }

}
