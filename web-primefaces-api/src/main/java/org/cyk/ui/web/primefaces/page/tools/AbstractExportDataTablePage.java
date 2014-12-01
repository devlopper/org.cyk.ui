package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;

public abstract class AbstractExportDataTablePage extends AbstractBusinessEntityFormManyPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Getter protected String fileUrl;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		fileUrl = WebNavigationManager.getInstance().exportDataTableFileUrl(businessEntityInfos.getClazz(),fileExtension(),print());
	}
	
	protected abstract String fileExtension();
	
	protected Boolean print(){
		return Boolean.FALSE;
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return Boolean.FALSE;
	}
}
