package org.cyk.ui.web.api.servlet.report;
import java.io.Serializable;

import javax.servlet.annotation.WebServlet;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;

@WebServlet(name=JasperReportReportBasedOnDynamicBuilderServlet.NAME,urlPatterns={UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER})
public class JasperReportReportBasedOnDynamicBuilderServlet extends AbstractJasperReportBasedOnDynamicBuilderServlet<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	public static final String NAME = "jasperReportReportBasedOnDynamicBuilderServlet";
	
}
