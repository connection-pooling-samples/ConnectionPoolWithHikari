package com.rudra.aks.hikari.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class HikariPoolDBConfig {

	@Value("${jdbc.driverClassName}")
	private	String 	driverClassName;
	
	@Value("${jdbc.connectionUrl}")
	private String url;
	
	@Value("${jdbc.username}")
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	
	@Bean
	public	HikariDataSource	pooledDataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		
				
		//Max number of active connections
		dataSource.setMaximumPoolSize(10);
		
		//Min number of connections which may remain idle in pool [ default - 8]
		dataSource.setMinimumIdle(2);
		
		//Max lifetime of a connection
		dataSource.setMaxLifetime(30000);
		
		//Max time a connection will be idle in pool
		dataSource.setIdleTimeout(10000);
		return dataSource;
	}
	
	@Bean
	@Scope("prototype")
	public	JdbcTemplate	jdbcTemplate() {
		return new JdbcTemplate(pooledDataSource(), true);
	
	}
	
	@Bean
	@Scope("prototype")
	public	PlatformTransactionManager	txManager() {
		return new DataSourceTransactionManager(pooledDataSource());
	}
}
