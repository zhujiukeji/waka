package com.zhujiu.waka.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zhujiu.waka.user.obj.User;

@Component
public class UserDao {
	private JdbcTemplate template;
	
	@Autowired
	public UserDao(JdbcTemplate template) {
		this.template=template;
	}
	
	public User findById(String id) {
		String sql="select id,username,passwd,salt from sys_user where id=?";
		List<User> list=template.query(sql, new RowMapper<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User u=new User();
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPasswd(rs.getString(3));
				u.setSalt(rs.getString(4));
				return u;
			}
			
		},id);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	public User findByUserName(String username) {
		String sql="select id,username,passwd,salt from sys_user where username=?";
		List<User> list=template.query(sql, new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User u=new User();
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPasswd(rs.getString(3));
				u.setSalt(rs.getString(4));
				return u;
			}
			
		},username);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

}
