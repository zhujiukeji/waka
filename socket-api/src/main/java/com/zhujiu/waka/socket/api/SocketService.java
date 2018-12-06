package com.zhujiu.waka.socket.api;

@FunctionalInterface
public interface SocketService {
	public boolean send(String token,String content);
}
