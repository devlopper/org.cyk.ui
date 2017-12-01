package org.cyk.ui.web.api.resources.shiro;

import java.io.Serializable;

import org.apache.shiro.web.filter.authc.UserFilter;

public class AuthorisationFilter extends UserFilter implements Serializable {

	private static final long serialVersionUID = -5545340462765870623L;

	public AuthorisationFilter() {
		//setLoginUrl("/login");
	}
	
}
