package com.thesys.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.thesys.base.core.util.ValidateUtil;
import com.thesys.core.util.XsslHttpServletRequestWrapper;

@SuppressWarnings("all")
public class UrlRecordFilter implements Filter {
	
	/*
	 * @author Athos   
	 * @date 2016-8-22 上午11:35:27
	 */
	@Override
	public void destroy() {
		
	}

	/*
	 * @author Athos   
	 * @date 2016-8-22 上午11:35:27
	 */
	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
	        //把ServletRequest和ServletResponse转换成真正的类型
	        HttpServletRequest req = (HttpServletRequest)request;
	        
			HttpSession session = req.getSession();
	        
	        XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest)request);  
	       
	        //记录浏览URL 
	        String fullRequestURL = req.getRequestURL().toString();
	        String queryString=null,paramType=null,param=null;
	        //记录参数 与 参数类型
	        queryString = req.getQueryString();
	        int targetIdx = ValidateUtil.isEmpty(queryString)?-1:queryString.indexOf("=");
	        if(targetIdx!=-1){
	        		paramType = queryString.substring(0,targetIdx);
	  	        	param = queryString.substring(targetIdx+1,queryString.length());
	        }
	      
	        chain.doFilter(xssRequest , response);   
	    }

	/*
	 * @author Athos   
	 * @date 2016-8-22 上午11:35:27
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}