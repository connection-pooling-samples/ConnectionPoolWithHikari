package com.rudra.aks.hikari.pool;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HikariPoolApp {

	
	public static void main(String[] args) throws SQLException {
		SpringApplication.run(HikariPoolApp.class, args);
		
	}
}
