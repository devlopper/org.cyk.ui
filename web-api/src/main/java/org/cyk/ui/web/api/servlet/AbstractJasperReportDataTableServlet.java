package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.system.root.model.file.report.ReportTableConfiguration;

public abstract class AbstractJasperReportDataTableServlet<IDENTIFIABLE extends AbstractIdentifiable> extends 
	AbstractJasperReportServlet<IDENTIFIABLE,ReportTable<IDENTIFIABLE>> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected ReportTable<IDENTIFIABLE> createReport(HttpServletRequest request,AbstractReportConfiguration<IDENTIFIABLE, ReportTable<IDENTIFIABLE>> configuration) {
		return ((ReportTableConfiguration<IDENTIFIABLE, ReportTable<IDENTIFIABLE>>) configuration)
			.build((Class<IDENTIFIABLE>) classParameter(request),fileExtensionRequestParameter(request),printRequestParameter(request));
	}
	
	/*
	@SuppressWarnings("unchecked") 
	@Override
	protected ReportTable<IDENTIFIABLE> createReport(HttpServletRequest request,HttpServletResponse response,
			AbstractReportConfiguration<?,ReportTable<?>> configuration) {
		
		AbstractReportConfiguration<IDENTIFIABLE,ReportTable<IDENTIFIABLE>> b = 
				reportBusiness.findConfiguration(request.getParameter(UIManager.getInstance().getReportIdentifierParameter()));
		
		ReportTableConfiguration<IDENTIFIABLE,ReportTable<IDENTIFIABLE>> builder = (ReportTableConfiguration<IDENTIFIABLE, ReportTable<IDENTIFIABLE>>) b;
		
		if(builder==null){
			return null;
		}
		return builder.build((Class<IDENTIFIABLE>) clazz,fileExtensionRequestParameter(request),printRequestParameter(request));
		
		//return (ReportTable<IDENTIFIABLE>) reportBusiness.buildTable(clazz,fileExtensionRequestParameter(request),printRequestParameter(request));
	}
	*/
}
