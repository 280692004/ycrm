package com.thesys.core.proxool;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;



@SuppressWarnings("all")
public class ProxoolListener implements ServletContextListener {
	static Logger logger = Logger.getLogger(ProxoolListener.class.getName());

	private static final String XML_FILE_PROPERTY = "xmlFile";
	private static final String PROPERTY_FILE_PROPERTY = "propertyFile";
	private static final String AUTO_SHUTDOWN_PROPERTY = "autoShutdown";

	@SuppressWarnings("unused")
	private boolean autoShutdown = true;

	/***
	 * destroy pool
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		 ProxoolFacade.shutdown();  
		System.out.println("destroy database pool....");
	}

	
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("database pool init....");
		 // 对应servlet的init方法中ServletConfig.getServletContext()
		ServletContext context = contextEvent.getServletContext();
		String appDir = contextEvent.getServletContext().getRealPath("/");
		Properties properties = new Properties();
		try {
			Enumeration names = context.getInitParameterNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				String value = context.getInitParameter(name);

				//xml文件
				if (name.equals(XML_FILE_PROPERTY)) {
					try {
						File file = new File(value);
						if (file.isAbsolute()) {
							JAXPConfigurator.configure(value, false);
						} else {
							JAXPConfigurator.configure(appDir + File.separator
									+ value, false);
						}
					} catch (ProxoolException e) {
						logger.error("Problem configuring " + value, e);
					}
					//property文件
				} else if (name.equals(PROPERTY_FILE_PROPERTY)) {
					File file = new File(value);
					if (file.isAbsolute()) {
						PropertyConfigurator.configure(value);
					} else {
						PropertyConfigurator.configure(appDir + File.separator
								+ value);
					}
				} else if (name.equals(AUTO_SHUTDOWN_PROPERTY)) {
					autoShutdown = Boolean.valueOf(value).booleanValue();
				} else if (name.startsWith("jdbc")) { // 此处以前是PropertyConfigurator.PREFIX改为jdbc
					properties.setProperty(name, value);
				}
			}

			if (properties.size() > 0) {
				PropertyConfigurator.configure(properties);
			}
			System.out.println("database pool init finished....");
		} catch (Exception ex) {
			logger.error("init error in ProxoolListener ", ex);
		}

	}

}
