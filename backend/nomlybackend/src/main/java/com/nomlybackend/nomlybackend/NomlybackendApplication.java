package com.nomlybackend.nomlybackend;

import com.nomlybackend.nomlybackend.service.GoogleApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class NomlybackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NomlybackendApplication.class, args);
	}

}
