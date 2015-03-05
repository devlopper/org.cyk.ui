package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityPrimefacesPage;

public abstract class AbstractReportPage<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Getter protected Exporter exporter = new Exporter();
	protected String fileExtension,reportIdentifier;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		fileExtension = requestParameter(uiManager.getFileExtensionParameter());
		reportIdentifier = requestParameter(uiManager.getReportIdentifierParameter());
		exporter.setFileUrl(url());
		exporter.setType("application/"+fileExtension());
	}
	
	protected String fileExtension(){
		return fileExtension;
	}
	
	protected abstract String url();
	
	protected Boolean print(){
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
}
