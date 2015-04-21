package org.cyk.ui.web.vaadin;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.menu.UIMenu;
import org.cyk.ui.api.model.HierarchyNode;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Tree;

public class CommandBuilder implements Serializable {

	private static final long serialVersionUID = 6706193290921067166L;
	
	private static final String MENU_BAR_HEIGHT = "23px";
	
	private static final CommandBuilder INSTANCE = new CommandBuilder();
	
	public static CommandBuilder getInstance() {
		return INSTANCE;
	}
	
	public Button button(UICommandable aCommandable){
		Button button = new Button();
		button.setVisible(Boolean.TRUE.equals(aCommandable.getRendered()));
		if(Boolean.TRUE.equals(aCommandable.getShowLabel()))
			button.setCaption(aCommandable.getLabel());
		//commandButton.setUpdate(command.getUpdate());
		if(aCommandable.getIconType()!=null)
			button.setIcon(icon(aCommandable.getIconType()));
		if(StringUtils.isEmpty(aCommandable.getTooltip()))
			button.setDescription(aCommandable.getLabel());
		else
			button.setDescription(aCommandable.getTooltip());
		//if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
		//	commandButton.setProcess("@this");		
		/*
		ConfirmBehavior confirmBehavior = new ConfirmBehavior();
		confirmBehavior.setHeader("Hello Titke");
		confirmBehavior.setMessage("Message");
		commandButton.addClientBehavior("org.primefaces.behavior.ConfirmBehavior", confirmBehavior);
		*/
		return button;
	}
	
