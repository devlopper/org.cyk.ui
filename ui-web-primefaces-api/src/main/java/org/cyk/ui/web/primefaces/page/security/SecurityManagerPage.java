package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

@Named @RequestScoped @Getter @Setter
public class SecurityManagerPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getSecurityMenu();
		//contextualMenu.setRenderType(RenderType.PANEL);
	}
	
}
