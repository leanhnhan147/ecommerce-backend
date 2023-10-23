package com.ecommerce.site.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SiteAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiteAdminApplication.class, args);
    }

}
