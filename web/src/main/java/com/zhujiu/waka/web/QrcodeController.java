package com.zhujiu.waka.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhujiu.waka.socket.api.SocketService;
import com.zhujiu.waka.socket.obj.UserInfo;
import com.zhujiu.waka.user.api.UserService;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/qrcode")
public class QrcodeController {
	@Reference(interfaceClass=UserService.class,version="1.0.0",check=false)
	private UserService userService;
	@Reference(interfaceClass=SocketService.class,version="1.0.0",check=false)
	private SocketService socketService;
	@RequestMapping("index")
	public Mono<Map<String,String>> index(){
		Map<String,String> rs=new HashMap<String,String>();
		rs.put("uid", UUID.randomUUID().toString());
		return Mono.just(rs);
	}
	
	@RequestMapping("scan")
	public Mono<Map<String,String>> scan(String token,String uid){
		UserInfo user=new UserInfo();
		user.setStatus(UserInfo.STATUS_SCAN);
		socketService.send(uid, "login", user);
		Map<String,String> rs=new HashMap<String,String>();
		rs.put("token", token);
		rs.put("uid", uid);
		return Mono.just(rs);
	}
}

