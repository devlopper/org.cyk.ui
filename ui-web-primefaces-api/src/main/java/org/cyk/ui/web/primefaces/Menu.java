package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuItemBusiness;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.web.api.AbstractMenu;
import org.primefaces.model.menu.MenuModel;

public class Menu extends AbstractMenu<MenuModel> implements Serializable {

	private static final long serialVersionUID = 1L;

	public Menu(UserInterfaceMenu userModel) {
		super(userModel);
	}

	@Override
	protected MenuModel buildTargetModel() {
		UIMenu sampleMenu = new DefaultMenu();
		sampleMenu.setRenderType(UIMenu.RenderType.BAR);
		for(UserInterfaceMenuItem userInterfaceMenuItem : inject(UserInterfaceMenuItemBusiness.class).findByCollection(userModel))
			sampleMenu.addCommandable(inject(CommandableBuilder.class).get(userInterfaceMenuItem));
		return CommandBuilder.getInstance().menuModel(sampleMenu, getClass(), "");
	}

	
}
