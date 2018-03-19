package com.thesys.core.listener;

import java.util.Date;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.core.filter.SessionListener;
import com.thesys.core.util.UserContainerSessionUtil;
import com.thesys.log.memberloginrecord.dto.MemberLoginLogDto;

public class WebSessionListener implements HttpSessionListener, HttpSessionAttributeListener,ServletRequestListener {
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
	public static final String SESSION_ATTRIBUTE_MEMBERLOGINRECORD = "memberLoginRecord";
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// 监听器不受SPRING 管理,所以使用此方式
		HttpSession session = event.getSession();
		MemberLoginLogDto memberLoginLogDto = new MemberLoginLogDto(DateUtil.format(DateUtil.newDate(), DateUtil.C_TIME_PATTON_DEFAULT)).setAnonymous();
		session.setAttribute(WebSessionListener.SESSION_ATTRIBUTE_MEMBERLOGINRECORD,memberLoginLogDto);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// 记录用户登出时间(以最后一次访问SESSION为准)
		HttpSession session = event.getSession();
		if (!ValidateUtil.isEmpty(session.getAttribute(WebSessionListener.SPRING_SECURITY_CONTEXT))) {
			final MemberLoginLogDto memberLoginRecordDto = (MemberLoginLogDto) session.getAttribute(WebSessionListener.SESSION_ATTRIBUTE_MEMBERLOGINRECORD);
			//因为系统销毁SESSION 可能不及时,所以手动清一下属性
			session.removeAttribute(WebSessionListener.SESSION_ATTRIBUTE_MEMBERLOGINRECORD);
			memberLoginRecordDto.setLogoutTime(DateUtil.format(new Date(session.getLastAccessedTime()), DateUtil.C_TIME_PATTON_DEFAULT));
			//从在线的memberId集合删除登录用户
			UserContainerSessionUtil.getOnlineMemberIds().remove(memberLoginRecordDto.getMemberId());
			 HttpSession userSession=(HttpSession)SessionListener.sessionContext.getSessionMap().get(memberLoginRecordDto.getMemberId());  
	            //注销在线用户  
			 if(!ValidateUtil.isEmpty(userSession)){
				   userSession.invalidate();  
			   }
	           
	            SessionListener.sessionContext.getSessionMap().remove(memberLoginRecordDto.getMemberId());  
	            //清除在线用户后，更新map,替换map sessionid  
	            SessionListener.sessionContext.getSessionMap().remove(session.getId());   
	            
		} 
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		request = (HttpServletRequest)event.getServletRequest();
	}
	
}
