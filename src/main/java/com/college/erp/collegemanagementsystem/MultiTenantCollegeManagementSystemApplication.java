package com.college.erp.collegemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @author grish
 *
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MultiTenantCollegeManagementSystemApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MultiTenantCollegeManagementSystemApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MultiTenantCollegeManagementSystemApplication.class, args);
    }

}
