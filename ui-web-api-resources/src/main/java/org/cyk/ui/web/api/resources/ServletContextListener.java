package org.cyk.ui.web.api.resources;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.RequestHelper;

public class ServletContextListener extends AbstractBean implements javax.servlet.ServletContextListener , Serializable  {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//UniformResourceLocatorHelper.TOKEN_DEFAULT_VALUE_MAP.put(UniformResourceLocatorHelper.TokenName.HOST, servletContextEvent.getServletContext().getse);
		
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_CONTEXT = StringUtils.replace(servletContextEvent.getServletContext().getContextPath(),Constant.CHARACTER_SLASH.toString(),Constant.EMPTY_STRING);
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP = new LinkedHashMap<>();
		UniformResourceLocatorHelper.PathStringifier.Adapter.Default.DEFAULT_SEQUENCE_REPLACEMENT_MAP.put(WebResourcesManager.FILE_STATIC_EXTENSION, WebResourcesManager.FILE_PROCESSING_EXTENSION);
		
		ClassHelper.getInstance().map(RequestHelper.Listener.class, org.cyk.ui.web.api.resources.helper.RequestHelper.Listener.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

}
