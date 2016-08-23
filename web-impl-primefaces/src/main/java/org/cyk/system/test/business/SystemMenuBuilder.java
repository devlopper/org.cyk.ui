package org.cyk.system.test.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.test.model.actor.Actor;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.UserSession;

public class SystemMenuBuilder extends org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.SystemMenuBuilder implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	private static SystemMenuBuilder INSTANCE;
		
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
