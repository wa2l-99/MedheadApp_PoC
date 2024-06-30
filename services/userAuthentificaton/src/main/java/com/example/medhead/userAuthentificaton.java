package com.example.medhead;

import com.example.medhead.dao.RoleRepository;
import com.example.medhead.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class userAuthentificaton {

	public static void main(String[] args) {
		SpringApplication.run(userAuthentificaton.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByNom("Patient").isEmpty()) {
				roleRepository.save(Role.builder().nom("Patient").build());
			}
		};
	}
}



