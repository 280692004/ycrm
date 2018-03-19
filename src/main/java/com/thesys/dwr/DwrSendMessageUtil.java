package com.thesys.dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;

import com.thesys.util.JsonUtil;

/**
 * Dwr 消息推送
 * @author Administrator
 *
 */
public class DwrSendMessageUtil {

	/**
	 * 给单独一个人发送
	 * @param userName  单个用户名
	 * @param messageresult
	 */
	public static void sendMessageThisUser(final String userName,final MessageResultDto messageresult){
		
        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {    
            public boolean match(ScriptSession session){  //验证符合条件的发送人  
                if (session.getAttribute("userName") == null){    
                    return false;    
                }else  {  
                    return (session.getAttribute("userName")).equals(userName);    
                }  
            }    
        }, new Runnable(){    
                
            private ScriptBuffer script = new ScriptBuffer();    
            public void run(){    
                    
                script.appendCall("showMessage", JsonUtil.toJson(messageresult));    
                Collection<ScriptSession> sessions = Browser.getTargetSessions();    
                for (ScriptSession scriptSession : sessions){    
                    scriptSession.addScript(script);    
                }    
            }    
        }); 
	}
	
	/**
	 * 同时给多个用户发送信息 
	 * @param userNames  final String userNames = "kyle,admin,***";   
	 * @param messageresult
	 */
	public static void sendMessageThisUsers(final String userNames, final MessageResultDto messageresult){
        
        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {    
            public boolean match(ScriptSession session){    
                if (session.getAttribute("userName") == null){    
                    return false;    
                }else  {  
                    String attribute = (String) session.getAttribute("userName");  
                    return (userNames.contains(attribute));  
                }  
            }    
        }, new Runnable(){    
                
            private ScriptBuffer script = new ScriptBuffer();    
                
            public void run(){    
                    
                script.appendCall("showMessage", messageresult);    
                Collection<ScriptSession> sessions = Browser.getTargetSessions();    
                for (ScriptSession scriptSession : sessions){    
                    scriptSession.addScript(script);    
                }    
            }    
        });    
	}
	
}
