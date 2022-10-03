package com.javabykiran.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeController {

	@RequestMapping("showRemainingTime")
	public String showRemainingTime(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute("timeremaining", (Integer)session.getAttribute("timeremaining")-1);
		System.out.println(session.getAttribute("timeremaining"));
		Integer remainingTime = (Integer) session.getAttribute("timeremaining");
		
		return remainingTime+"";
		// a=a+10
	}
}
