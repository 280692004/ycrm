package com.thesys.core.architecture.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;

import com.google.gson.Gson;
import com.thesys.base.core.util.GenericeClassUtils;
import com.thesys.base.core.util.ValidateUtil;
import com.thesys.architecture.base.entity.BaseEntity;
import com.thesys.architecture.core.dto.FlexiPageDto;
import com.thesys.architecture.core.dto.ResultDataDto;

@SuppressWarnings("all")
public class BaseController<T extends BaseEntity> {
	
	/**
	 * 区域编码
	 */
    @Autowired  
    private LocaleResolver localeResolver;  
	
	// 异常信息拦截，返回
    @ExceptionHandler(Exception.class)   //在Controller类中添加该注解方法即可(注意：添加到某个controller，只针对该controller起作用)  
    public void exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {    
        ex.printStackTrace();
    	ResultDataDto resultDataDto = new ResultDataDto(ex);
        response.setContentType("text/html");
        if(!ValidateUtil.isEmpty(resultDataDto.getCode())){
        	response.setStatus(Integer.valueOf(resultDataDto.getCode()));	
        }
        
		response.getWriter().write(new Gson().toJson(resultDataDto));
    }
	
	private FlexiPageDto pages;
	
	protected Class<T> entityClass = (Class<T>)GenericeClassUtils.getSuperClassGenricType(this.getClass(), 0);
	
	protected ModelMap getModelMap(){
		
		ModelMap modelMap = new ModelMap(); 		
		
		modelMap.addAttribute("pages", this.getPages());		
		return modelMap;
	}
	
	/**
	 * 执行每个请求方法前都需要执行的准备工作
	 */
    public void prepare() {
    	ActionHolder.setAction(this);
    }
	    
	/**
	 * 在每一个类中需要重置
	 * @return
	 */
	protected String getActionNamespace(){
		return "";
	}
	/**
	 * 在每一个类中需要重置
	 * @return
	 */
	protected String getTypeName(){
		return "";
	}

	public FlexiPageDto getPages() {
		if (null==pages) {
			this.pages = new FlexiPageDto();
		}
		return this.pages;
	}

	public void setPages(FlexiPageDto pages) {
		this.pages = pages;
	}

	public LocaleResolver getLocaleResolver() {
		return localeResolver;
	}

	public void setLocaleResolver(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}	
	
}
