package com.zhujiu.waka.socket.api;


import com.zhujiu.waka.socket.obj.UserInfo;

@FunctionalInterface
public interface SocketService {
	public boolean send(String token,String event,UserInfo user);
}
