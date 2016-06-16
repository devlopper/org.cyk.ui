package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.command.AbstractCommandable.Builder;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.test.model.Actor;
import org.cyk.ui.web.api.WebHierarchyNode;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.Tree;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

@Singleton @Deployment(initialisationType=InitialisationType.EAGER,order=RootWebManager.DEPLOYMENT_ORDER+1) @Getter
public class MyWebManager extends AbstractPrimefacesManager implements Serializable {

	private static final long serialVersionUID = -769097240180562952L;

	private static MyWebManager INSTANCE;
	
	private String editManyPersons = "editmanypers";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		identifier = "test";
		
		//businessClassConfig(Actor.class,ActorFormModel.class);
		
	}
	
	@Override
	public SystemMenu systemMenu(UserSession userSession) {
		SystemMenu systemMenu = new SystemMenu();
		systemMenu.setName("MyApp");
		
		//UICommandable commandable;
		//systemMenu.getBusinesses().add(menuManager.crudMany(Actor.class, IconType.PERSON));
		systemMenu.getBusinesses().add(Builder.createList(Person.class, Icon.PERSON));
		systemMenu.getBusinesses().add(Builder.createList(Actor.class, null));
		systemMenu.getBusinesses().add(Builder.createSelectOne(Actor.class,null, null));
		systemMenu.getBusinesses().add(Builder.createSelectMany(Actor.class,"myactionid", Icon.PERSON));
		systemMenu.getBusinesses().add(Builder.createSelectMany(Person.class,editManyPersons, Icon.ACTION_EDIT));
		//systemMenu.getBusinesses().add(Builder.createList(MovementCollection.class, null));
		//systemMenu.getBusinesses().add(Builder.createList(Movement.class, null));
		//systemMenu.getBusinesses().add(Builder.createList(Sex.class, null));
		//debug(Builder.createList(Sex.class, null));
		//menu.getCommandables().add(commandable = MenuManager.commandable("command.search", IconType.ACTION_SEARCH));
		//commandable.setViewId("personsearch");
		
		userSession.setNavigatorTree(new Tree());
		WebHierarchyNode root = new WebHierarchyNode(null);
		
		Collection<WebHierarchyNode> nodes = new ArrayList<>();
		nodes.add(new WebHierarchyNode(null, "A", Boolean.FALSE));
		nodes.add(new WebHierarchyNode(null, "B", Boolean.FALSE));
		nodes.add(new WebHierarchyNode(null, "C", Boolean.FALSE));
		userSession.getNavigatorTree().build(WebHierarchyNode.class, nodes, null);
		
		return systemMenu;
	}
		
	public static MyWebManager getInstance() {
		return INSTANCE;
	}

}
