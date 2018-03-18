package com.thesys.login.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.dto.ResultDataDto;
import com.thesys.architecture.core.exception.RuntimeServiceException;
import com.thesys.base.core.util.DateUtil;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.base.sysmag.aclmenu.service.AclMenuService;
import com.thesys.base.sysmag.aclmodule.domain.AclModule;
import com.thesys.base.sysmag.acluser.domain.AclUser;
import com.thesys.base.sysmag.acluser.service.AclUserService;
import com.thesys.core.architecture.controller.BaseController;
import com.thesys.core.util.UserContainerSessionUtil;

@Controller
@RequestMapping("/")  
public class LoginController extends BaseController<BaseEntity>{
	
	public final static String MENU_ROOT="root";
	@Autowired
	private AclUserService aclUserService;
	
	@Autowired
	private AclMenuService aclMenuService;
	
	
    @InitBinder("aclUser")
    public void initBinder1(WebDataBinder binder){
    	binder.setFieldDefaultPrefix("aclUser.");
    }	
    
	/**
	 * 设
	 * @param loginUser
	 * @param request
	 * @param session
	 * @return
	 */
    @RequestMapping(value = "getCurrentLoginedUser")
	public @ResponseBody ResultDataDto login(HttpSession session) {	
		AclUser aclUser = UserContainerSessionUtil.getAclUser();
		if(ValidateUtil.isEmpty(aclUser)) {
			throw new RuntimeServiceException("找不到该用户信息");
		}
		if(!ValidateUtil.isEmpty("expireTime", aclUser)){
			throw new RuntimeServiceException("此帐户已被锁定,请联系管理员误");
		}
		/*
		if ((!(ValidateUtil.isEmpty(aclUser.getActiveday()))) && (!(ValidateUtil.isEmpty(aclUser.getLastmodfypwddate())))) {
		      Date date = DateUtil.addDays(aclUser.getLastmodfypwddate(), aclUser.getActiveday().intValue());
		      Date enddate = new Date();
		      if (DateUtil.after(enddate, date)) {
		    	  aclUser.setIsRedirection(true);
		      }
		      int ibetwwendays = DateUtil.getDiscrepantDays(enddate, date);
		      if (PassWordUtil.BEFORE_WARNING_PASSWORD_DAYS_7.intValue() >= Math.abs(ibetwwendays)) {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        aclUser.setMessage("你的密码有效期" + sdf.format(date) + ",还有" + String.valueOf(ibetwwendays) + "天即将到期，请及时修改密码");
		      }
		 }
		 */
		
		return new ResultDataDto(aclUser);
	}
	
    @RequestMapping(value = "findUserMenu")
    public @ResponseBody ResultDataDto findUserMenu(){
    	
		String uname=SecurityContextHolder.getContext().getAuthentication().getName();		
		AclUser user = aclUserService.findAclUserByName(uname) ;
    	String usedId = user.getId().toString();
		List<AclModule> details = aclMenuService.findUserAclMenu(usedId, MENU_ROOT, true);
		
		return new ResultDataDto(details);
		
    }
	// 登录成功跳转主界面
	@RequestMapping("toUserMain")
    public String toUserMain(){		
		return "redirect:/main.html?u="+SecurityContextHolder.getContext().getAuthentication().getName()+'&'+new Date().getTime();
    }
	
	@RequestMapping("/doOpfindAcluserByName")
	public @ResponseBody ResultDataDto doOpfindAcluserByName(String name){
		AclUser user = aclUserService.findAclUserByName(name) ;
		if ((!(ValidateUtil.isEmpty(user.getActiveday()))) && (!(ValidateUtil.isEmpty(user.getLastmodfypwddate())))) {
		      Date date = DateUtil.addDays(user.getLastmodfypwddate(), user.getActiveday().intValue());
		      Date enddate = new Date();
		      if (DateUtil.after(enddate, date)) {
		    	  user.setIsRedirection(true);
		      }
		    }
		return new ResultDataDto(user);
	}
	
	// 登录异常
	@RequestMapping(value = "getLoginError")
	public @ResponseBody ResultDataDto getLoginError(HttpSession session) {

		
		RuntimeException ex=(RuntimeException)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

		if(ex instanceof AuthenticationServiceException){
			
			if(ex.getCause() instanceof NullPointerException){
				//此帐户已被锁定,请联系管理员
				throw new RuntimeServiceException("此帐户已被锁定,请联系管理员误");
			}
			
			throw new RuntimeServiceException("用户名错误");
		}else if(ex instanceof BadCredentialsException){
			//密码错误
			throw new RuntimeServiceException("密码错误");
		}else if(ex instanceof DisabledException){
			//帐户锁定
			throw new RuntimeServiceException("此帐户已被锁定,请联系管理员");
		}else if(ex.getMessage().equals("checkcode")){
			throw new RuntimeServiceException("验证码有误");
		} else{
			//登陆验证错误
			throw new RuntimeServiceException("登陆验证错误");
		}
	}    
	
}
