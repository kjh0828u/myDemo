package com.kjhapp.web;

import javax.servlet.http.HttpSession;

import com.kjhapp.domain.User;

public class HttpSessionUtils {
	public static final String USER_SESSION_KEY = "sessionedUser";
	
	//로그인 여부
	public static boolean isLoginUser(HttpSession session){
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
		if (sessionedUser == null){
			return false;
		}
		return true;
	}
	//로그인 된 user
	public static User getUserFormSession(HttpSession session){
		if(!isLoginUser(session)){
			return null;			
		}
		return (User)session.getAttribute(USER_SESSION_KEY);
	}
}