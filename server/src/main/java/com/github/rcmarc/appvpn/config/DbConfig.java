package com.github.rcmarc.appvpn.config;

import java.util.Scanner;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.rcmarc.appvpn.services.PropertiesService;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DbConfig extends HikariConfig {
	@Bean
	public DataSource dataSource() {
		String url = "jdbc:postgresql://localhost:5432/vpn_events";
		PropertiesService propertiesService = new PropertiesService();
		propertiesService.setPath("application.properties");
		if (propertiesService.getProperty("spring.datasource.hikari.username") == null) {
			try (final Scanner in = new Scanner(System.in)) {
				String username = username(in);
				String password = password(in);
				propertiesService.setValue("spring.datasource.hikari.username", username);
				propertiesService.setValue("spring.datasource.hikari.password", password);
				return DataSourceBuilder.create().driverClassName("org.postgresql.Driver").url(url).username(username)
						.password(password).build();
			}
		}

		return new HikariDataSource(this);
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