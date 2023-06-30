package com.springboot.softwaremanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SoftwaremanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwaremanagementApplication.class, args);
	}

}
