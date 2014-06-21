package org.cyk.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.BusinessManager;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.ui.api.command.DefaultCommandable;
import org.cyk.ui.api.command.DefaultMenu;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
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
		case CONTEXTUAL:contextual(menu); break;
		}
		return menu;
	}
	
	/**/
	
	private void application(UIMenu aMenu){
		//aMenu.getCommandables().add(commandable("command.file", "ui-icon-file"));
		UICommandable commandable,p;
		
		aMenu.getCommandables().add(commandable = commandable("command.tools", IconType.THING_TOOLS));
		commandable.getChildren().add( p = commandable("command.tools.calendar", null));
		p.setViewType(ViewType.TOOLS_CALENDAR);
		
		aMenu.getCommandables().add(commandable = commandable("command.administration", IconType.ACTION_ADMINISTRATE));
		List<BusinessEntityInfos> list = new ArrayList<>(businessManager.findEntitiesInfos(CrudStrategy.ENUMERATION));
		Collections.sort(list, new BusinessEntityInfosMenuItemComparator());
		for(BusinessEntityInfos businessEntityInfos : list){
			commandable.getChildren().add( p = commandable(businessEntityInfos.getUiLabelId(), null));
			p.setBusinessEntityInfos(businessEntityInfos);
			if(DataTreeType.class.isAssignableFrom(businessEntityInfos.getClazz())){
				p.setViewType(ViewType.DYNAMIC_FORM_TABLE);	
			}else{
				p.setViewType(ViewType.DYNAMIC_FORM_TABLE);
			}
		}
			//aMenu.getCommandables().add(commandable(aClass.getSimpleName(), null));
		aMenu.getCommandables().add(commandable("command.help", IconType.ACTION_HELP));
		
		aMenu.getCommandables().add(commandable = commandable("command.management", IconType.ACTION_HELP));
		commandable.getChildren().add( p = commandable("command.management.deployment", null));
		p.setViewType(ViewType.MANAGEMENT_DEPLOYMENT);
	}
	
	private void contextual(UIMenu aMenu){
		
	}
	
	/**/
	
	private UICommandable commandable(CommandRequestType aCommandRequestType, String labelId,IconType iconType){
		UICommandable commandable = new DefaultCommandable();
		commandable.setCommandRequestType(aCommandRequestType);
		commandable.setLabel(languageBusiness.findText(labelId));
		commandable.setIconType(iconType);
		return commandable;
	}
	
	private UICommandable commandable(String labelId,IconType iconType){
		return commandable(CommandRequestType.UI_VIEW, labelId, iconType);
	}
	
	private class BusinessEntityInfosMenuItemComparator implements Comparator<BusinessEntityInfos>{

		@Override
		public int compare(BusinessEntityInfos o1, BusinessEntityInfos o2) {
			return o1.getClazz().getName().compareTo(o2.getClazz().getName());
		}
		
	}
	
}
