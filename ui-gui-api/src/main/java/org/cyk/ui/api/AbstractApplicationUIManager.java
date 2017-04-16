package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractApplicationUIManager<TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	@Getter @Setter private Integer orderIndex;
	
	@Inject protected UIProvider uiProvider;
	@Inject protected UIManager uiManager;
	@Inject protected MenuManager menuManager;
	@Inject protected LanguageBusiness languageBusiness;
	@Inject protected ApplicationBusiness applicationBusiness;
	@Inject protected BusinessManager businessManager;
	
	@Getter @Setter protected String identifier;
	@Getter @Setter protected Boolean autoAddToSystemMenu = Boolean.TRUE;
	
	@Getter @Setter protected SystemMenu systemMenu;
	
	@Getter @Setter protected Collection<AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>> listeners = new ArrayList<>();
	
	public abstract SystemMenu systemMenu(USER_SESSION userSession);
	
	protected BusinessEntityInfos businessEntityInfos(Class<? extends AbstractIdentifiable> aClass){
		return uiManager.businessEntityInfos(aClass);
	}
	
	protected IdentifiableConfiguration identifiableConfiguration(Class<? extends AbstractIdentifiable> aClass){
		IdentifiableConfiguration identifiableConfiguration = uiManager.findConfiguration(aClass,Boolean.FALSE);
		if(identifiableConfiguration==null){
			identifiableConfiguration = new IdentifiableConfiguration();
			identifiableConfiguration.setClazz(aClass);
			uiManager.registerConfiguration(identifiableConfiguration);
		}
		return identifiableConfiguration;
	}
	
	protected void registerFormModel(String id,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> aClass){
		UIManager.FORM_MODEL_MAP.put(id,aClass);
	}
	
	protected void registerFormModel(Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> aClass){
		registerFormModel(aClass.getSimpleName(),aClass);//TODO ensure that there is no duplicate
	}
	
	/**/
	
	protected void businessClassConfig(Class<? extends AbstractIdentifiable> aClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass,
			String uiEditViewId){
		//identifiableConfiguration(aClass).setFormModelClass(formModelClass);
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		if(StringUtils.isNotBlank(uiEditViewId))
			businessEntityInfos.getUserInterface().setEditViewId(uiEditViewId);
	}
	
	protected void businessClassConfig(Class<? extends AbstractIdentifiable> aClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass){
		businessClassConfig(aClass,formModelClass,null);
	}
	
	@Deprecated
	protected <TYPE> AbstractTree<TREE_NODE,TREE_NODE_MODEL> getNavigator(Class<TREE_NODE> nodeClass,Class<TREE_NODE_MODEL> nodeModelClass,Class<TYPE> dataClass,USER_SESSION userSession){
		AbstractTree<TREE_NODE,TREE_NODE_MODEL> navigator = createNavigatorTree(userSession);
		Collection<TYPE> datas = getNavigatorTreeNodeDatas(dataClass,userSession);
		/*if(datas!=null){
			for(Object data : datas){
				NODE_MODEL nodeModel = createHierarchyNodeClassInstance(nodeClass, nodeModelClass, userSession, data);
				nodeModel.setExpanded(Boolean.FALSE);
				nodes.add(nodeModel);
			}
		}*/
		navigator.build(dataClass, datas, null);
		return navigator;
	}
	@Deprecated
	public void initialiseNavigatorTree(USER_SESSION userSession){
		
	}
	@Deprecated
	protected AbstractTree<TREE_NODE,TREE_NODE_MODEL> createNavigatorTree(USER_SESSION userSession){
		return null;
	}
	@Deprecated
	protected <TYPE> Collection<TYPE> getNavigatorTreeNodeDatas(Class<TYPE> dataClass,USER_SESSION userSession){
		return null;
	}
	
	protected Boolean isConnectedUserInstanceOfActor(USER_SESSION userSession,AbstractActorBusiness<?,?> actorBusiness){
		return actorBusiness.findByPerson((Person) userSession.getUser())!=null;
	}
	
	/**/
	
	protected void addBusinessMenu(USER_SESSION userSession,SystemMenu systemMenu,UICommandable commandable){
		addCommandable(userSession,systemMenu.getBusinesses(),commandable); 
	}
	
	protected void onBusinessMenuPopulateEnded(final USER_SESSION userSession,final UICommandable module){
		listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public void execute(AbstractApplicationUIManagerListener<TREE_NODE, TREE_NODE_MODEL,USER_SESSION> listener) {
				listener.onBusinessMenuPopulateEnded(userSession, module);
			}
		});
	}
	
	protected void addChild(USER_SESSION userSession,UICommandable parent,UICommandable child){
		addCommandable(userSession,parent.getChildren(),child); 
	}
	
	private void addCommandable(USER_SESSION userSession,Collection<UICommandable> commandables,UICommandable commandable){
		if(commandable==null)
			;
		else
			if(Boolean.TRUE.equals(isCommandableVisible(userSession,commandable)))
				commandables.add(commandable); 
	}
	
	protected Boolean isCommandableVisible(final USER_SESSION userSession,final UICommandable commandable){
		Collection<String> invisibleCommandableIdentifiers = getInvisibleCommandableIdentifiers(userSession);
		Boolean visible = Boolean.TRUE; 
		if(invisibleCommandableIdentifiers!=null && StringUtils.isNotBlank(commandable.getIdentifier()) && invisibleCommandableIdentifiers.contains(commandable.getIdentifier()))
			visible = Boolean.FALSE;
		if(visible)
			visible = listenerUtils.getBoolean(listeners, new ListenerUtils.BooleanMethod<AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public Boolean execute(AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.isCommandableVisible(userSession,commandable);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
		return visible;
	}
	
	protected Collection<String> getInvisibleCommandableIdentifiers(final USER_SESSION userSession){
		return listenerUtils.getCollection(listeners, new ListenerUtils.CollectionMethod<AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>,String>() {
			@Override
			public Collection<String> execute(AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.getInvisibleCommandableIdentifiers(userSession);
			}
		});
	}
	
	public SystemMenu getSystemMenu(final USER_SESSION userSession){
		return listenerUtils.getValue(SystemMenu.class,listeners, new ListenerUtils.ResultMethod<AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>,SystemMenu>() {
			@Override
			public SystemMenu execute(AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.getSystemMenu(userSession);
			}

			@Override
			public SystemMenu getNullValue() {
				return null;
			}
		});
	}
	
	/**/
	
	public static interface AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> {
		
		Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession);
		Boolean isCommandableVisible(USER_SESSION userSession,UICommandable commandable);
		void onBusinessMenuPopulateEnded(USER_SESSION userSession,UICommandable module);
		SystemMenu getSystemMenu(USER_SESSION userSession);
		
		/**/
		
		public static class Adapter<TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends BeanAdapter implements AbstractApplicationUIManagerListener<TREE_NODE, TREE_NODE_MODEL,USER_SESSION>{
			private static final long serialVersionUID = 3034803382486669232L;

			public Adapter() {
				configureEventModule();
				configureFileModule();
				configureGeographyModule();
				configureGlobalIdentificationModule();
				configureInformationModule();
				configureLanguageModule();
				configureMathematicsModule();
				configureMessageModule();
				configurePartyModule();
				configureSecurityModule();
				configureTimeModule();
				configureTreeModule();
				configureUserInterfaceModule();
				configureNetworkModule();
			}
			
			@Override
			public Boolean isCommandableVisible(USER_SESSION userSession,UICommandable commandable) {
				return null;
			}

			@Override
			public Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession) {
				return null;
			}

			@Override
			public void onBusinessMenuPopulateEnded(USER_SESSION userSession,UICommandable module) {}
			
			@Override
			public SystemMenu getSystemMenu(USER_SESSION userSession) {
				return null;
			}
			
			protected void configureEventModule(){}
			protected void configureFileModule(){}
			protected void configureGeographyModule(){}
			protected void configureGlobalIdentificationModule(){}
			protected void configureInformationModule(){}
			protected void configureLanguageModule(){}
			protected void configureMathematicsModule(){}
			protected void configureMessageModule(){}
			protected void configureNetworkModule(){}
			protected void configurePartyModule(){}
			protected void configureTreeModule(){}
			protected void configureSecurityModule(){}
			protected void configureTimeModule(){}
			protected void configureUserInterfaceModule(){}
			
			protected String getTabIdentifier(Class<?> aClass){
				return IdentifierProvider.Adapter.getTabOf(aClass);
			}
		}
	}
}
