package org.cyk.ui.web.api.resources.shiro;

import java.io.Serializable;

import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.cyk.utility.common.security.SecurityHelper;
import org.cyk.utility.common.security.Shiro;

public class IniWebEnvironment extends org.apache.shiro.web.env.IniWebEnvironment implements Serializable {

	private static final long serialVersionUID = -9184798357687299784L;
	
	@Override
	protected Ini createIni(String configLocation, boolean required) throws ConfigurationException {
		SecurityHelper.getInstance().initialize();
		//System.out.println("IniWebEnvironment.createIni()");
		//System.out.println(Shiro.Ini.getInstance());
		return Shiro.Ini.getInstance();
	}

}
