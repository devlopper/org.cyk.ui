package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.TypedBusiness.CreateReportFileArguments;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.globalidentification.AbstractJoinGlobalIdentifierEditPage;

@Named @ViewScoped @Getter @Setter
public class FileComputeContentPage extends AbstractJoinGlobalIdentifierEditPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private ReportTemplate reportTemplate;
	private Exporter exporter = new Exporter();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		reportTemplate = inject(ReportTemplateBusiness.class).find(WebManager.getInstance()
				.getRequestParameterAsLong(UniformResourceLocatorParameter.REPORT_IDENTIFIER));
		Object[] parameters = webManager.getRequestParameterMapAsArray();
		for(int i = 0 ; i < parameters.length - 1 ; i++)
			if(UniformResourceLocatorParameter.IDENTIFIABLE.equals(parameters[i])){
				parameters[i+1] = identifiable.getFile().getIdentifier();
				break;
			}
				
		exporter.setFileUrl(navigationManager.url(navigationManager.getOutcomeFileServlet(), parameters,Boolean.FALSE,Boolean.FALSE));
		exporter.setType(inject(FileBusiness.class).findMime(requestParameter(UniformResourceLocatorParameter.FILE_EXTENSION)));
	}
	
	@Override
	protected void update() {
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> clazz = (Class<AbstractIdentifiable>) joinedIdentifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = inject(BusinessInterfaceLocator.class).injectTyped(clazz);
		CreateReportFileArguments<AbstractIdentifiable> arguments = new CreateReportFileArguments<AbstractIdentifiable>(reportTemplate,joinedIdentifiable,
				identifiable.getFile());
		business.createReportFile(joinedIdentifiable, arguments);
	}
	
}
