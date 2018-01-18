package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.ui.api.resources.window.IdentifiableConsultWindow;
import org.cyk.ui.api.resources.window.IdentifiablesManageWindow;

public class MenuBuilder extends org.cyk.ui.web.api.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Boolean isIdentifiablesManageWindow() {
		return componentParent instanceof IdentifiablesManageWindow;
	}
	
	@Override
	protected Boolean isIdentifiableConsultWindow() {
		return componentParent instanceof IdentifiableConsultWindow;
	}
	
	
}