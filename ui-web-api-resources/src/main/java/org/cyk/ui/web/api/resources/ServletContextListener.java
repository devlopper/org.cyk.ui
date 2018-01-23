package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.security.Shiro;

public class ServletContextListener extends AbstractBean implements javax.servlet.ServletContextListener , Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Override
	public final void contextInitialized(ServletContextEvent servletContextEvent) {
		__contextInitialized__(servletContextEvent);	
		__initialiseSecutity__(servletContextEvent);
	}
	
	protected void __contextInitialized__(ServletContextEvent servletContextEvent){}
	
	protected void __initialiseSecutity__(ServletContextEvent servletContextEvent){
		Shiro.Ini ini = Shiro.Ini.getInstance().clean();
		__addUsers__(ini);
		__addFoldersForUser__(ini);
		__addLoginUrl__(ini);
		logInfo("security initialised");
	}
	
	protected void __addUsers__(Shiro.Ini ini){
		ini.addUsers("admin", "123","user1","123","user2","123");
	}
	
	protected void __addFoldersForUser__(Shiro.Ini ini){
		ini.addFoldersForUser("private");
	}
	
	protected void __addLoginUrl__(Shiro.Ini ini){
		ini.addLoginUrl("/public/security/login.jsf");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}
	
	

}
