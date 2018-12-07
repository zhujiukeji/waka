package com.zhujiu.waka.socket.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;

import io.netty.channel.ChannelHandlerContext;

@Component
public class SocketExceptionListener implements ExceptionListener {
	private static Logger log=LoggerFactory.getLogger(SocketExceptionListener.class);
	public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
		log.error(e.getMessage(),e.getCause());
		e.printStackTrace();
	}

	@Override
	public void onDisconnectException(Exception e, SocketIOClient client) {
		log.error(e.getMessage(),e.getCause());
		e.printStackTrace();
	}

	@Override
	public void onConnectException(Exception e, SocketIOClient client) {
		log.error(e.getMessage(),e.getCause());
		e.printStackTrace();
	}

	@Override
	public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
		log.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		return false;
	}

	@Override
	public void onPingException(Exception e, SocketIOClient client) {
		log.error(e.getMessage(),e.getCause());
		e.printStackTrace();
		
	}

}
