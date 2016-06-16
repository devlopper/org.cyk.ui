package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.security.RoleManager;

public abstract class AbstractWebManager<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode,USER_SESSION extends AbstractWebUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractApplicationUIManager<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	@Inject protected transient WebNavigationManager webNavigationManager;
	@Inject protected transient WebManager webManager;
	@Inject protected transient RoleManager roleManager;
	@Inject protected transient JavaScriptHelper javaScriptHelper;
	
	@Override
	protected void businessClassConfig(Class<? extends AbstractIdentifiable> aClass,Class<? extends AbstractFormModel<? extends AbstractIdentifiable>> formModelClass,
			String uiEditViewId) {
		super.businessClassConfig(aClass, formModelClass, uiEditViewId==null?webNavigationManager.getOutcomeDynamicCrudOne():uiEditViewId);
	}
	
	public String getLibraryName(){
		return null;
	}
	
	/**/
	
	public static interface AbstractWebManagerListener<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode,USER_SESSION extends AbstractWebUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> {
		
		/**/
		
		public static class Adapter<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode,USER_SESSION extends AbstractWebUserSession<TREE_NODE,TREE_NODE_MODEL>> extends AbstractApplicationUIManagerListener.Adapter<TREE_NODE,TREE_NODE_MODEL,USER_SESSION> implements AbstractWebManagerListener<TREE_NODE,TREE_NODE_MODEL,USER_SESSION>{
			private static final long serialVersionUID = 3034803382486669232L;

		}
	}
	
}
