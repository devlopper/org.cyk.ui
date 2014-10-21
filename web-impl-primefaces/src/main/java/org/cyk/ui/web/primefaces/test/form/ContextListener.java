package org.cyk.ui.web.primefaces.test.form;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.cyk.system.root.model.party.Person;
import org.cyk.ui.api.MenuManager.Type;
import org.cyk.ui.api.CrudConfig;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.ui.web.primefaces.AbstractContextListener;

@WebListener
public class ContextListener extends AbstractContextListener {

	private static final long serialVersionUID = -3211898049670089807L;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		CrudConfig config = new CrudConfig(Person.class, PersonFormModel.class);
		uiManager.registerCrudConfig(config);
	}
	
	@Override
	public void menu(UserSession userSession, UIMenu menu, Type type) {
		
	}

}
