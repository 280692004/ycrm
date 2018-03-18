package com.thesys.log.logthreadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogExecutorService {
	private static LogExecutorService logExecutorService;
	
	private final ExecutorService executorService;
	private LogExecutorService(){
		executorService = Executors.newCachedThreadPool();
	}
	
	public static LogExecutorService getInstance(){
		if(null == logExecutorService){
			synchronized (LogExecutorService.class) {
				if(null == logExecutorService){
					logExecutorService = new LogExecutorService();
				}
			}
		}
		return logExecutorService;
	}
	
	/**
	 * @return the executorService
	 */
	public ExecutorService getExecutorService() {
		return executorService;
	}
}
