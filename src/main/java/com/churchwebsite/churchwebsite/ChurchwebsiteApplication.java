package com.churchwebsite.churchwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChurchwebsiteApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.load();
//		 Dotenv dotenv = Dotenv.configure().directory("/home/ec2-user/").load();
//		String dbHost = dotenv.get("DB_HOST");
//		String dbName = dotenv.get("DB_NAME");
//		String dbUser = dotenv.get("DB_USERNAME");
//		String dbPassword = dotenv.get("DB_PASSWORD");
//		System.out.println("Database host: " + dbHost);
//		System.out.println("Database dbName: " + dbName);
//		System.out.println("Database dbUser: " + dbUser);
//		System.out.println("Database dbPassword: " + dbPassword);
		SpringApplication.run(ChurchwebsiteApplication.class, args);
	}

}
