package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.ui.api.command.menu.UIMenu.RenderType;

import lombok.Getter;
import lombok.Setter;

@Named
@RequestScoped
@Getter
@Setter
public class ReferenceEntityPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() {
		super.initialisation();
		contextualMenu = userSession.getReferenceEntityMenu();
		contextualMenu.setRenderType(RenderType.PANEL);
	}
	
}
