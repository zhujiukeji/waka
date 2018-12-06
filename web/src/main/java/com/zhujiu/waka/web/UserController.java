package com.zhujiu.waka.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.zhujiu.waka.socket.api.SocketService;
import com.zhujiu.waka.user.api.UserService;
import com.zhujiu.waka.user.obj.User;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/user")
public class UserController {
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
	public Mono<Map<String,Object>> loginApp(String username,String passwd){
		Map<String,Object> rs=new HashMap<String,Object>();
		User u=service.findByUserName(username, passwd);
		if(u==null) {
			rs.put("errorCode", 101);
			return Mono.just(rs);
		}else {
			rs.put("errorCode", 0);
			rs.put("token", u.getToken());
			rs.put("username", u.getUsername());
			
			return Mono.just(rs);
		}
	}
	@RequestMapping("loginConfirm")
	@ResponseBody
	public Mono<Map<String,Object>> loginPcConfirm(String token,String tempToken){
		Map<String,Object> rs=new HashMap<String,Object>();
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
				socket.send(tempToken, JSONObject.toJSONString(rs));
			}
		}
		return Mono.just(rs);
	}
}
