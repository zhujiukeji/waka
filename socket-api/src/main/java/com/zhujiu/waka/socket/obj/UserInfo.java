package com.zhujiu.waka.socket.obj;

import java.io.Serializable;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6280452979307437823L;

	public final static int STATUS_SCAN=403;
	public final static int STATUS_SUCCESS=405;
	
	private String username;
	private String token;
	private int status;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
