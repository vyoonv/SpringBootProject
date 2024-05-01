package com.bebegiboo.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class BeBeGiBooApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeBeGiBooApplication.class, args);
	}

}
