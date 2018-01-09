package org.cyk.ui.web.primefaces.resources.page;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.common.userinterface.container.window.Window;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class HomePage extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void initialisation() {
		super.initialisation();
		//getPropertyStringValueIdentifier("window", "ho", "title");
		_setPropertyTitleFromStringIdentifier("userinterface.page.home.title");
	}
	
	@Override
	protected String computePropertyTitle() {
		// TODO Auto-generated method stub
		return super.computePropertyTitle();
	}
}
