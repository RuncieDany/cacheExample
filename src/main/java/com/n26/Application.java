package com.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication(scanBasePackages = "com.n26")
public class Application {

	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.build();
		JavaTimeModule module = new JavaTimeModule();
		objectMapper.registerModule(module);
		return objectMapper;
	}
}
