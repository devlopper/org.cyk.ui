package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*@Log*/ @WebServlet(name="fileServlet",urlPatterns={"/_cyk_file/*"})
public class FileServlet extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Override
	protected void initialisation(HttpServletRequest request, HttpServletResponse response) {
		
	}

	@Override
	protected Boolean isAttachment(HttpServletRequest request, HttpServletResponse response) {
		return Boolean.TRUE;
	}
}
