package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.RoleUniformResourceLocator;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class RoleEditPage extends AbstractCollectionEditPage<Role,RoleUniformResourceLocator,RoleEditPage.RoleUniformResourceLocatorItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		itemCollection = createItemCollection(RoleUniformResourceLocatorItem.class, RoleUniformResourceLocator.class,identifiable 
				,new RoleUniformResourceLocatorItemAdapter(identifiable,crud,form));
		//itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
		//((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
		getIdentifiable().getRoleUniformResourceLocators().setSynchonizationEnabled(Boolean.TRUE);
	}
	
	@Override
	protected AbstractCollection<?> getCollection() {
		return null;
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		super.transfer(command, parameter);
		if(form.getSubmitCommandable().getCommand()==command){
			getIdentifiable().getRoleUniformResourceLocators().setCollection(itemCollection.getIdentifiables());
		}
	}
		
	@Getter @Setter
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED,type=UniformResourceLocator.class)
			})
	public static class Form extends AbstractForm<Role,RoleUniformResourceLocator> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Override
		protected AbstractCollection<?> getCollection() {
			return null;
		}
		
	}
	
	@Getter @Setter
	public static class RoleUniformResourceLocatorItem extends AbstractItemCollectionItem<RoleUniformResourceLocator> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name;
	}
	
	/**/
	
	public static class RoleUniformResourceLocatorItemAdapter extends org.cyk.ui.web.primefaces.ItemCollectionAdapter<RoleUniformResourceLocatorItem,RoleUniformResourceLocator,Role> implements Serializable{
		private static final long serialVersionUID = 1L;

		public RoleUniformResourceLocatorItemAdapter(Role role, Crud crud,FormOneData<?> form) {
			super(role, crud,form);
		}
		
		@Override
		public Collection<RoleUniformResourceLocator> load() {
			getCollection().getRoleUniformResourceLocators().setCollection(inject(RoleUniformResourceLocatorBusiness.class).findByRole(getCollection()));
			return getCollection().getRoleUniformResourceLocators().getCollection();
		}
				
		@Override
		public void instanciated(AbstractItemCollection<RoleUniformResourceLocatorItem, RoleUniformResourceLocator,Role,SelectItem> itemCollection,RoleUniformResourceLocatorItem item) {
			super.instanciated(itemCollection, item);
			if(item.getIdentifiable().getUniformResourceLocator()==null)
				item.getIdentifiable().setUniformResourceLocator((UniformResourceLocator) getInputChoice().getValue());
			if(item.getIdentifiable().getRole()==null)
				item.getIdentifiable().setRole(collection);
			item.setName(item.getIdentifiable().getUniformResourceLocator().getName());
	
		}	
				
		@Override
		public Boolean isShowAddButton() {
			return Boolean.TRUE;
		}
		
		@Override
		public AbstractIdentifiable getMasterSelected(AbstractItemCollection<RoleUniformResourceLocatorItem, RoleUniformResourceLocator, Role, SelectItem> itemCollection,
				RoleUniformResourceLocator roleUniformResourceLocator) {
			return roleUniformResourceLocator.getUniformResourceLocator();
		}
	}

	
}
