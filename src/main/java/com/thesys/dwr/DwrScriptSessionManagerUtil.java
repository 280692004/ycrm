package com.thesys.dwr;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

/**
 * @author Kyle
 * 
 */
@SuppressWarnings("serial")
public class DwrScriptSessionManagerUtil extends DwrServlet {

	public void init() throws ServletException {

		Container container = ServerContextFactory.get().getContainer();

		ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);

		ScriptSessionListener listener = new ScriptSessionListener() {

			public void sessionCreated(ScriptSessionEvent ev) {

				HttpSession session = WebContextFactory.get().getSession();

				String userName = (String) session.getAttribute("userName");

				System.out.println(">>>>>>>>>a ScriptSession is created!");

				ev.getSession().setAttribute("userName", userName);

			}

			public void sessionDestroyed(ScriptSessionEvent ev) {

				System.out.println(">>>>>>>>a ScriptSession is distroyed");

			}

		};

		manager.addScriptSessionListener(listener);
	}
}