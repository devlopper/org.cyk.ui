package org.cyk.ui.web.api.servlet.report;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;

import org.cyk.system.root.model.AbstractIdentifiable;

@WebServlet(name="jasperReportReportBasedOnDynamicBuilderServlet",urlPatterns={"/private/__tools__/export/_cyk_report_/_dynamicbuilder_/_jasper_/"})
public class JasperReportReportBasedOnDynamicBuilderServlet extends AbstractJasperReportBasedOnDynamicBuilderServlet<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	

}
