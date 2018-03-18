package com.thesys.core.architecture.controller;
@SuppressWarnings("all")
public class ActionHolder {

	private static ThreadLocal<BaseController> threadLocalAction = new ThreadLocal<BaseController>();

	
	public static void setAction(BaseController action) {
		threadLocalAction.set(action);
	}
	
	public static BaseController getAction() {
		return threadLocalAction.get();
	}
	
	public static void removeAction() {
		threadLocalAction.remove();
	}
}
