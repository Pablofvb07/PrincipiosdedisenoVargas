package com.example.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RestserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestserviceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductoRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.save(new Producto("Manzana", 0.5));
				repo.save(new Producto("Pan", 0.9));
				repo.save(new Producto("Leche", 1.2));
			}
		};
	}
}