	public MenuItem menuItem(MenuBar menuBar,UICommandable aCommandable,MenuItem parent,Object param){
		MenuItem menuItem;
		if(parent==null)
			menuItem = menuBar.addItem(aCommandable.getLabel(),icon(aCommandable.getIconType()),null);
		else
			menuItem = parent.addItem(aCommandable.getLabel(),icon(aCommandable.getIconType()),null);
		
		menuItem.setDescription(aCommandable.getLabel());
		
		if(aCommandable.getChildren().isEmpty()){
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					
				}else{
					/*
					switch(aCommandable.getViewType()){
					case DYNAMIC_FORM_EDITOR:menuItem.setOutcome("dynamiceditor");break;
					case DYNAMIC_FORM_TABLE:menuItem.setOutcome("dynamictable");break;
					case DYNAMIC_FORM_HIERARCHY:menuItem.setOutcome("dynamichierarchy");break;
					case MANAGEMENT_DEPLOYMENT:menuItem.setOutcome("deploymentmanagement");break;
					case TOOLS_CALENDAR:menuItem.setOutcome("toolscalendar");break;
					default:break;
					}
					if(aCommandable.getBusinessEntityInfos()!=null)
						menuItem.setParam(WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
					*/
				}
				
			}else{
				menuItem.setCommand( new VaadinCommandable(aCommandable,param));
				
				/*
				menuItem.setUpdate(":form:contentPanel");
				menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
				menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
				menuItem.setGlobal(true);
				
				menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");	
				*/
			}
		}else{
			for(UICommandable commandable : aCommandable.getChildren())
				menuItem(menuBar,commandable, menuItem,param);
		}
		
		return menuItem;
	}
	
	public MenuBar menuBar(UIMenu aMenu,Object param){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		MenuBar menuBar = new MenuBar();
		for(UICommandable commandable : aMenu.getCommandables())
			menuItem(menuBar,commandable,null,param);
		menuBar.setSizeFull();
		menuBar.setHeight(MENU_BAR_HEIGHT);
		return menuBar;
	}
	
	/*
	public MenuElement menuItem(UICommandable aCommandable,DefaultSubMenu parent,String managedBeanName,@NotNull String...fields){
		if(aCommandable.getChildren().isEmpty()){
			DefaultMenuItem	menuItem = new DefaultMenuItem();
			menuItem.setValue(aCommandable.getLabel());
			if(aCommandable.getIconType()!=null)
				menuItem.setIcon(icon(aCommandable.getIconType()));
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					
				}else{
					switch(aCommandable.getViewType()){
					case DYNAMIC_FORM_EDITOR:menuItem.setOutcome("dynamiceditor");break;
					case DYNAMIC_FORM_TABLE:menuItem.setOutcome("dynamictable");break;
					case DYNAMIC_FORM_HIERARCHY:menuItem.setOutcome("dynamichierarchy");break;
					case MANAGEMENT_DEPLOYMENT:menuItem.setOutcome("deploymentmanagement");break;
					case TOOLS_CALENDAR:menuItem.setOutcome("toolscalendar");break;
					default:break;
					}
					if(aCommandable.getBusinessEntityInfos()!=null)
						menuItem.setParam(WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
				}
				
			}else{
				menuItem.setUpdate(":form:contentPanel");
				menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
				menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
				menuItem.setGlobal(true);
				
				menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
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
	}*/
	
	public Object treeItem(Tree tree,UICommandable aCommandable,Object parent){
		HierarchyNode hierarchyNode = new HierarchyNode();
		hierarchyNode.setLabel(aCommandable.getLabel());
		hierarchyNode.setData(aCommandable);
		tree.addItem(hierarchyNode);
		if(parent!=null)
			tree.setParent(hierarchyNode, parent);
		
		//System.out.println("CommandBuilder.treeItem() : "+item);
		tree.setChildrenAllowed(hierarchyNode, !aCommandable.getChildren().isEmpty());
		if(aCommandable.getChildren().isEmpty()){
			
			//if(aCommandable.getIconType()!=null)
			//	menuItem.setIcon(icon(aCommandable.getIconType()));
			if(aCommandable.getIsNavigationCommand()){
				if(aCommandable.getViewType()==null){
					
				}else{
					/*switch(aCommandable.getViewType()){
					case DYNAMIC_FORM_EDITOR:menuItem.setOutcome("dynamiceditor");break;
					case DYNAMIC_FORM_TABLE:menuItem.setOutcome("dynamictable");break;
					case DYNAMIC_FORM_HIERARCHY:menuItem.setOutcome("dynamichierarchy");break;
					case MANAGEMENT_DEPLOYMENT:menuItem.setOutcome("deploymentmanagement");break;
					case TOOLS_CALENDAR:menuItem.setOutcome("toolscalendar");break;
					default:break;
					}*/
					//if(aCommandable.getBusinessEntityInfos()!=null)
					//	menuItem.setParam(WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(aCommandable.getBusinessEntityInfos()));
				}
				
			}else{
				/*
				menuItem.setUpdate(":form:contentPanel");
				menuItem.setOnstart(WebManager.getInstance().getBlockUIDialogWidgetId()+".show();");
				menuItem.setOnsuccess(WebManager.getInstance().getBlockUIDialogWidgetId()+".hide();");
				menuItem.setGlobal(true);
				
				menuItem.setCommand(String.format(EL_MENU_ITEM_COMMAND_FORMAT, managedBeanName,StringUtils.join(fields,"."),aCommandable.getIdentifier())); 
				if(UICommandable.ProcessGroup.THIS.equals(aCommandable.getProcessGroup()))
					menuItem.setProcess("@this");
					*/	
			}
			
			//System.out.println("Parent : "+parent);
			//if(parent!=null)
			//	tree.setParent(hierarchyNode, parent);
			
		}else{
			//DefaultSubMenu subMenu = new DefaultSubMenu(aCommandable.getLabel());
			//if(aCommandable.getIconType()!=null)
			//	subMenu.setIcon(icon(aCommandable.getIconType()));
			for(UICommandable commandable : aCommandable.getChildren())
				treeItem(tree,commandable, hierarchyNode);
		}
		
		return hierarchyNode;
	}
	
	public Tree tree(UIMenu aMenu){
		if(aMenu==null || aMenu.getCommandables()==null || aMenu.getCommandables().isEmpty())
			return null;
		Tree tree = new Tree();
		for(UICommandable commandable : aMenu.getCommandables()){
			tree.addItem(treeItem(tree,commandable,null));
		}
		return tree;
	}
	
	private Object treeItemObject(Tree tree,Object node,HierarchyNode parent){
		HierarchyNode hierarchyNode = new HierarchyNode(node);
		tree.addItem(hierarchyNode);
		if(parent!=null)
			tree.setParent(hierarchyNode, parent);
		
		Collection<Object> children = HierarchyNode.CHILDREN_METHOD.execute(node);
		//System.out.println("CommandBuilder.treeItemObject() "+node+" : "+children);
		tree.setChildrenAllowed(hierarchyNode, children!=null && !children.isEmpty());
		
		if(children==null || children.isEmpty()){
			
		}else{
			for(Object child : children)
				treeItemObject(tree,child, hierarchyNode);
		}
		
		return hierarchyNode;
	}
	
	public Tree tree(HierarchyNode base,Collection<?> roots){
		if(roots==null || roots.isEmpty())
			return null;
		Tree tree = new Tree();
		if(base!=null)
			tree.addItem(base);
		for(Object node : roots){
			tree.addItem(treeItemObject(tree,node,base));
		}
		return tree;
	}
	
	public Tree tree(String baseName,Collection<?> roots){
		HierarchyNode base = new HierarchyNode();
		base.setLabel(baseName);
		return tree(base, roots);
	}
	
	private FontAwesome icon(IconType iconType){
		if(iconType==null)
			return null;
		
		switch(iconType){
		case ACTION_ADD:return FontAwesome.PLUS;
		case ACTION_CANCEL:return FontAwesome.STOP;
		case ACTION_OPEN:return FontAwesome.FOLDER_OPEN;
		case ACTION_REMOVE:return FontAwesome.MINUS;
		case ACTION_ADMINISTRATE:return FontAwesome.ADN;
		case ACTION_HELP:return FontAwesome.QUESTION;
		case ACTION_APPLY:return FontAwesome.CHECK_CIRCLE;
		case ACTION_EDIT:return FontAwesome.PENCIL;
		case ACTION_GO_BACK:return FontAwesome.BACKWARD;
		case ACTION_OK:return FontAwesome.CHECK;
		case ACTION_SAVE:return FontAwesome.FLOPPY_O;
		case ACTION_SEARCH:return FontAwesome.SEARCH;
		case ACTION_EXPORT:return FontAwesome.PRINT;
		case ACTION_EXPORT_EXCEL:return FontAwesome.FILE_TEXT;
		case ACTION_EXPORT_PDF:return FontAwesome.FILE;
		default:return FontAwesome.ADN;
		}
	}
	
	/**/
	@Getter
	public static class VaadinCommandable implements Serializable,Command{
		private static final long serialVersionUID = -3003339900107057907L;
		private UICommandable commandable;
		private Object param;
		
		public VaadinCommandable(UICommandable commandable,Object param) {
			super();
			this.commandable = commandable;
			this.param = param;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			commandable.getCommand().execute(param);
		}
		
	}
	
}
