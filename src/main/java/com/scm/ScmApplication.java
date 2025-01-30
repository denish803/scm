package com.scm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class ScmApplication {
// add favorite api on click.
	public static void main(String[] args) {
		SpringApplication.run(ScmApplication.class, args);
	}

}
