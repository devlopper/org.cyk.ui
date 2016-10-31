package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.file.report.ReportFileBusiness;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.report.ReportFile;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Named @ViewScoped @Getter @Setter
public class FileIdentifiableGlobalIdentifierConsultPage extends AbstractConsultPage<FileIdentifiableGlobalIdentifier> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void processIdentifiableContextualCommandable(UICommandable commandable) {
		super.processIdentifiableContextualCommandable(commandable);
		Collection<ReportFile> reportFiles = inject(ReportFileBusiness.class).findByFile(identifiable.getFile());
		if(!reportFiles.isEmpty()){
			for(ReportFile reportFile : reportFiles)
				commandable.addChild(AbstractCommandable.Builder.createCrud(Crud.UPDATE, identifiable, "command.file.content.compute"
						, null, navigationManager.getOutcomeFileComputeContent())
						.addParameter(UniformResourceLocatorParameter.REPORT_IDENTIFIER, reportFile.getReportTemplate().getIdentifier())
						.addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER))
						.addParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, requestParameter(UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS))
						)
						;
		}
	}
	
}
