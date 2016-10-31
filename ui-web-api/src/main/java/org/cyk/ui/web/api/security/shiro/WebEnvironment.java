package org.cyk.ui.web.api.security.shiro;

import java.io.Serializable;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.web.env.IniWebEnvironment;

public class WebEnvironment extends IniWebEnvironment implements Serializable {

	private static final long serialVersionUID = -9184798357687299784L;
	
	public static WebEnvironmentListener LISTENER = new WebEnvironmentListener.Adapter.Default();// WebEnvironmentAdapter();
	
	@Override
	protected Ini createIni(String arg0, boolean arg1) throws ConfigurationException {
		Ini ini = new Ini();
		LISTENER.ini(this,ini);
		return ini;
	}

}
