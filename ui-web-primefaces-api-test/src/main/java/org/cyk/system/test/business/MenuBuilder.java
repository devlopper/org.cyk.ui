package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.userinterface.command.Menu;

public class MenuBuilder extends org.cyk.ui.web.primefaces.resources.MenuBuilder implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Menu __execute__() {
		Menu menu = super.__execute__();
		
		if(Menu.Type.MAIN.equals(menu.getType())){
			menu.addNode("person.list")._setPropertyUrl(Constant.Action.LIST, Person.class);
		}
		
		return menu;
	}
	
}