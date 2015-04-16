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
	
	private WebNavigationManager navigationManager = WebNavigationManager.getInstance();
	
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
					case HOME:menuItem.setOutcome(navigationManager.getOutcomePrivateIndex());break;
					case DYNAMIC_CRUD_ONE:
						menuItem.setOutcome(navigationManager.getOutcomeDynamicCrudOne());
						menuItem.setParam(UIManager.getInstance().getCrudParameter(), UIManager.getInstance().getCrudCreateParameter());
						break;
					case DYNAMIC_CRUD_MANY:menuItem.setOutcome(navigationManager.getOutcomeDynamicCrudMany());break;
					case USERACCOUNT_LOGOUT:menuItem.setOutcome(navigationManager.getOutcomeLogout());break;
					case LICENCE_READ:menuItem.setOutcome(navigationManager.getOutcomeLicenseRead());break;
					case TOOLS_AGENDA:menuItem.setOutcome(navigationManager.getOutcomeToolsCalendar());break;
					//case TOOLS_EXPORT_DATA_TABLE_TO_PDF:menuItem.setOutcome(navigationManager.getOutcomeToolsExportDataTableToPdf());break;
					//case TOOLS_EXPORT_DATA_TABLE_TO_XLS:menuItem.setOutcome(navigationManager.getOutcomeToolsExportDataTableToXls());break;
					case TOOLS_REPORT:menuItem.setOutcome(navigationManager.getOutcomeToolsReport());break;
					case TOOLS_PRINT_DATA_TABLE:menuItem.setOutcome(navigationManager.getOutcomeToolsPrintDataTable());break;
					
					case MODULE_REFERENCE_ENTITY:menuItem.setOutcome(navigationManager.getOutcomeReferenceEntity());break;
					case MODULE_SECURITY:menuItem.setOutcome(navigationManager.getOutcomeSecurity());break;
					case USER_ACCOUNTS:menuItem.setOutcome(navigationManager.getOutcomeUserAccounts());break;
					case USER_ACCOUNT_CRUD_ONE:menuItem.setOutcome(navigationManager.getOutcomeUserAccountCrudOne());break;
					case USER_ACCOUNT_CONSULT:
						menuItem.setOutcome(navigationManager.getOutcomeUserAccountConsult());
						break;
					case USER_ACCOUNT_CHANGE_PASSWORD:
						menuItem.setOutcome(navigationManager.getOutcomeUserAccountChangePassword());
						break;
					case EVENT_CRUD_ONE:menuItem.setOutcome(navigationManager.getOutcomeEventCrudOne());break;
					case NOTIFICATIONS:menuItem.setOutcome(navigationManager.getOutcomeNotifications());break;
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
		case ACTION_LOGOUT:return "ui-icon-extlink";
		case ACTION_EXPORT:return "ui-icon-document";
		case ACTION_PRINT:return "ui-icon-print";
		case ACTION_CLEAR: return "ui-icon-trash";
		case ACTION_EXPORT_EXCEL: return "ui-icon-";
		case ACTION_EXPORT_PDF: return "ui-icon-";
		case ACTION_SET: return "ui-icon-wrench";
		
		case THING_APPLICATION: return "ui-icon-";
		case THING_CALENDAR: return "ui-icon-calendar";
		case THING_CONTROLPANEL: return "ui-icon-gear";
		case THING_HELP: return "ui-icon-help";
		case THING_LICENCE: return "ui-icon-document";
		case THING_LIST: return "ui-icon-document";
		case THING_NOTIFICATIONS: return "ui-icon-flag";
		case THING_REPORT: return "ui-icon-document";
		case THING_SECURITY: return "ui-icon-key";
		case THING_TOOLS: return "ui-icon-wrench";
		case THING_USERACCOUNT: return "ui-icon-suitcase";
		case THING_HOME: return "ui-icon-home";
		case THING_CONNECTED: return "ui-icon-newin";
		
		case PERSON:return "ui-icon-person";
		default:return null;
		}
	}
	
}
