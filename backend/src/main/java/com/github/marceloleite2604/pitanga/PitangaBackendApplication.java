package com.github.marceloleite2604.pitanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class PitangaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PitangaBackendApplication.class, args);
	}

}