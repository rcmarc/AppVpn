package com.github.rcmarc.appvpn.config;

import java.util.Scanner;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;

import com.github.rcmarc.appvpn.services.PropertiesService;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
@AllArgsConstructor
public class DbConfig extends HikariConfig {

	private final PropertiesService propertiesService;

	@Bean
	public DataSource dataSource() {
		String url = "jdbc:postgresql://localhost:5432/vpn_events";
		String driverClassName = "org.postgresql.Driver";
		String userKey = "spring.datasource.hikari.username", passwordKey = "spring.datasource.hikari.password";

		if (propertiesService.getProperty(userKey) == null) {
			try (final Scanner in = new Scanner(System.in)) {
				String username = username(in);
				String password = password(in);
				propertiesService.setValue(userKey, username);
				propertiesService.setValue(passwordKey, password);
				return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username)
						.password(password).build();
			}
		}

		return DataSourceBuilder.create().driverClassName(driverClassName).url(url)
				.username(propertiesService.getProperty(userKey)).password(propertiesService.getProperty(passwordKey))
				.build();
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