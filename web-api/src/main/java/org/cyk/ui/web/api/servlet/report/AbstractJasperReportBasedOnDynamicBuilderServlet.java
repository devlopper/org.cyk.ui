package org.cyk.ui.web.api.servlet.report;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.report.AbstractReportConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilder;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderConfiguration;
import org.cyk.system.root.model.file.report.ReportBasedOnDynamicBuilderParameters;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.UserAccount;

public abstract class AbstractJasperReportBasedOnDynamicBuilderServlet<IDENTIFIABLE extends AbstractIdentifiable> extends 
	AbstractJasperReportServlet<IDENTIFIABLE,ReportBasedOnDynamicBuilder<IDENTIFIABLE>> implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Inject private UserAccountBusiness userAccountBusiness;
	
	@Override
	protected ReportBasedOnDynamicBuilder<IDENTIFIABLE> createReport(HttpServletRequest request,AbstractReportConfiguration<IDENTIFIABLE, ReportBasedOnDynamicBuilder<IDENTIFIABLE>> configuration) {
		
		ReportBasedOnDynamicBuilderParameters<IDENTIFIABLE> p = new ReportBasedOnDynamicBuilderParameters<IDENTIFIABLE>();
		p.setIdentifiableClass(identifiableClassParameter(request));
		p.setFileExtension(fileExtensionRequestParameter(request));
		p.setPrint(printRequestParameter(request));
		UserAccount userAccount = userAccountBusiness.findByUsername(request.getRemoteUser());
		p.setCreatedBy(((Person)userAccount.getUser()).getNames());
		p.setExtendedParameterMap(request.getParameterMap());
       
		return ((ReportBasedOnDynamicBuilderConfiguration<IDENTIFIABLE, ReportBasedOnDynamicBuilder<IDENTIFIABLE>>) configuration).build(p);
	}
	
}
