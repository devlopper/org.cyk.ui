package org.cyk.ui.web.primefaces.resources.page.controlpanel;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.Properties;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class IdentifiablesManagePage extends org.cyk.ui.web.api.resources.page.IdentifiablesManagePage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getPropertyTitleIdentifier() {
		return "userinterface.menu.main.controlpanel.identifiables.manage.title";
	}
	
	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setLayoutCardinalPointCenterSouthRendered(Boolean.FALSE);
	}
	
}
