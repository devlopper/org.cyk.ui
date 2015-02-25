package org.cyk.ui.web.api.servlet;
import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.ReportTable;
import org.cyk.ui.api.UIManager;

public abstract class AbstractJasperReportDataTableServlet<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractJasperReportServlet<ReportTable<IDENTIFIABLE>> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;

	@Inject private GenericBusiness genericBusiness;
	
	@Override
	protected ReportTable<IDENTIFIABLE> createReport() {
		return new ReportTable<IDENTIFIABLE>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void build(HttpServletRequest request, HttpServletResponse response) {
		Class<IDENTIFIABLE> clazz = identifiableClass(request, response);
		report.setTitle(languageBusiness.findText("report.datatable.title", new Object[]{UIManager.getInstance().textOfClass(clazz)}));
		report.setFileName(report.getTitle());
		report.setColumns(reportBusiness.findColumns(clazz));
		Collection<IDENTIFIABLE> collection = datas(request,response,clazz);
		if(collection!=null)
			for(AbstractIdentifiable identifiable : collection)
				report.getDataSource().add((IDENTIFIABLE) identifiable); 
		beforeBuild(request,response);
		reportBusiness.buildTable(report,print(request, response));
	}
	
	@SuppressWarnings("unchecked")
	protected Collection<IDENTIFIABLE> datas(HttpServletRequest request, HttpServletResponse response,Class<IDENTIFIABLE> clazz){
		return (Collection<IDENTIFIABLE>) genericBusiness.use(clazz).find().all();
	}
	
	protected void beforeBuild(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	protected abstract Class<IDENTIFIABLE> identifiableClass(HttpServletRequest request, HttpServletResponse response);

}
