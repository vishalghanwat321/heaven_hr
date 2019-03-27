package com.web.app.heaven_hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This application is to manage recruitment process, base framework spring boot is used to build this application.
 * @since 2019
 */

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class HeavenHrApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeavenHrApplication.class, args);
    }

}