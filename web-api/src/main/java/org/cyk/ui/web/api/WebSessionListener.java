package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.cyk.utility.common.cdi.AbstractBean;

@WebListener
public class WebSessionListener extends AbstractBean implements HttpSessionListener,Serializable {

	private static final long serialVersionUID = -756924141162339544L;

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		//System.out.println(" --- WebSessionListener.sessionCreated() ---");
		//debug(get(AbstractUserSession.class, "userSession"));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		//System.out.println(" --- WebSessionListener.sessionDestroyed() ---");
		//AbstractUserSession session = get(AbstractUserSession.class, "userSession");
		//debug(session);
		//if(Boolean.FALSE.equals(session.getLogoutCalled()))
		//	session.autoLogout();
	}
	
	/**/
	/*
	private <T> T get(Class<T> aClass,String name){
		FacesContext context = FacesContext.getCurrentInstance();
		T object = context.getApplication().evaluateExpressionGet(context, "#"+"{"+name+"}", aClass);
		return object;
	}
	*/

}
