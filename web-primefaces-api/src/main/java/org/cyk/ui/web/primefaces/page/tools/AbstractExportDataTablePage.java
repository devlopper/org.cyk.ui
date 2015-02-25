package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.Exporter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;

public abstract class AbstractExportDataTablePage extends AbstractBusinessEntityFormManyPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Getter protected Exporter exporter = new Exporter();
	protected String fileExtension;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		fileExtension = requestParameter(webManager.getRequestParameterFileExtension());
		exporter.setFileUrl(url());
		exporter.setType("application/"+fileExtension());
	}
	
	protected String fileExtension(){
		return fileExtension;
	}
	
	protected String url(){
		return WebNavigationManager.getInstance().exportDataTableFileUrl(businessEntityInfos.getClazz(),fileExtension(),print());
	}
	
	protected Boolean print(){
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
}
