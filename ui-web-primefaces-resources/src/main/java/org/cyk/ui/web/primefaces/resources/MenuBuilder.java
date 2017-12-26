package org.cyk.ui.web.primefaces.resources;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.command.Menu;

public class MenuBuilder extends org.cyk.ui.web.api.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		if(Menu.Type.MAIN.equals(menu.getType())){

		}else if(Menu.Type.CONTEXT.equals(menu.getType())){
			if(Menu.RenderType.PANEL.equals(menu.getRenderType()))
				menu.getPropertiesMap().setStyle("height : 445px;overflow : scroll;");
		}
		return menu;
	}
	
}