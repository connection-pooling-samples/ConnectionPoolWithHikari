package com.rudra.aks.hikari.pool.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rudra.aks.hikari.pool.model.UserDTO;
import com.zaxxer.hikari.HikariDataSource;

@Repository
public class UserDaoImpl implements IUserDAO {

	@Autowired
	HikariDataSource	dataSource;
	
	@Autowired
	JdbcTemplate	jdbcTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	private static final  String SELECT_QUERY = "select userid, username, emailid from USER_BATCH where userid=";
	private static final  String COUNT_QUERY  = "select count(userid) from USER_BATCH";
	
	
	public UserDTO getUser(int userid) {
		
		//For simple JDBC API
		//return getUserByDS(userid);
		
		//For Spring-JDBC using JdbcTemplate
		return getUserByTemplate(userid);
	}
		
	private UserDTO getUserByTemplate(int userid) {
		
		HikariDataSource dataSource = (HikariDataSource) jdbcTemplate.getDataSource();
		logger.info("------ Basic Data Source Properties Before Processing : ----------");
		connectionPoolProperties(dataSource);
		
		final UserDTO	user = new UserDTO();
		jdbcTemplate.query(SELECT_QUERY + userid, new RowCallbackHandler() {
			
			public void processRow(ResultSet rs) throws SQLException {
				if (rs.next()) {
					
					user.setUserid(rs.getInt(1));
					user.setUsername(rs.getString(2));
					user.setEmailid(rs.getString(3));
					logger.info(user.toString());
				}
			}
		});
		
		logger.info("------ Basic Data Source Properties After Processing : ----------");
		connectionPoolProperties(dataSource);
		return user;
	}

	public	int getNoOfUsers() {
		
		HikariDataSource dataSource = (HikariDataSource) jdbcTemplate.getDataSource();
		logger.info("------ Basic Data Source Properties Before Processing : ----------");
		connectionPoolProperties(dataSource);
		int usersCount = jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class);
				
		logger.info("------ Basic Data Source Properties After Processing : ----------");
		connectionPoolProperties(dataSource);
		return usersCount;
	
	}
	
	private	UserDTO	getUserByDS(int userid) {
		UserDTO user = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs =  null;
		
		logger.info("------ Basic Data Source Properties Before Opening Connection: ----------");
		connectionPoolProperties(dataSource);
		
		try {
			connection = dataSource.getConnection();
			logger.info("------ Basic Data Source Properties while processing : ----------");
			connectionPoolProperties(dataSource);
			/*try {
				//Holding the connection to check connection wait time
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error(" " + e);
			}*/
			statement = connection.createStatement();
			rs = statement.executeQuery(SELECT_QUERY + userid);
			if (rs.next()) {
				user = new UserDTO();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setEmailid(rs.getString(3));
				logger.info(user.toString());
			}
			
		} catch (SQLException e) {
			logger.error("Exception while query : " + e);
		} finally {
			
				try {
					rs.close();
					statement.close();
					connection.close();
					
					//Holding the connection to check expiry
					Thread.sleep(10000);
				} catch (SQLException e) {
					logger.error("Exception while sql query: " + e);
				} catch (InterruptedException e) {
					logger.error(" " + e);
				}
				
		}
		logger.info("------ Basic Data Source Properties After closing Connection : ----------");
		connectionPoolProperties(dataSource);
		return user;
	}

	public void getPoolProperties() {
		connectionPoolProperties(dataSource);
	}

	private void connectionPoolProperties(HikariDataSource dataSource) {
				
		/*logger.info("Max Pool Size : " + dataSource.getMaximumPoolSize());
		logger.info("Max time for Connections in Pool to be Idle : " + dataSource.getIdleTimeout());
		logger.info("Max life-tiem of Connection in Pool : " + dataSource.getMaxLifetime());
		logger.info("Max Pool size(idle & in-use both) : " + dataSource.getMinimumIdle());
		logger.info("DataSourceProperties : " + dataSource.getDataSourceProperties());*/
	}

}
