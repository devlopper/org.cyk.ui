package org.cyk.ui.api.menu;

import java.io.Serializable;

import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.ui.api.AbstractComponent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractMenu<MODEL> extends AbstractComponent<UserInterfaceMenu,MODEL> implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer selectedIndex;
	
	public AbstractMenu(UserInterfaceMenu userModel) {
		super(userModel);
	}
}
