package com.zhujiu.waka.socket.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.store.RedissonStoreFactory;

@Configuration
@EnableConfigurationProperties(SocketIoProperties.class)
public class SocketIoConfig {
	
	@Autowired
	private SocketIoProperties config;
	
	@Autowired
	private SocketExceptionListener exceptionListener;
	
	
	@Bean
	public RedissonClient crateRedisson() {
		Config redissonConfig = new Config();
		redissonConfig.useSingleServer().setAddress(config.getRedisHost())/*.setPassword(config.getRedisPassword())*/
		.setDatabase(config.getRedisDatabase()).setConnectionMinimumIdleSize(config.getRedisMinIdleSize()).setConnectionPoolSize(config.getRedisPoolSize()).setSubscriptionConnectionPoolSize(config.getRedisSubscriptionConnectionPoolSize());
		return Redisson.create(redissonConfig);
	}

	@Bean
	public RedissonStoreFactory createStoreFactory(RedissonClient redisson) {
		return new RedissonStoreFactory(redisson);
	}
	
	@Bean
	public SocketIOServer socketIOServer(RedissonStoreFactory sf) {
		com.corundumstudio.socketio.Configuration wsconfig = new com.corundumstudio.socketio.Configuration();
		// 设置主机名，默认是0.0.0.0
		wsconfig.setHostname(config.getHostname());
		// 设置监听端口
		wsconfig.setPort(config.getPort());
		// 协议升级超时时间（毫秒），默认10000。HTTP握手升级为ws协议超时时间
		wsconfig.setUpgradeTimeout(config.getUpgradeTimeout());
		// Ping消息间隔（毫秒），默认25000。客户端向服务器发送一条心跳消息间隔
		wsconfig.setPingInterval(config.getPingInterval());
		// Ping消息超时时间（毫秒），默认60000，这个时间间隔内没有接收到心跳消息就会发送超时事件
		wsconfig.setPingTimeout(config.getPingTimeout());
		wsconfig.setExceptionListener(exceptionListener);
		// 这个版本0.9.0不能处理好namespace和query参数的问题。所以为了做认证必须使用全局默认命名空间
		wsconfig.setAuthorizationListener(new AuthorizationListener() {
			public boolean isAuthorized(HandshakeData data) {
				/*
				 * logger.info("websocket auth start!!!"); String jwt =
				 * data.getSingleUrlParam("Authorization"); String token =
				 * StringUtils.substringAfter(jwt, bearer); if (StringUtils.isBlank(token) ||
				 * !jwtService.verifyAndDelay(token)) {
				 * logger.info("websocket token auth fail!!!"); return false; }
				 * logger.info("websocket token auth succeed!!!");
				 */
				return true;
			}
		});
		wsconfig.setTransports(Transport.WEBSOCKET);
		// redis start
		//wsconfig.setStoreFactory(sf);
		// redis end
		final SocketIOServer server = new SocketIOServer(wsconfig);
		return server;
	}
}
