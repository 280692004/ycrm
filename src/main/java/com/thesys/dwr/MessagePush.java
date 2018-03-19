package com.thesys.dwr;

import javax.servlet.ServletException;

import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContextFactory;
/**
 * 消息推送
 * @author Kyle
 *
 */
public class MessagePush {

	public void onPageLoad(String userName) {

		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();

		scriptSession.setAttribute("userName", userName); // 把前台传入的id保存
		
		DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil();

		try {

			dwrScriptSessionManagerUtil.init();

		} catch (ServletException e) {

			e.printStackTrace();

		}

	}
}
