package com.example.medhead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class userAuthentificatonApplication {

	public static void main(String[] args) {
		SpringApplication.run(userAuthentificatonApplication.class, args);
	}

}



