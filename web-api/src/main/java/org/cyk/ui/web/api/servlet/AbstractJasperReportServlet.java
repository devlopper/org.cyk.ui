package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.file.JasperReportBusinessImpl;
import org.cyk.system.root.model.file.report.AbstractReport;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.ui.web.api.WebManager;

public abstract class AbstractJasperReportServlet<MODEL,REPORT extends AbstractReport<?>/*,CONFIGURATION extends AbstractReportConfiguration<?, AbstractReport<?>>*/> extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@Inject protected JasperReportBusinessImpl reportBusiness;
	@Inject protected LanguageBusiness languageBusiness;
	
	protected REPORT report;
	//protected String url;
	//protected Class<?> clazz;
	
	@SuppressWarnings("unchecked")
	@Override
	protected final void initialisation(HttpServletRequest request, HttpServletResponse response) {
		//clazz = classParameter(request);
		AbstractReportConfiguration<MODEL, AbstractReport<?>> configuration = configuration(reportIdentifierRequestParameter(request));
		if(configuration==null)
			;
		else
			report = createReport(request,(AbstractReportConfiguration<MODEL, REPORT>) configuration );
	}
	
	protected AbstractReportConfiguration<MODEL, AbstractReport<?>> configuration(String identifier){
		return reportBusiness.findConfiguration(identifier);
	}
	
	protected abstract REPORT createReport(HttpServletRequest request,AbstractReportConfiguration<MODEL, REPORT> configuration);
	
	@Override
	protected byte[] bytes(HttpServletRequest request, HttpServletResponse response) {
		return report.getBytes();
	}

	@Override
	protected String fileName(HttpServletRequest request, HttpServletResponse response) {
		return report.getFileName();
	}

	@Override
	protected String fileExtension(HttpServletRequest request, HttpServletResponse response) {
		return report.getFileExtension();
	}

	@Override
	protected Boolean isAttachment(HttpServletRequest request, HttpServletResponse response) {
		return Boolean.FALSE;
	}

	protected Boolean printRequestParameter(HttpServletRequest request){
		try {
			return Boolean.parseBoolean(request.getParameter(WebManager.getInstance().getRequestParameterPrint()));
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}
	
	protected String reportIdentifierRequestParameter(HttpServletRequest request){
		return requestParameter(request, uiManager.getReportIdentifierParameter());
	}


}
