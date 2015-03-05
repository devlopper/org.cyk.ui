package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.web.api.WebNavigationManager;

public abstract class AbstractExportDataTablePage extends AbstractReportPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected String url(){
		return WebNavigationManager.getInstance().exportDataTableFileUrl(businessEntityInfos.getClazz(),fileExtension(),print());
	}
	
	
}
