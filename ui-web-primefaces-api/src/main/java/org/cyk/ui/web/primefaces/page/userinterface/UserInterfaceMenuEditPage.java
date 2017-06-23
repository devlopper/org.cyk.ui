package org.cyk.ui.web.primefaces.page.userinterface;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.userinterface.UserInterfaceMenuItemBusiness;
import org.cyk.system.root.model.userinterface.UserInterfaceMenu;
import org.cyk.system.root.model.userinterface.UserInterfaceMenuItem;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class UserInterfaceMenuEditPage extends AbstractCollectionEditPage.Extends<UserInterfaceMenu,UserInterfaceMenuItem,UserInterfaceMenuEditPage.Item> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.getItems().setSynchonizationEnabled(Boolean.TRUE);
		itemCollection = createItemCollection(Item.class, UserInterfaceMenuItem.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter<Item,UserInterfaceMenuItem,UserInterfaceMenu>(identifiable,crud,form){
					private static final long serialVersionUID = 1L;
			
					@Override
					public Collection<UserInterfaceMenuItem> load() {
						getCollection().getItems().setCollection(inject(UserInterfaceMenuItemBusiness.class).findByCollection(getCollection()));
						return getCollection().getItems().getCollection();
					}
					
					@Override
					public UserInterfaceMenuItem instanciate(AbstractItemCollection<Item, UserInterfaceMenuItem, UserInterfaceMenu, SelectItem> itemCollection) {
						UserInterfaceMenuItem interval = inject(UserInterfaceMenuItemBusiness.class).instanciateOne();
						interval.setCollection(collection);
						return interval;
					}
					
					@Override
					public Boolean isShowAddButton() {
						return Boolean.TRUE;
					}
					
					@Override
					public void read(Item item) {
						super.read(item);
						
					}
					
					@Override
					public void write(Item item) {
						super.write(item);
						
					}
					
		});
		
		itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
		((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm.Extends<UserInterfaceMenu,UserInterfaceMenuItem> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		
	}

	@Getter @Setter
	public static class Item extends AbstractItemCollectionItem<UserInterfaceMenuItem> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		
		
	}
}
