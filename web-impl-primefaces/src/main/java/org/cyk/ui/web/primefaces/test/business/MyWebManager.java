package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.api.AbstractWebManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER)
public class MyWebManager extends AbstractWebManager implements Serializable {

	private static final long serialVersionUID = -769097240180562952L;

	private static MyWebManager INSTANCE;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Override
	public SystemMenu systemMenu(UserSession userSession) {
		SystemMenu systemMenu = new SystemMenu();
		systemMenu.setName("MyApp");
		
		//UICommandable commandable;
		systemMenu.getBusinesses().add(menuManager.crudMany(Actor.class, IconType.PERSON));
		//menu.getCommandables().add(commandable = MenuManager.commandable("command.search", IconType.ACTION_SEARCH));
		//commandable.setViewId("personsearch");
		
		systemMenu.getBusinesses().add(UIProvider.getInstance().createCommandable("command.add", IconType.ACTION_ADD));
		systemMenu.getBusinesses().add(UIProvider.getInstance().createCommandable("command.edit", IconType.ACTION_EDIT));
		
		systemMenu.getReports().add(UIProvider.getInstance().createCommandable("command.edit", IconType.ACTION_ADD));
		systemMenu.getReports().add(UIProvider.getInstance().createCommandable("command.edit", IconType.ACTION_ADD));
		systemMenu.getReports().add(UIProvider.getInstance().createCommandable("command.edit", IconType.ACTION_ADD));
		systemMenu.getReports().add(UIProvider.getInstance().createCommandable("command.edit", IconType.ACTION_ADD));
		return systemMenu;
	}
	
	public static MyWebManager getInstance() {
		return INSTANCE;
	}

}
