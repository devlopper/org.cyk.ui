package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.DynamicView;
import org.cyk.ui.api.command.UIMenu;
import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class MenuManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -59229034680588410L;

	public enum Type {APPLICATION,CONTEXTUAL}
	
	private @Inject UIManager uiManager;
	private @Inject LanguageBusiness languageBusiness;
	
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
		aMenu.getCommandables().add(commandable("file", "ui-icon-file"));
		UICommandable commandable,p;
		aMenu.getCommandables().add(commandable = commandable("administration", "ui-icon-gear"));
		for(Class<?> aClass : uiManager.getParametersClasses()){
			commandable.getChildren().add( p = commandable(aClass.getSimpleName(), null));
			p.setDynamicClass(aClass);
			p.setDynamicView(DynamicView.EDITOR);
		}
			//aMenu.getCommandables().add(commandable(aClass.getSimpleName(), null));
		aMenu.getCommandables().add(commandable("help", "ui-icon-help"));
	}
	
	/**/
	
	private UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,String icon){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommandRequestType(aCommandRequestType);
		commandable.setLabel(languageBusiness.findText("command."+labelId));
		commandable.setIcon(icon);
		return commandable;
	}
	
	private UICommandable commandable(String labelId,String icon){
		return commandable(CommandRequestType.UI_VIEW, labelId, icon);
	}
	
}
