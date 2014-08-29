package org.cyk.ui.web.vaadin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.vaadin.server.VaadinServlet;

//TODO why we cannot use that and web.xml
//@WebServlet(value = "/*", asyncSupported = true)
//@VaadinServletConfiguration(productionMode = false, ui = IndexPage.class)
//@WebInitParam(name="UIProvider",value="com.vaadin.cdi.CDIUIProvider")
public class Servlet extends VaadinServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1191138560212394558L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		

	}
}