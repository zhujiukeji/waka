package com.zhujiu.waka.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.zhujiu.waka.socket.api.SocketService;
import com.zhujiu.waka.socket.obj.UserInfo;
import com.zhujiu.waka.user.api.UserService;
import com.zhujiu.waka.user.obj.User;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/user")
public class UserController {
	private final org.slf4j.Logger log=LoggerFactory.getLogger(UserController.class);
	@Reference(interfaceClass = UserService.class, version = "1.0.0", check = false)
	private UserService service;
	@Reference(interfaceClass = SocketService.class, version = "1.0.0", check = false)
	private SocketService socket;
	@GetMapping
	@RequestMapping("index")
	public Mono<Void> index(){
		return Mono.empty();
	}
	
	//@PostMapping
	@RequestMapping("loginApp")
	@ResponseBody
	public Mono<Object> loginApp(ServerHttpRequest request,ServerHttpResponse response,String username,String passwd){
		Map<String,Object> rs=new HashMap<String,Object>();
		User u=service.findByUserName(username, passwd);
		if(u==null) {
			rs.put("errorCode", 101);
			return Mono.just(rs);
		}else {
			rs.put("errorCode", 0);
			rs.put("token", u.getToken());
			rs.put("username", u.getUsername());
			
			response.addCookie(ResponseCookie.from("token", u.getToken()).build());
			return Mono.just(rs);
		}
	}
	@RequestMapping("confirm")
	public Mono<Map<String,Object>> loginPcConfirm(ServerHttpRequest request,String tempToken){
		Map<String,Object> rs=new HashMap<String,Object>();
		
		String token=request.getCookies().getFirst("token").getValue();
		if(token==null||token.length()<36||tempToken==null||tempToken.length()<36) {
			//非法token
			rs.put("errorCode", 102);
		}else {
			User u=service.findByToken(token);
			//token失效
			if(u==null) {
				rs.put("errorCode", 102);
			}else {
				rs.put("errorCode", 0);
				rs.put("token", token);
				rs.put("username", u.getUsername());
				UserInfo user=new UserInfo();
				user.setToken(token);
				user.setUsername(u.getUsername());
				user.setStatus(UserInfo.STATUS_SUCCESS);
				socket.send(tempToken,"login", user);
				Mono.just(user);
			}
		}
		return Mono.just(rs);
	}
}
