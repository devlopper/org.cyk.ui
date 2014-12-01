package org.cyk.ui.web.api.security.shiro;

import org.apache.shiro.config.Ini;

public interface WebEnvironmentListener {

	void ini(WebEnvironment environment,Ini anIni);
	
}
