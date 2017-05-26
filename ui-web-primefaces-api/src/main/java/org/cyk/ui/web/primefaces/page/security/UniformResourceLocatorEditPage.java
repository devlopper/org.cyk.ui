package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class UniformResourceLocatorEditPage extends AbstractCollectionEditPage<UniformResourceLocator,UniformResourceLocatorParameter,UniformResourceLocatorEditPage.UniformResourceLocatorParameterItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	//private ItemCollection<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter,UniformResourceLocator> uniformResourceLocatorParameterCollection;
	
	@Override
	protected AbstractCollection<?> getCollection() {
		return null;
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		itemCollection = createItemCollection(UniformResourceLocatorParameterItem.class, UniformResourceLocatorParameter.class,identifiable 
				,new UniformResourceLocatorParameterItemAdapter(identifiable,crud,form));
		itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
		((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
		getIdentifiable().getParameters().setSynchonizationEnabled(Boolean.TRUE);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		super.transfer(command, parameter);
		if(form.getSubmitCommandable().getCommand()==command){
			getIdentifiable().getParameters().setCollection(itemCollection.getIdentifiables());
		}
	}
		
	@Getter @Setter
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED,type=UniformResourceLocatorParameter.class)
			})
	public static class Form extends AbstractForm<UniformResourceLocator,UniformResourceLocatorParameter> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText @NotNull private String address;
		
		public static final String FIELD_ADDRESS = "address";

		@Override
		protected AbstractCollection<?> getCollection() {
			return null;
		}
		
	}
	
	@Getter @Setter
	public static class UniformResourceLocatorParameterItem extends AbstractItemCollectionItem<UniformResourceLocatorParameter> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;
		private String name,value;
	}
	
	/**/
	
	public static class UniformResourceLocatorParameterItemAdapter extends org.cyk.ui.web.primefaces.ItemCollectionAdapter<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter,UniformResourceLocator> implements Serializable{
		private static final long serialVersionUID = 1L;

		public UniformResourceLocatorParameterItemAdapter(UniformResourceLocator uniformResourceLocator, Crud crud,FormOneData form) {
			super(uniformResourceLocator, crud,form);
		}
		
		@Override
		public Collection<UniformResourceLocatorParameter> load() {
			getCollection().getParameters().setCollection(inject(UniformResourceLocatorParameterBusiness.class).findByUniformResourceLocator(getCollection()));
			return getCollection().getParameters().getCollection();
		}
				
		@Override
		public void instanciated(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter,UniformResourceLocator,SelectItem> itemCollection,UniformResourceLocatorParameterItem item) {
			super.instanciated(itemCollection, item);
			item.setName(item.getIdentifiable().getName());
			item.setValue(item.getIdentifiable().getValue());
		}	
		
		@Override
		public void write(UniformResourceLocatorParameterItem item) {
			super.write(item);
			item.getIdentifiable().setName(item.getName());
			item.getIdentifiable().setValue(item.getValue());
			item.getIdentifiable().setUniformResourceLocator(collection);
		}
				
		@Override
		public Boolean isShowAddButton() {
			return Boolean.TRUE;
		}
		
		/*@Override
		public AbstractIdentifiable getMasterSelected(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter, UniformResourceLocator, SelectItem> itemCollection,
				UniformResourceLocatorParameterItem uniformResourceLocatorParameterItem) {
			return uniformResourceLocatorParameterItem.getSalableProduct();
		}*/
	}
	
}
