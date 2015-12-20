package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.security.RoleManager;

public abstract class AbstractWebManager extends AbstractApplicationUIManager implements Serializable {

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
	
	protected void addBusinessMenu(SystemMenu systemMenu,UICommandable item){
		if(item==null)
			;
		else
			systemMenu.getBusinesses().add(item); 
	}
	
}
