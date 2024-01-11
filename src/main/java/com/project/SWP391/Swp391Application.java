package com.project.SWP391;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Swp391Application {

	public static void main(String[] args) {
		SpringApplication.run(Swp391Application.class, args);
	}

}
