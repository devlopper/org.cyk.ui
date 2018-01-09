package org.cyk.ui.web.primefaces.resources.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.userinterface.container.window.Window;

@Named @ViewScoped @Getter @Setter
public class HomePage extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getPropertyTitleIdentifier() {
		return "userinterface.window.home.title";
	}
	
	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setLayoutCardinalPointCenterSouthRendered(Boolean.FALSE);
	}
	
}
