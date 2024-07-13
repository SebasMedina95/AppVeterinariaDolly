package com.sebastian.veterinaria_dolly.veterinaria_dolly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:swagger.properties", encoding = "UTF-8")
public class VeterinariaDollyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaDollyApplication.class, args);
	}

}
