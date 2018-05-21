package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.servlet.ServletContextEvent;

import org.cyk.system.test.business.MenuBuilder;
import org.cyk.utility.common.security.Shiro;

@javax.servlet.annotation.WebListener
public class ContextListener extends org.cyk.ui.web.primefaces.ServletContextListener implements Serializable {
	private static final long serialVersionUID = -3211898049670089807L;

	@Override
	public void __contextInitialized__(ServletContextEvent event) {
		super.__contextInitialized__(event);
	}
	
	protected void __addFoldersForUser__(Shiro.Ini ini){
		ini.addFoldersForUser("privates");
	}
	
	@Override
	protected Class<?> __getMenuBuilderClass__() {
		return MenuBuilder.class;
	}
	
}
