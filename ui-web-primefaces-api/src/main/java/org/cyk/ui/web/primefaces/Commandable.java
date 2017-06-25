package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.command.AbstractWebCommandable;
import org.cyk.utility.common.Constant;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

@NoArgsConstructor
public class Commandable extends AbstractWebCommandable implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;

	private static final String EL_MENU_ITEM_COMMAND_FORMAT = "#"+"{"+ "%s.%s.commandable('%s').command.execute(null)}";
	
	private CommandButton commandButton;
	@Getter @Setter private MenuModel menu;
	@Getter @Setter private String update;
	
	{
		update = WebManager.getInstance().getFormContentFullId()+" "+WebManager.getInstance().getFormMenuFullId();
	}
	
	public CommandButton getButton(){
		if(commandButton==null)
			commandButton = CommandBuilder.getInstance().commandButton(this);
		return commandButton;
	}
	
	public void updateLabel(String label){
		getButton().setValue(label);
		getButton().setTitle(getButton().getValue().toString());
	}
	
	public void onDialogReturn(SelectEvent selectEvent){}
	
	/**/
	
	@SuppressWarnings("unchecked")
	@Override
	public <COMPONENT> COMPONENT getComponent(Class<COMPONENT> componentClass,Object[] arguments) {
		if(CommandButton.class.equals(componentClass))
			return (COMPONENT) getCommandButton(this);
		if(MenuElement.class.equals(componentClass))
			return (COMPONENT) getMenuElement(this,(DefaultSubMenu)commonUtils.getValueAt(arguments, 0),(String)commonUtils.getValueAt(arguments, 1),(String[])commonUtils.getValueAt(arguments, 2));
		return super.getComponent(componentClass,arguments);
	}
	
	private CommandButton getCommandButton(Commandable aCommandable){
		CommandButton commandButton = new CommandButton();
		commandButton.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
		if(Boolean.TRUE.equals(aCommandable.getShowLabel()))
			commandButton.setValue(aCommandable.getLabel());
		commandButton.setUpdate(aCommandable.getUpdate());
		if(aCommandable.getIcon()!=null)
			commandButton.setIcon(FontAwesomeIconSet.INSTANCE.get(aCommandable.getIcon()));
		if(StringUtils.isEmpty(aCommandable.getTooltip()))
			commandButton.setTitle(aCommandable.getLabel());
		else
			commandButton.setTitle(aCommandable.getTooltip());
		if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
			commandButton.setProcess("@this");		
		commandButton.setOnclick(aCommandable.getOnClick());
		if(aCommandable.getCascadeStyleSheet()!=null){
			commandButton.setStyleClass(aCommandable.getCascadeStyleSheet().getClazz());
			commandButton.setStyle(aCommandable.getCascadeStyleSheet().getInline());
		}
		return commandButton;
	}
	
	private MenuElement getMenuElement(Commandable aCommandable,DefaultSubMenu parent,String managedBeanName,String[] fields){
		if(aCommandable.getChildren().isEmpty()){
			DefaultMenuItem	menuItem = new DefaultMenuItem();
			menuItem.setValue(aCommandable.getLabel());
			menuItem.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
			if(aCommandable.getIcon()!=null)
				menuItem.setIcon(FontAwesomeIconSet.INSTANCE.get(aCommandable.getIcon()));
			if(aCommandable.getCascadeStyleSheet()!=null){
				menuItem.setStyleClass(aCommandable.getCascadeStyleSheet().getClazz());
				menuItem.setStyle(aCommandable.getCascadeStyleSheet().getInline());
				
			}
			
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					if(aCommandable.getViewId()!=null){
						if(StringUtils.isNotBlank(aCommandable.getViewId().toString()))
							menuItem.setOutcome(aCommandable.getViewId().toString());
					
					}
				}else{
					WebNavigationManager navigationManager = inject(WebNavigationManager.class);
					switch(aCommandable.getViewType()){
					case HOME:menuItem.setOutcome(navigationManager.getOutcomePrivateIndex());break;
					case DYNAMIC_CRUD_ONE:
						menuItem.setOutcome(navigationManager.getOutcomeDynamicCrudOne());
						menuItem.setParam(UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameter.CRUD_CREATE);
						break;
					case DYNAMIC_CRUD_MANY:menuItem.setOutcome(navigationManager.getOutcomeDynamicCrudMany());break;
					case USERACCOUNT_LOGOUT:menuItem.setOutcome(navigationManager.getOutcomeLogout());break;
					case TOOLS_AGENDA:menuItem.setOutcome(navigationManager.getOutcomeToolsCalendar());break;
					case TOOLS_REPORT:menuItem.setOutcome(navigationManager.getOutcomeToolsReport());break;
					case TOOLS_PRINT_DATA_TABLE:menuItem.setOutcome(navigationManager.getOutcomeToolsPrintDataTable());break;
					
					case MODULE_REFERENCE_ENTITY:menuItem.setOutcome(navigationManager.getOutcomeReferenceEntity());break;
					case MODULE_SECURITY:menuItem.setOutcome(navigationManager.getOutcomeSecurity());break;
					case USER_ACCOUNT_LIST:menuItem.setOutcome(UIManager.getInstance().businessEntityInfos(UserAccount.class).getUserInterface().getListViewId());break;
					case USER_ACCOUNT_CHANGE_PASSWORD:
						menuItem.setOutcome(navigationManager.getOutcomeUserAccountChangePassword());
						break;
					case EVENT_CRUD_ONE:menuItem.setOutcome(navigationManager.getOutcomeEventCrudOne());break;
					case NOTIFICATIONS:menuItem.setOutcome(navigationManager.getOutcomeNotifications());break;
					default:break;
					}
					
					if(aCommandable.getBusinessEntityInfos()!=null)
						menuItem.setParam(UniformResourceLocatorParameter.CLASS, UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
				}
				//TODO navigation mode to be handled
				for(Parameter parameter : aCommandable.getParameters()){
					if(StringUtils.isBlank(parameter.getName()) || parameter.getValue()==null)
						;
					else
						menuItem.setParam(parameter.getName(), parameter.getValue());
				}
				
				menuItem.setOnclick(aCommandable.getOnClick());
			}else{
				
				menuItem.setUpdate(WebManager.getInstance().getFormContentFullId());//TODO make it constant
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
					default:menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,Constant.CHARACTER_DOT),aCommandable.getIdentifier())); 
					}
				}
								
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");	
			}
			//if(StringUtils.isNotBlank(aCommandable.getOnClick()))
			//	menuItem.setOnclick(aCommandable.getOnClick());
			
			if(parent!=null)
				parent.addElement(menuItem);
			return menuItem;
		}else{
			DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
			subMenu.setRendered(Boolean.TRUE.equals(aCommandable.getRendered()));
			if(aCommandable.getIcon()!=null)
				subMenu.setIcon(FontAwesomeIconSet.INSTANCE.get(aCommandable.getIcon()));
			if(aCommandable.getCascadeStyleSheet()!=null){
				subMenu.setStyleClass(aCommandable.getCascadeStyleSheet().getClazz());
				subMenu.setStyle(aCommandable.getCascadeStyleSheet().getInline());
			}
			for(UICommandable commandable : aCommandable.getChildren())
				getMenuElement((Commandable) commandable, subMenu, managedBeanName, fields);
			
			if(parent!=null)
				parent.addElement(subMenu);
			return subMenu;
		}
	}
}
