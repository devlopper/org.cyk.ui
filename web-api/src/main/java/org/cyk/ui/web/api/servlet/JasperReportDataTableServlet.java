package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebManager;

@WebServlet(name="jasperReportDataTableServlet",urlPatterns={"/_cyk_report_/_datatable_/_jasper_/"})
public class JasperReportDataTableServlet extends AbstractJasperReportDataTableServlet<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class<AbstractIdentifiable> identifiableClass(HttpServletRequest request, HttpServletResponse response) {
		return (Class<AbstractIdentifiable>) UIManager.getInstance().classFromKey(request.getParameter(WebManager.getInstance().getRequestParameterClass())).getClazz();
	}


}
