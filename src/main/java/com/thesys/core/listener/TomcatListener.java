package com.thesys.core.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.thesys.log.logthreadpool.LogExecutorService;

public class TomcatListener implements ServletContextListener {

	/**
	 * Tomcat 关闭时,关闭线程池
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ExecutorService threadPool = LogExecutorService.getInstance().getExecutorService();
		threadPool.shutdown();
		try {
			if(!threadPool.awaitTermination(10, TimeUnit.SECONDS)){
				threadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			 System.out.println("线程池关闭被中断,强制关闭线程池!" + e);  
			 threadPool.shutdownNow();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}

}
