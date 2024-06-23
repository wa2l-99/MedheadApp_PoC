package com.example.medhead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class userAuthentificaton {

	public static void main(String[] args) {
		SpringApplication.run(userAuthentificaton.class, args);
	}

}



