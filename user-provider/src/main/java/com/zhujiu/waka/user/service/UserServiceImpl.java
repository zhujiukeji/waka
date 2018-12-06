package com.zhujiu.waka.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhujiu.waka.user.api.UserService;
import com.zhujiu.waka.user.dao.UserDao;
import com.zhujiu.waka.user.obj.User;

@Service(interfaceClass=UserService.class,version="1.0.0")
@Component
public class UserServiceImpl implements UserService {

	private final static String TOKEN_STRIX="_user_token:";
	private final static String TEMP_TOKEN_STRIX="_temp_token:";
	private StringRedisTemplate redis;
	private UserDao dao;
	@Autowired
	public UserServiceImpl(UserDao dao,StringRedisTemplate redis) {
		this.dao=dao;
		this.redis=redis;
	}
	@Override
	public User findByToken(String token) {
		if(token==null) {
			return null;
		}
		String idStr=redis.opsForValue().get(TOKEN_STRIX+token);
		if(idStr!=null&&idStr.length()>0) {
			User u=dao.findById(idStr);
			return u;
		}
		return null;
	}
	

	@Override
	public User findByUserName(String username, String passwd) {
		if(username==null||username.length()==0||passwd==null||passwd.length()==0) {
			return null;
		}
		User u=dao.findByUserName(username);
		if(u!=null) {
			String passwd1=DigestUtils.md5DigestAsHex((passwd+"_"+u.getSalt()).getBytes());
			if(passwd1.equals(u.getPasswd())) {
				String token=UUID.randomUUID().toString();
				u.setToken(token);
				redis.opsForValue().set(TOKEN_STRIX+token, u.getId()+"");
				return u;
			}
		}
		return null;
	}

	@Override
	public User register(String username, String passwd1, String passwd2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTempToken() {
		String token=UUID.randomUUID().toString();
		redis.opsForValue().set(TEMP_TOKEN_STRIX+token, "");
		return token;
	}
	@Override
	public boolean isTempTokenExist(String token) {
		if(token==null) {
			return false;
		}
		return redis.hasKey(TEMP_TOKEN_STRIX+token);
	}

}
