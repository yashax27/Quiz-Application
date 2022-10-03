package com.javabykiran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javabykiran.dao.LoginDAO;
import com.javabykiran.entity.User;

/* Business logic methods should be written in Service Class , 
 * which is annonated with @Service annotation
 * 
 * In service class , we define reference of DAO class .
 * 
 * */
@Service
public class LoginService {

	@Autowired
	LoginDAO dao;
		
	public boolean validate(User user)
	{
		String dbPassword=dao.getPassword(user.getUsername());
		
		if(dbPassword==null)
			return false;
		
		if(dbPassword.equals(user.getPassword()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
	
}
// Browser ==> Controller==> Service==> DAO