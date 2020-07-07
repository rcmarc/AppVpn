package com.github.rcmarc.appvpn;

import java.util.Scanner;

import com.github.rcmarc.appvpn.services.PropertiesService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {
	public static void main(String[] args) {
		initDatabaseUser();
		SpringApplication.run(App.class, args);
	}

	private static void initDatabaseUser() {
		PropertiesService propertiesService = new PropertiesService();
		propertiesService.setPath("application.properties");
		if (propertiesService.getProperty("spring.datasource.username") == null) {
			try (final Scanner in = new Scanner(System.in)) {
				String username = username(in);
				String password = password(in);
				propertiesService.setValue("spring.datasource.username", username);
				propertiesService.setValue("spring.datasource.password", password);
			}
		}

	}

	private static String password(Scanner in) {
		System.out.print("Enter user password: ");
		return in.next();
	}

	private static String username(Scanner in) {
		System.out.print("Enter database user: ");
		return in.next();
	}
}
