package com.rudra.aks.hikari.pool.model;

public class UserDTO {

	private int userid;
	private String username;
	private String emailid;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDTO(int userid, String username, String emailid) {
		super();
		this.userid = userid;
		this.username = username;
		this.emailid = emailid;
	}
	@Override
	public String toString() {
		return "UserDTO [userid=" + userid + ", username=" + username + ", emailid=" + emailid + "]";
	}
	
	
}
