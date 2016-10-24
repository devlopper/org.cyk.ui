package org.cyk.system.test.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.JobTitle;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.AbstractSystemMenuBuilder;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.UserSession;

public class SystemMenuBuilder extends org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.SystemMenuBuilder implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	private static SystemMenuBuilder INSTANCE;
	
	private SystemMenuBuilder() {
		listeners.add(new AbstractSystemMenuBuilder.Listener.Adapter() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Boolean isCommandableVisible(UserSession userSession,Commandable commandable) {
				return super.isCommandableVisible(userSession, commandable);
			}
			
		});
	}
	
	@Override
	public SystemMenu build(UserSession userSession) {
		SystemMenu systemMenu = super.build(userSession);
		UICommandable commandable = createModuleCommandable("PPPP", null);
		systemMenu.getReferenceEntities().add(commandable);
		
		commandable.addChild(createListCommandable(Locality.class, null));
		commandable.addChild(createListCommandable(Country.class, null));
		commandable.addChild(createListCommandable(PersonTitle.class, null));
		commandable.addChild(createListCommandable(JobTitle.class, null));
		commandable.addChild(createListCommandable(JobFunction.class, null));
		return systemMenu;
	}
	
	public Commandable getPersonCommandable(UserSession userSession,Collection<UICommandable> mobileCommandables){
		Commandable module = super.getPersonCommandable(userSession, mobileCommandables);
		module.addChild(createListCommandable(Actor.class,null));
		return module;
	}

	public static SystemMenuBuilder getInstance(){
		if(INSTANCE==null)
			INSTANCE = new SystemMenuBuilder();
		return INSTANCE;
	}
}
