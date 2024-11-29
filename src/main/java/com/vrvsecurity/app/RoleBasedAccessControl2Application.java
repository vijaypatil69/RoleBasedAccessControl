package com.vrvsecurity.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.vrvsecurity.*")
//@ComponentScan("com.vrvsecurity.utility")
@EnableJpaRepositories(basePackages = "com.vrvsecurity.repo")
@EntityScan("com.vrvsecurity.model")
public class RoleBasedAccessControl2Application {

	public static void main(String[] args) {
		SpringApplication.run(RoleBasedAccessControl2Application.class, args);
	}

}
