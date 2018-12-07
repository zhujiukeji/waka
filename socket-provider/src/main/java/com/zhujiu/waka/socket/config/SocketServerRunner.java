package com.zhujiu.waka.socket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
@Order(value=1)
public class SocketServerRunner implements CommandLineRunner {
	private static final Logger log=LoggerFactory.getLogger(SocketServerRunner.class);
    private final SocketIOServer server;
 
 
    @Autowired
    public SocketServerRunner(SocketIOServer server) {
        this.server = server;
    }


	public void run(String... args) throws Exception {
		server.start();
        log.info("*****************WebSocketServer启动成功！************************");
	}
 
}
