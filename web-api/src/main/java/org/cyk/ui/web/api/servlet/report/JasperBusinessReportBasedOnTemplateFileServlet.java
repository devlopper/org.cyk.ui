package org.cyk.ui.web.api.servlet.report;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;

@WebServlet(name="jasperBusinessReportServlet",urlPatterns={"/private/__tools__/export/_cyk_report_/_business_/_jasper_/"})
public class JasperBusinessReportBasedOnTemplateFileServlet extends AbstractBusinessReportBasedOnTemplateFileServlet<AbstractIdentifiable,ReportBasedOnTemplateFile<AbstractIdentifiable>> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	

}
