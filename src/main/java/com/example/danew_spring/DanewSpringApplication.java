package com.example.danew_spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.danew_spring")
public class DanewSpringApplication {

	public static void main(String[] args) {

		SpringApplication.run(DanewSpringApplication.class, args);
	}

}
