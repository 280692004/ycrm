package com.thesys.core.filter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {  
    public  static SessionContext sessionContext=SessionContext.getInstance();  
   
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {  
        sessionContext.AddSession(httpSessionEvent.getSession());  
    }  
  
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
//		 String userid=UserContainerSessionUtil.getMemberInformation().getMember_id();  
//	    HttpSession userSession=(HttpSession)SessionListener.sessionContext.getSessionMap().get(userid);  
    	//HttpSession session = httpSessionEvent.getSession();
 
	    //String kk =	(String) session.getAttribute("userid");
     
        sessionContext.DelSession(httpSessionEvent.getSession());  
    }  
} 
