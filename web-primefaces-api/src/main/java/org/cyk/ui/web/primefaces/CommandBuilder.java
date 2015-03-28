package org.cyk.ui.web.primefaces;

import java.beans.Introspector;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

public class CommandBuilder implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;
	private static final CommandBuilder INSTANCE = new CommandBuilder();
	private static final String EL_MENU_ITEM_COMMAND_FORMAT = "#"+"{"+ "%s.%s.commandable('%s').command.execute(null)}";
	
	public static CommandBuilder getInstance() {
		return INSTANCE;
	}
	
	public CommandButton commandButton(Commandable aCommandable){
		CommandButton commandButton = new CommandButton();
		commandButton.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
		if(Boolean.TRUE.equals(aCommandable.getShowLabel()))
			commandButton.setValue(aCommandable.getLabel());
		commandButton.setUpdate(aCommandable.getUpdate());
		if(aCommandable.getIconType()!=null)
			commandButton.setIcon(icon(aCommandable.getIconType()));
		if(StringUtils.isEmpty(aCommandable.getTooltip()))
			commandButton.setTitle(aCommandable.getLabel());
		else
			commandButton.setTitle(aCommandable.getTooltip());
		if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
			commandButton.setProcess("@this");		
		
		return commandButton;
	}
	
	public MenuElement menuItem(UICommandable aCommandable,DefaultSubMenu parent,String managedBeanName,@NotNull String...fields){
		if(aCommandable.getChildren().isEmpty()){
			DefaultMenuItem	menuItem = new DefaultMenuItem();
			menuItem.setValue(aCommandable.getLabel());
			if(aCommandable.getIconType()!=null)
				menuItem.setIcon(icon(aCommandable.getIconType()));
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					if(aCommandable.getViewId()!=null){
						menuItem.setOutcome(aCommandable.getViewId().toString());
					
					}
				}else{
					switch(aCommandable.getViewType()){
					case DYNAMIC_CRUD_ONE:
						menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeDynamicCrudOne());
						menuItem.setParam(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter());
						break;
					case DYNAMIC_CRUD_MANY:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeDynamicCrudMany());break;
					case USERACCOUNT_LOGOUT:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeLogout());break;
					case LICENCE:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeLicense());break;
					case LICENCE_READ:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeLicenseRead());break;
					case TOOLS_CALENDAR:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsCalendar());break;
					case TOOLS_EXPORT_DATA_TABLE_TO_PDF:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsExportDataTableToPdf());break;
					case TOOLS_EXPORT_DATA_TABLE_TO_XLS:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsExportDataTableToXls());break;
					case TOOLS_REPORT:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsReport());break;
					case TOOLS_PRINT_DATA_TABLE:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeToolsPrintDataTable());break;
					
					case MODULE_REFERENCE_ENTITY:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeReferenceEntity());break;
					case MODULE_SECURITY:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeSecurity());break;
					case USER_ACCOUNTS:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeUserAccounts());break;
					case USER_ACCOUNT_CRUD_ONE:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeUserAccountCrudOne());break;
					case EVENT_CRUD_ONE:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeEventCrudOne());break;
					case NOTIFICATIONS:menuItem.setOutcome(WebNavigationManager.getInstance().getOutcomeNotifications());break;
					default:break;
					}
					
					if(aCommandable.getBusinessEntityInfos()!=null)
						menuItem.setParam(WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
				}
				for(Parameter parameter : aCommandable.getParameters()){
					if(StringUtils.isBlank(parameter.getName()) || parameter.getValue()==null)
						;
					else
						menuItem.setParam(parameter.getName(), parameter.getValue());
				}
				
			}else{
				
				menuItem.setUpdate(":form:contentPanel");
				menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
				menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
				menuItem.setGlobal(true);
				
				if(aCommandable.getViewType()==null){
					
				}else{
					switch(aCommandable.getViewType()){
					case USERACCOUNT_LOGOUT:
						menuItem.setCommand("#{userSession.logout()}");
						menuItem.setAjax(Boolean.FALSE);
						break;
					default:menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
					}
				}
								
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");	
			}
			
			
			if(parent!=null)
				parent.addElement(menuItem);
			return menuItem;
		}else{
			DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
			if(aCommandable.getIconType()!=null)
				subMenu.setIcon(icon(aCommandable.getIconType()));
			for(UICommandable commandable : aCommandable.getChildren())
				menuItem(commandable, subMenu, managedBeanName, fields);
			return subMenu;
		}
	}
	
	public MenuModel menuModel(@NotNull UIMenu aMenu,Class<?> managedBeanClass,String fieldName){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		MenuModel model = new DefaultMenuModel();
		for(UICommandable commandable : aMenu.getCommandables()){
			model.addElement(menuItem(commandable,null, Introspector.decapitalize(managedBeanClass.getSimpleName()), fieldName));
		}
		return model;
	}
	
	private String icon(IconType iconType){
		if(iconType==null)
			return null;
		switch(iconType){
		case ACTION_ADD:return "ui-icon-plus";
		case ACTION_CANCEL:return "ui-icon-close";
		case ACTION_OPEN:return "ui-icon-folder-open";
		case ACTION_REMOVE:return "ui-icon-trash";
		case ACTION_ADMINISTRATE:return "ui-icon-gear";
		case ACTION_HELP:return "ui-icon-help";
		case ACTION_APPLY:return "ui-icon-check";
		case ACTION_EDIT:return "ui-icon-pencil";
		case ACTION_GO_BACK:return "ui-icon-arrow-e";
		case ACTION_OK:return "ui-icon-check";
		case ACTION_SAVE:return "ui-icon-check";
		case ACTION_SEARCH:return "ui-icon-search";
		case ACTION_PREVIEW:return "ui-icon-image";
		case ACTION_LOGOUT:return "ui-icon-logout";
		case ACTION_EXPORT:return "ui-icon-document";
		case ACTION_PRINT:return "ui-icon-print";
		
		case PERSON:return "ui-icon-person";
		default:return null;
		}
	}
	
}
