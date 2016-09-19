package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;

@Named @ViewScoped @Getter @Setter
public class FileComputeContentPage extends AbstractJoinGlobalIdentifierEditPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ReportTemplate reportTemplate;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		reportTemplate = inject(ReportTemplateBusiness.class).find(WebManager.getInstance()
				.getRequestParameterAsLong(UniformResourceLocatorParameter.REPORT_IDENTIFIER));
	}
	
	@Override
	protected void update() {
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> clazz = (Class<AbstractIdentifiable>) joinedIdentifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = inject(BusinessInterfaceLocator.class).injectTyped(clazz);
		CreateReportFileArguments<AbstractIdentifiable> arguments = new CreateReportFileArguments<AbstractIdentifiable>(reportTemplate,joinedIdentifiable);
		business.createReportFile(joinedIdentifiable, arguments);
	}
	
}
