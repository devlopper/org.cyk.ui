package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.file.File;

@Named @ViewScoped @Getter @Setter
public class FileConsultPage extends AbstractFileConsultPage<File> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected File getFile() {
		return identifiable;
	}
	
	/*
	@Override
	protected void processIdentifiableContextualCommandable(UICommandable commandable) {
		super.processIdentifiableContextualCommandable(commandable);
		Collection<ReportFile> reportFiles = inject(ReportFileBusiness.class).findByFile(identifiable);
		if(!reportFiles.isEmpty()){
			for(ReportFile reportFile : reportFiles)
				commandable.addChild(AbstractCommandable.Builder.createCrud(Crud.UPDATE, identifiable, "command.file.content.compute"
						, null, navigationManager.getOutcomeFileComputeContent()).addParameter(UniformResourceLocatorParameter.REPORT_IDENTIFIER
								, reportFile.getReportTemplate().getIdentifier()));
		}
	}
	*/
	
}
