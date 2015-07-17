package org.cyk.ui.web.api.servlet.report;

import java.io.Serializable;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFile;
import org.cyk.system.root.model.file.report.ReportBasedOnTemplateFileConfiguration;

public abstract class AbstractBusinessReportBasedOnTemplateFileServlet<IDENTIFIABLE extends AbstractIdentifiable,REPORT extends ReportBasedOnTemplateFile<?>> extends AbstractJasperReportServlet<IDENTIFIABLE,REPORT> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@SuppressWarnings("unchecked")
	@Override
	protected REPORT createReport(HttpServletRequest request,AbstractReportConfiguration<IDENTIFIABLE, REPORT> configuration) {
		Class<IDENTIFIABLE> aClass = (Class<IDENTIFIABLE>) identifiableClassParameter(request);
		IDENTIFIABLE identifiable = (IDENTIFIABLE) identifiableParameter(request, (Class<AbstractIdentifiable>) aClass);
		ReportBasedOnTemplateFileConfiguration<IDENTIFIABLE, REPORT> c = (ReportBasedOnTemplateFileConfiguration<IDENTIFIABLE, REPORT>) configuration;
		return (REPORT) c.build((Class<IDENTIFIABLE>) aClass,Arrays.asList(identifiable),fileExtensionRequestParameter(request),printRequestParameter(request),
				request.getParameterMap());
	}
	
}
