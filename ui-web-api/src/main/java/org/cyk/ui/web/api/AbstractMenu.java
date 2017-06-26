package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.system.root.model.userinterface.UserInterfaceMenu;

public abstract class AbstractMenu<MODEL> extends org.cyk.ui.api.menu.AbstractMenu<MODEL> implements Serializable {

	private static final long serialVersionUID = 1L;

	public AbstractMenu(UserInterfaceMenu persistedModel) {
		super(persistedModel);
	}

}
