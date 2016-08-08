package org.cyk.ui.web.primefaces.test.business;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.system.root.ui.web.primefaces.api.RootWebManager;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;

import lombok.Getter;

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
		/*systemMenu.setName("MyApp");
		UICommandable module;
		
		module = Builder.create(null, null).setLabel("Ensemble");
		module.addChild(Builder.createList(NestedSetNode.class, null));
		module.addChild(Builder.createList(LocalityType.class, null));
		module.addChild(Builder.createList(Locality.class, null));
		module.addChild(Builder.createList(DataTree.class, null));
		module.addChild(Builder.createList(DataTreeType.class, null));
		module.addChild(Builder.createList(DataTreeIdentifiableGlobalIdentifier.class, null));
		systemMenu.getBusinesses().add(module);
		
		module = Builder.create(null, null).setLabel("Enumeration");
		module.addChild(Builder.createList(Sex.class, null));
		module.addChild(Builder.createList(PersonTitle.class, null));
		module.addChild(Builder.createList(JobTitle.class, null));
		systemMenu.getBusinesses().add(module);
		
		module = Builder.create(null, Icon.PERSON).setLabel("Partie");
		module.addChild(Builder.createList(Person.class, Icon.PERSON));
		module.addChild(Builder.createList(Actor.class, null));
		module.addChild(Builder.createSelectOne(Actor.class,null, null));
		module.addChild(Builder.createSelectMany(Actor.class,"myactionid", null));
		module.addChild(Builder.createSelectMany(Person.class,editManyPersons, Icon.ACTION_EDIT));
		systemMenu.getBusinesses().add(module);
		
		module = Builder.create(null, null).setLabel("Commentaire");
		module.addChild(Builder.createList(Comment.class, null));
		systemMenu.getBusinesses().add(module);
		
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
		*/
		return systemMenu;
	}
		
	public static MyWebManager getInstance() {
		return INSTANCE;
	}

}
