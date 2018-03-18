package com.thesys.base.configuration;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.thesys.architecture.core.context.ApplicationContextHolder;



public class IcZoomContextLoaderListener extends ContextLoaderListener{

	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext arg0) {
		WebApplicationContext webApplicationContext =  super.initWebApplicationContext(arg0);

		ApplicationContextHolder.setApplicationContext(webApplicationContext);
		return webApplicationContext;
	}

}