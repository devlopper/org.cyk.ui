package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;

import org.cyk.system.root.model.AbstractIdentifiable;

@WebServlet(name="jasperReportDataTableServlet",urlPatterns={"/private/__tools__/export/_cyk_report_/_datatable_/_jasper_/"})
public class JasperReportDataTableServlet extends AbstractJasperReportDataTableServlet<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	

}
