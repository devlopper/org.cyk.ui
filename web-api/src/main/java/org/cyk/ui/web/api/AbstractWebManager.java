package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.security.RoleManager;

public abstract class AbstractWebManager<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode> extends AbstractApplicationUIManager<TREE_NODE,TREE_NODE_MODEL> implements Serializable {

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
	
	public static interface AbstractWebManagerListener<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode> extends AbstractApplicationUIManagerListener<TREE_NODE,TREE_NODE_MODEL> {
		
		/**/
		
		public static class Adapter<TREE_NODE,TREE_NODE_MODEL extends WebHierarchyNode> extends AbstractApplicationUIManagerListener.Adapter<TREE_NODE,TREE_NODE_MODEL> implements AbstractWebManagerListener<TREE_NODE,TREE_NODE_MODEL>{
			private static final long serialVersionUID = 3034803382486669232L;

		}
	}
	
}
