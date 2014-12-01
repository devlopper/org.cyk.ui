package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.file.JasperReportBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.file.report.Report;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebManager;

public abstract class AbstractJasperReportServlet extends AbstractFileServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@Inject protected JasperReportBusinessImpl reportBusiness;
	@Inject protected LanguageBusiness languageBusiness;
	
	protected Report<AbstractIdentifiable> report;
	
	@Override
	protected final void initialisation(HttpServletRequest request, HttpServletResponse response) {
		report = new Report<>();
		report.setColumns(reportBusiness.findColumns(EventType.class));
		report.setFileExtension(request.getParameter(UIManager.getInstance().getFileExtensionParameter()));
		build(request,response);
	}
	
	protected void build(HttpServletRequest request, HttpServletResponse response){
		reportBusiness.build(report,print(request, response));
	}
	
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

	protected Boolean print(HttpServletRequest request, HttpServletResponse response){
		try {
			return Boolean.parseBoolean(request.getParameter(WebManager.getInstance().getRequestParameterPrint()));
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}


}
