package com.thesys.common.security.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.thesys.base.util.SystemConstants;
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{
	
	private FilterInvocationSecurityMetadataSource securityMetadataSource;  
	@Override
	public Class<?> getSecureObjectClass() {
		
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return securityMetadataSource;
	}

	@Override
	public void destroy() {
		
	} 
	
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		//super.beforeInvocation(fi);源码    
        //1.获取请求资源的权限    
        //执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);    
        //2.是否拥有权限    
        //this.accessDecisionManager.decide(authenticated, object, attributes);
		
		System.out.println("------------MyFilterSecurityInterceptor.doFilter()-----------开始拦截器了....");  
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		Map<String, Boolean> notInterceptionUrl =  getNotInterceptionUrl();
		String accessUrl = ((HttpServletRequest) request).getServletPath(); 
		if(accessUrl.equals("/undefined")){
			return;
		}
		
		Boolean hasLogin=SystemConstants.hasLogin();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if((hasLogin && notInterceptionUrl.containsKey(accessUrl))||//不进行拦截的链接
        		(null!=request.getSession() && null!=userName && !SystemConstants.SPRING_SECURITY_ANONYMOUSUSER.equals(userName))){//已经登录
			
			FilterInvocation fi = new FilterInvocation(request, response, chain);  
	        InterceptorStatusToken token = super.beforeInvocation(fi);  
	        try {  
	            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }finally{  
	            super.afterInvocation(token,null);  
	        }
	        
	        return;
	        
		}else{
            System.out.println("非法访问被过滤");
            if(-1 != accessUrl.indexOf("html")){
            	 req.getRequestDispatcher(accessUrl).forward(request, response);
            }
            //response.sendRedirect(getMainRul(request));
            //RequestDispatcher dispatcher = request.getRequestDispatcher(RandomUtil.addRandomTimeStampToUrl("/index.html"));
            //req.getRequestDispatcher(accessUrl).forward(request, response);                        
        }
	}
	
	/**
	 * 获取访问根路径
	 * @param request
	 * @return
	private String getMainRul(HttpServletRequest request){
		return request.getScheme().concat("://").concat(request.getServerName()).concat(":").concat(String.valueOf(request.getServerPort()));
	}*/

	
	/**
	 * kyle
	 * 获取不需要连接的请求
	 * @return
	 */
	private Map<String, Boolean> getNotInterceptionUrl(){
		Map<String, Boolean> map = new HashMap<String, Boolean>(); 
		map.put("/getCurrentLoginedUser.action",true);
		map.put("/getCurrentLoginedUserShowType.action", true);
		map.put("/toMembercenter.action", true);
		map.put("/toShoppingCart.action", true);
		map.put("/checklogin", true);
		map.put("/dwr", true);
		map.put("/404", true);
		return map;
	}
	
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}


	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

}
