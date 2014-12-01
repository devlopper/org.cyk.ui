package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;

public abstract class AbstractJasperReportDataTableServlet<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractJasperReportServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@Inject private GenericBusiness genericBusiness;
	
	@Override
	protected void build(HttpServletRequest request, HttpServletResponse response) {
		Class<IDENTIFIABLE> clazz = identifiableClass(request, response);
		report.setTitle(languageBusiness.findText("report.datatable.title", new Object[]{UIManager.getInstance().textOfClass(clazz)}));
		report.setFileName(report.getTitle());
		report.setColumns(reportBusiness.findColumns(clazz));
		for(AbstractIdentifiable identifiable : genericBusiness.use(clazz).find().all())
			report.getDataSource().add(identifiable); 
		super.build(request,response);
	}
	
	protected abstract Class<IDENTIFIABLE> identifiableClass(HttpServletRequest request, HttpServletResponse response);

}
