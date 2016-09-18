package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
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
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ReportFileGeneratePage extends AbstractJoinGlobalIdentifierEditPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void create() {
		@SuppressWarnings("unchecked")
		Class<AbstractIdentifiable> clazz = (Class<AbstractIdentifiable>) joinedIdentifiable.getClass();
		TypedBusiness<AbstractIdentifiable> business = inject(BusinessInterfaceLocator.class).injectTyped(clazz);
		CreateReportFileArguments<AbstractIdentifiable> arguments = new CreateReportFileArguments<AbstractIdentifiable>(((Form)form.getData()).reportTemplate.getCode(),joinedIdentifiable);
		business.createReportFile(joinedIdentifiable, arguments);	
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		return Form.class;
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm<FileIdentifiableGlobalIdentifier> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice(load=true) @InputOneChoice @InputOneCombo @NotNull protected ReportTemplate reportTemplate;
		
		@Override
		public void read() {
			super.read();
			reportTemplate = inject(ReportTemplateBusiness.class).find(WebManager.getInstance().getRequestParameter(UniformResourceLocatorParameter.REPORT_IDENTIFIER));
		}
				
		public static final String FIELD_REPORT_TEMPLATE = "reportTemplate";
		
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(FileIdentifiableGlobalIdentifier.class);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
}
