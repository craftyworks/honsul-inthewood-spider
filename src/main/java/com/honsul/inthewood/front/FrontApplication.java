package com.honsul.inthewood.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FrontApplication {

	public static void main2(String[] args) {
		SpringApplication.run(FrontApplication.class, args);
	}
	
  
}
