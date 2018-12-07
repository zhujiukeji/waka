package com.zhujiu.waka.socket.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.pubsub.DispatchMessage;
import com.corundumstudio.socketio.store.pubsub.PubSubType;
import com.zhujiu.waka.socket.api.SocketService;
import com.zhujiu.waka.socket.obj.UserInfo;


@Component
@Service(interfaceClass=SocketService.class,version="1.0.0")
public class SocketSerivceImpl implements SocketService {
	private static final Logger log = Logger.getLogger(SocketSerivceImpl.class);
	@Autowired
	private SocketIOServer server;
	
	@Autowired
	private RedissonStoreFactory storeFactory;
	@Override
	public boolean send(String token,String event, UserInfo content) {
		log.info("websocket dubbo service start succeed");
		log.debug("===============Token:"+token);
		Collection<SocketIOClient> cs = server.getRoomOperations(token).getClients();
		System.out.println("====连接数量："+cs.size());
		if(!cs.isEmpty()) {
			server.getRoomOperations(token).sendEvent(event, content);
		}/*else {
			 List<Object> dataList=new ArrayList<Object>();
			 dataList.add(content);
			 Packet packet = new Packet(PacketType.MESSAGE);
			 packet.setSubType(PacketType.EVENT);
			 packet.setName(event);
			 packet.setData(dataList);
			 packet.setNsp("");
			 storeFactory.pubSubStore().publish(PubSubType.DISPATCH, new DispatchMessage(token, packet, ""));
		}*/
		log.info("websocket dubbo service end succeed");
		return false;
	}

}
