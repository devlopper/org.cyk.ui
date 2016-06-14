package org.cyk.ui.api.command.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.model.AbstractHierarchyNode;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

@Getter @Setter
public abstract class AbstractSystemMenuBuilder<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 6995162040038809581L;

	protected Collection<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>> listeners = new ArrayList<>();
	
	/**/
	
	public abstract SystemMenu build(USER_SESSION userSession);
	
	/**/
	
	protected void addBusinessMenu(USER_SESSION userSession,SystemMenu systemMenu,COMMANDABLE commandable){
		addCommandable(userSession,systemMenu.getBusinesses(),commandable); 
	}
	
	protected void onBusinessMenuPopulateEnded(final USER_SESSION userSession,final COMMANDABLE module){
		listenerUtils.execute(listeners, new ListenerUtils.VoidMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public void execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE, TREE_NODE_MODEL,USER_SESSION> listener) {
				listener.onBusinessMenuPopulateEnded(userSession, module);
			}
		});
	}
	
	protected void addChild(USER_SESSION userSession,COMMANDABLE parent,COMMANDABLE child){
		addCommandable(userSession,parent.getChildren(),child); 
	}
	
	private void addCommandable(USER_SESSION userSession,Collection<UICommandable> commandables,COMMANDABLE commandable){
		if(commandable==null)
			;
		else
			if(Boolean.TRUE.equals(isCommandableVisible(userSession,commandable)))
				commandables.add(commandable); 
	}
	
	protected Boolean isCommandableVisible(final USER_SESSION userSession,final COMMANDABLE commandable){
		Collection<String> invisibleCommandableIdentifiers = getInvisibleCommandableIdentifiers(userSession);
		Boolean visible = Boolean.TRUE; 
		if(invisibleCommandableIdentifiers!=null && StringUtils.isNotBlank(commandable.getIdentifier()) && invisibleCommandableIdentifiers.contains(commandable.getIdentifier()))
			visible = Boolean.FALSE;
		if(visible)
			visible = listenerUtils.getBoolean(listeners, new ListenerUtils.BooleanMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>>() {
			@Override
			public Boolean execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
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
		return listenerUtils.getCollection(listeners, new ListenerUtils.CollectionMethod<AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION>,String>() {
			@Override
			public Collection<String> execute(AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE,TREE_NODE_MODEL,USER_SESSION> listener) {
				return listener.getInvisibleCommandableIdentifiers(userSession);
			}
		});
	}
	
	/**/
	
	protected AbstractCommandable.Builder<COMMANDABLE> getCommandableBuilder(){
		return AbstractCommandable.Builder.instanciateOne();
	}
	
	protected COMMANDABLE createModuleCommandable(String labelId,Icon icon){
		@SuppressWarnings("unchecked")
		COMMANDABLE module = (COMMANDABLE) AbstractCommandable.Builder.create(labelId, icon);
		
		return module;
	}
	protected COMMANDABLE createModuleCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(businessClass);
		return createModuleCommandable(businessEntityInfos.getUserInterface().getLabelId(), icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createListCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createList(businessClass, icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createCreateCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createCreate(UIManager.getInstance().businessEntityInfos(businessClass), icon);
	}
	@SuppressWarnings("unchecked")
	protected COMMANDABLE createCreateManyCommandable(Class<? extends AbstractIdentifiable> businessClass,Icon icon){
		return (COMMANDABLE) AbstractCommandable.Builder.createCreateMany(UIManager.getInstance().businessEntityInfos(businessClass), icon);
	}
	
	/**/
	
	public static interface AbstractSystemMenuBuilderListener<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> {
		
		Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession);
		Boolean isCommandableVisible(USER_SESSION userSession,COMMANDABLE commandable);
		void onBusinessMenuPopulateEnded(USER_SESSION userSession,COMMANDABLE module);
		
		/**/
		
		public static class Adapter<COMMANDABLE extends AbstractCommandable,TREE_NODE,TREE_NODE_MODEL extends AbstractHierarchyNode,USER_SESSION extends AbstractUserSession<TREE_NODE,TREE_NODE_MODEL>> extends BeanAdapter implements AbstractSystemMenuBuilderListener<COMMANDABLE,TREE_NODE, TREE_NODE_MODEL,USER_SESSION>{
			private static final long serialVersionUID = 3034803382486669232L;

			@Override
			public Boolean isCommandableVisible(USER_SESSION userSession,COMMANDABLE commandable) {
				return null;
			}

			@Override
			public Set<String> getInvisibleCommandableIdentifiers(USER_SESSION userSession) {
				return null;
			}

			@Override
			public void onBusinessMenuPopulateEnded(USER_SESSION userSession,COMMANDABLE module) {}
		}
	}
}
