package com.zhujiu.waka.socket.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

@Component
public class SocketMessageHandler implements ConnectListener,DisconnectListener,DataListener<String>{
	private static final Logger log = Logger.getLogger(SocketMessageHandler.class);

	private SocketIOServer server;

	@Autowired
	public SocketMessageHandler(SocketIOServer server) {
		this.server = server;
	}

	public void onConnect(SocketIOClient client) {
		String uid = client.getHandshakeData().getSingleUrlParam("token");
		System.out.println("===token:"+uid);
		if (!StringUtils.isEmpty(uid)) {
			if (!client.getAllRooms().contains(uid)) {
				client.joinRoom(uid);
				client.leaveRoom("");
			}
		}
		// listClient.add(client.getSessionId());
		log.info("客户端:" + client.getSessionId() + "已连接");
	}

	public void onDisconnect(SocketIOClient client) {
		log.info("客户端:" + client.getSessionId() + "断开连接");
	}


	@Override
	public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
		log.info("发来消息：" + data.toString());
		server.getClient(client.getSessionId()).sendEvent("login", data);
		
	}
	@PostConstruct
	public void init() {
		server.addConnectListener(this);
		server.addDisconnectListener(this);
		server.addEventListener("login", String.class, this);
	}

}
