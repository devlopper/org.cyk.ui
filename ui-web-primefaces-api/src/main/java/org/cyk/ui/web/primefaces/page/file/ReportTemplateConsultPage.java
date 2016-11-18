package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ReportTemplateConsultPage extends AbstractConsultPage<ReportTemplate> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Exporter templateExporter = new Exporter(),headerImageExporter = new Exporter(),backgroundImageExporter = new Exporter()
			,draftBackgroundImageExporter = new Exporter();
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		templateExporter.setFileUrlFromRequestParameters(identifiable.getTemplate());
		headerImageExporter.setFileUrlFromRequestParameters(identifiable.getHeaderImage());
		backgroundImageExporter.setFileUrlFromRequestParameters(identifiable.getBackgroundImage());
		draftBackgroundImageExporter.setFileUrlFromRequestParameters(identifiable.getDraftBackgroundImage());
	}

}
