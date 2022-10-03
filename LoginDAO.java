package com.javabykiran.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javabykiran.entity.Users;

@Repository
public class LoginDAO {

	@Autowired
	SessionFactory factory;
	Session session;
	
	
	public String getPassword(String username) {
		
		session=factory.openSession();
		System.out.println("session created..");
		
		System.out.println(username);
		Users user=session.get(Users.class,username);  
		if(user==null)
			return null;
		else
			
		return user.getPassword();
	}

}
