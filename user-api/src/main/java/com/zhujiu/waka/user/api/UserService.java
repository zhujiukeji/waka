package com.zhujiu.waka.user.api;

import com.zhujiu.waka.user.obj.User;

public interface UserService {

	public User findByToken(String token);
	
	public User findByUserName(String username,String passwd);
	
	public String getTempToken();
	
	public boolean isTempTokenExist(String token);
	public User register(String username,String passwd1,String passwd2);
	
	
}
