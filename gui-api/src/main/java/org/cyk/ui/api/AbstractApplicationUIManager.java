package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.party.person.AbstractActorBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.command.menu.MenuManager;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.config.IdentifiableConfiguration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractTree;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractApplicationUIManager<TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode> extends AbstractBean implements Serializable {

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
	
	public abstract SystemMenu systemMenu(AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession);
	
	protected BusinessEntityInfos businessEntityInfos(Class<? extends AbstractIdentifiable> aClass){
		return uiManager.businessEntityInfos(aClass);
	}
	
	protected IdentifiableConfiguration identifiableConfiguration(Class<? extends AbstractIdentifiable> aClass){
		IdentifiableConfiguration identifiableConfiguration = uiManager.findConfiguration(aClass);
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
	
	protected <TYPE> AbstractTree<TREE_NODE,TREE_NODE_MODEL> getNavigator(Class<TREE_NODE> nodeClass,Class<TREE_NODE_MODEL> nodeModelClass,Class<TYPE> dataClass,AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession){
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
	
	public void initialiseNavigatorTree(AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession){
		
	}
	
	protected AbstractTree<TREE_NODE,TREE_NODE_MODEL> createNavigatorTree(AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession){
		return null;
	}
	protected <TYPE> Collection<TYPE> getNavigatorTreeNodeDatas(Class<TYPE> dataClass,AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession){
		return null;
	}
	
	protected Boolean isConnectedUserInstanceOfActor(AbstractUserSession<TREE_NODE,TREE_NODE_MODEL> userSession,AbstractActorBusiness<?,?> actorBusiness){
		return actorBusiness.findByPerson((Person) userSession.getUser())!=null;
	}
	
	/**/
	
	public static interface Listener {
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable {
			private static final long serialVersionUID = 3034803382486669232L;
			
			/**/
			
		}
		
	}
}
