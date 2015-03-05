package org.cyk.ui.web.api.servlet;

import java.io.Serializable;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.Report;

public abstract class AbstractBusinessReportServlet<IDENTIFIABLE extends AbstractIdentifiable,REPORT extends Report<?>> extends AbstractJasperReportServlet<IDENTIFIABLE,REPORT> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@SuppressWarnings("unchecked")
	@Override
	protected REPORT createReport(HttpServletRequest request,AbstractReportConfiguration<IDENTIFIABLE, REPORT> configuration) {
		Class<IDENTIFIABLE> aClass = (Class<IDENTIFIABLE>) classParameter(request);
		IDENTIFIABLE identifiable = (IDENTIFIABLE) identifiableParameter(request, (Class<AbstractIdentifiable>) aClass);
		return (REPORT) configuration.build((Class<IDENTIFIABLE>) aClass,Arrays.asList(identifiable),fileExtensionRequestParameter(request),printRequestParameter(request));
	}
	
	/*
	@SuppressWarnings({ "unchecked" })
	@Override
	protected REPORT createRepor(HttpServletRequest request,HttpServletResponse response) {	
		identifiable = (IDENTIFIABLE) identifiableParameter(request, (Class<AbstractIdentifiable>) clazz);
		
		AbstractReportConfiguration<IDENTIFIABLE,REPORT> builder = reportBusiness.findConfiguration(request.getParameter(UIManager.getInstance().getReportIdentifierParameter()));
			
		if(builder==null){
			return null;
		}
		return (REPORT) builder.build((Class<IDENTIFIABLE>) clazz,Arrays.asList(identifiable),fileExtensionRequestParameter(request),printRequestParameter(request));
	}
	*/
	
}
