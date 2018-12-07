package com.zhujiu.waka.socket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "socketio")
public class SocketIoProperties {
	
	private String hostname;
	
	private int port;
	
	private int upgradeTimeout;
	
	private int pingInterval;
	
	private int pingTimeout;
	
	private String eventname;
	
	private String redisHost;
	private int redisPort;
	private int redisDatabase;
	private String redisPassword;
	private int redisMinIdleSize;
	private int redisPoolSize;
	private int redisSubscriptionConnectionPoolSize;
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getUpgradeTimeout() {
		return upgradeTimeout;
	}

	public void setUpgradeTimeout(int upgradeTimeout) {
		this.upgradeTimeout = upgradeTimeout;
	}

	public int getPingInterval() {
		return pingInterval;
	}

	public void setPingInterval(int pingInterval) {
		this.pingInterval = pingInterval;
	}

	public int getPingTimeout() {
		return pingTimeout;
	}

	public void setPingTimeout(int pingTimeout) {
		this.pingTimeout = pingTimeout;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getRedisDatabase() {
		return redisDatabase;
	}

	public void setRedisDatabase(int redisDatabase) {
		this.redisDatabase = redisDatabase;
	}

	public String getRedisPassword() {
		return redisPassword;
	}

	public void setRedisPassword(String redisPassword) {
		this.redisPassword = redisPassword;
	}


	public int getRedisPoolSize() {
		return redisPoolSize;
	}

	public void setRedisPoolSize(int redisPoolSize) {
		this.redisPoolSize = redisPoolSize;
	}

	public int getRedisMinIdleSize() {
		return redisMinIdleSize;
	}

	public void setRedisMinIdleSize(int redisMinIdleSize) {
		this.redisMinIdleSize = redisMinIdleSize;
	}

	public int getRedisSubscriptionConnectionPoolSize() {
		return redisSubscriptionConnectionPoolSize;
	}

	public void setRedisSubscriptionConnectionPoolSize(int redisSubscriptionConnectionPoolSize) {
		this.redisSubscriptionConnectionPoolSize = redisSubscriptionConnectionPoolSize;
	}


	
	
}
