package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuLocation;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuNode;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuRenderType;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class UserInterfaceMenuEditPage extends AbstractCollectionEditPage.Extends<UserInterfaceMenu,UserInterfaceMenuItem,UserInterfaceMenuEditPage.Item> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected ItemCollection<Item, UserInterfaceMenuItem, UserInterfaceMenu> instanciateItemCollection() {
		return createItemCollection(Item.class, UserInterfaceMenuItem.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter.Extends<Item,UserInterfaceMenuItem,UserInterfaceMenu>(identifiable,crud,form){
			private static final long serialVersionUID = 1L;
			
			@Override
			public void instanciated(AbstractItemCollection<Item, UserInterfaceMenuItem, UserInterfaceMenu, SelectItem> itemCollection,Item item) {
				super.instanciated(itemCollection, item);
				if(item.getIdentifiable().getNode()==null)
					item.getIdentifiable().setNode((UserInterfaceMenuNode) getInputChoice().getValue());
			}
			
			@Override
			public AbstractIdentifiable getMasterSelected(AbstractItemCollection<Item, UserInterfaceMenuItem, UserInterfaceMenu, SelectItem> itemCollection,
					UserInterfaceMenuItem identifiable) {
				return identifiable.getNode();
			}
			
		});
	}
	
	@Getter @Setter
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED,type=UserInterfaceMenuNode.class)
			})
	public static class Form extends AbstractForm.Extends<UserInterfaceMenu,UserInterfaceMenuItem> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private UserInterfaceMenuLocation location;
		@Input @InputChoice @InputOneChoice @InputOneCombo private UserInterfaceMenuRenderType renderType;
		
		public static final String FIELD_MENU_LOCATION = "location";
		public static final String FIELD_RENDER_TYPE = "renderType";
	}

	@Getter @Setter
	public static class Item extends AbstractItemCollectionItem<UserInterfaceMenuItem> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		
	}
}
