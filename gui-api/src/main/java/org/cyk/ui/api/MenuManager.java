package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.annotation.ModelBean.CrudStrategy;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class MenuManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -59229034680588410L;

	public enum Type {APPLICATION,CONTEXTUAL}
	
	private @Inject LanguageBusiness languageBusiness;
	@Inject protected BusinessManager businessManager;
	
	public UIMenu build(Type type){
		UIMenu menu = new DefaultMenu();
		switch(type){
		case APPLICATION:application(menu);break;
		case CONTEXTUAL: break;
		}
		return menu;
	}
	
	/**/
	
	private void application(UIMenu aMenu){
		aMenu.getCommandables().add(commandable("command.file", "ui-icon-file"));
		UICommandable commandable,p;
		aMenu.getCommandables().add(commandable = commandable("command.administration", "ui-icon-gear"));
		for(BusinessEntityInfos businessEntityInfos : businessManager.findEntitiesInfos(CrudStrategy.ENUMERATION)){
			commandable.getChildren().add( p = commandable(businessEntityInfos.getUiLabelId(), null));
			p.setBusinessEntityInfos(businessEntityInfos);
			p.setViewType(ViewType.DYNAMIC_TABLE);
		}
			//aMenu.getCommandables().add(commandable(aClass.getSimpleName(), null));
		aMenu.getCommandables().add(commandable("command.help", "ui-icon-help"));
		
		aMenu.getCommandables().add(commandable = commandable("command.management", "ui-icon-help"));
		commandable.getChildren().add( p = commandable("command.management.deployment", null));
		p.setViewType(ViewType.MANAGEMENT_DEPLOYMENT);
	}
	
	/**/
	
	private UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,String icon){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommandRequestType(aCommandRequestType);
		commandable.setLabel(languageBusiness.findText(labelId));
		commandable.setIcon(icon);
		return commandable;
	}
	
	private UICommandable commandable(String labelId,String icon){
		return commandable(CommandRequestType.UI_VIEW, labelId, icon);
	}
	
}
