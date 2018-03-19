package com.thesys.common.security.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.thesys.architecture.core.util.CusAccessObjectUtil;
import com.thesys.base.core.util.DateUtil;
import com.thesys.core.filter.SessionListener;
import com.thesys.core.util.UserContainerSessionUtil;
import com.thesys.dwr.DwrSendMessageUtil;
import com.thesys.dwr.DwrSendTypeEnum;
import com.thesys.dwr.MessageResultDto;

public class CustomLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler  {
	
	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		super.onAuthenticationSuccess(request, response, authentication);
		System.out.println("CustomLoginHandler.onAuthenticationSuccess() is called!");
		UserContainerSessionUtil.getOnlineMemberIds().add(UserContainerSessionUtil.getAclUser().getId().toString());//记录在线用户Id
		//记录用户实际登录时间非访问时间
		HttpSession session = request.getSession();
		//当前登陆的Ip
		String currentIp = CusAccessObjectUtil.getIpAddress(request);
		//访问的浏览器类型，设置同一个浏览器可共用session,如果不同一种浏览器且为用一个版本时，会踢掉之前的账号
		String currentAgent = request.getHeader("USER-AGENT"); 
		
		String userid = UserContainerSessionUtil.getAclUser().getId().toString(); 
        if(SessionListener.sessionContext.getSessionMap().get(userid)!=null){  
        	
        	HttpSession userSession=(HttpSession)SessionListener.sessionContext.getSessionMap().get(userid);  
        	//如果跟已经登陆的账号ip及使用的浏览器一致的情况 不注销
        	String oldAgent = (String) userSession.getAttribute(userid.concat("agent"));
        	String oldIp = (String) userSession.getAttribute(userid.concat("ip"));
        	if(currentAgent.equals(oldAgent) && currentIp.equals(oldIp)){
        		return;
        	}
        	
        	MessageResultDto sendData = new MessageResultDto(DwrSendTypeEnum.SENDTYPE_COMPULSORYDOWNLINE.getSendType(), DwrSendTypeEnum.SENDTYPE_COMPULSORYDOWNLINE.getSendName(), DateUtil.format(new Date(),DateUtil.C_TIME_MM_DD_MM));
        	DwrSendMessageUtil.sendMessageThisUser(UserContainerSessionUtil.getAclUser().getName(), sendData);
            //注销在线用户  
            userSession.invalidate();   
            SessionListener.sessionContext.getSessionMap().remove(userid);  
            //清除在线用户后，更新map,替换map sessionid  
            SessionListener.sessionContext.getSessionMap().remove(session.getId());   
            SessionListener.sessionContext.getSessionMap().put(userid,session);   
        } else {  
            // 根据当前sessionid 取session对象。 更新map key=用户名 value=session对象 删除map  
            SessionListener.sessionContext.getSessionMap().put(userid,session);  
            session.setAttribute(userid.concat("agent"), currentAgent);
            session.setAttribute(userid.concat("ip"), currentIp);
            SessionListener.sessionContext.getSessionMap().remove(session.getId());  
        }
        
        
       /* AclUser aclUser =   this.aclUserService.findAclUserById(userid);
        
		MemberLoginLogDto memberLoginRecordDto = (MemberLoginLogDto) session.getAttribute(WebSessionListener.SESSION_ATTRIBUTE_MEMBERLOGINRECORD);
		if (!ValidateUtil.isEmpty(memberLoginRecordDto)&&ValidateUtil.isEmpty(memberLoginRecordDto.getLoginTime())) {
			if(!ValidateUtil.isEmpty(session.getAttribute(WebSessionListener.SPRING_SECURITY_CONTEXT))){
				UserDetails userDetails = (User) ((SecurityContext) session.getAttribute(WebSessionListener.SPRING_SECURITY_CONTEXT)).getAuthentication().getPrincipal();
				memberLoginRecordDto.setLoginTime(DateUtil.format(DateUtil.newDate(), DateUtil.C_TIME_PATTON_DEFAULT));
				
				MemberInformation memberInformation = this.memberInformationService.findMemberInformationByName(userDetails.getUsername());
				Enterprise enterprise = enterpriseService.findEnterpriseByCompyId(memberInformation.getCompy_id());
				session.setAttribute(WebSessionListener.SESSION_ATTRIBUTE_MEMBERLOGINRECORD,setMemberLoginRecordMemberInformation(memberLoginRecordDto,memberInformation));
				final OperateLogDto operateLogDto = new OperateLogDto(memberInformation.getMember_id(),memberInformation.getCompy_id(),OperateLogDto.ACTIVE_USER_LOGIN,enterprise.getTrade_identity());
				//匿名内部类 直接给一个线程发送数据到LOG系统,为了不阻塞程序
				LogExecutorService.getInstance().getExecutorService().execute(new Thread(new Runnable() {
					@Override
					public void run() {
						operateLogService.addOperateLog(operateLogDto);
					}
				}));
			}
		}*/
	}
	//设值
	/*private MemberLoginLogDto setMemberLoginRecordMemberInformation(MemberLoginLogDto memberLoginLogDto,AclUser aclUser){
		memberLoginLogDto.setMemberId(memberInformation.getMember_id());
		memberLoginLogDto.setCreateByUserCode(memberInformation.getMember_id());
		memberLoginLogDto.setCreateByUserName(memberInformation.getMember_name());
		memberLoginLogDto.setLastModifyByUserCode(memberInformation.getMember_id());
		memberLoginLogDto.setLastModifyByUserName(memberInformation.getMember_name());
		return memberLoginLogDto;
	}*/
			
}
