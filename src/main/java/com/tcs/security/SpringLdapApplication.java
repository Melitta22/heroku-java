package com.tcs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLdapApplication {

	
	
	public static void main(String[] args) {
		System.out.println("Starting App.....");
		SpringApplication.run(SpringLdapApplication.class, args);
	}

}
