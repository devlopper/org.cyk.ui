package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import org.cyk.utility.common.security.Shiro;

@javax.servlet.annotation.WebListener
public class ContextListener extends org.cyk.ui.web.primefaces.ServletContextListener implements Serializable {
	private static final long serialVersionUID = -3211898049670089807L;

	protected void __addFoldersForUser__(Shiro.Ini ini){
		ini.addFoldersForUser("privates");
	}
	
}
