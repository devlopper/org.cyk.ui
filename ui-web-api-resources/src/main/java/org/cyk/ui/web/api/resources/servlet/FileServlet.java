package org.cyk.ui.web.api.resources.servlet;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name="fileServlet",urlPatterns={FileServlet.PATH+"*"})
public class FileServlet extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	public static final String PATH = "/_cyk_servlet_file_/";
	
	@Override
	protected void initialisation(HttpServletRequest request, HttpServletResponse response) {
		
	}

}
