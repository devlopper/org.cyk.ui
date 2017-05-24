package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.network.UniformResourceLocatorParameterBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractEnumerationForm;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

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
		/*uniformResourceLocatorParameterCollection = createItemCollection(UniformResourceLocatorParameterItem.class, UniformResourceLocatorParameter.class,identifiable
				,new ItemCollectionWebAdapter<UniformResourceLocatorParameterItem,UniformResourceLocatorParameter,UniformResourceLocator>(identifiable,crud){
			private static final long serialVersionUID = -3872058204105902514L;
			@Override
			public Collection<UniformResourceLocatorParameter> load() {
				return inject(UniformResourceLocatorParameterBusiness.class).findByUniformResourceLocator(identifiable);
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
			}
			@Override
			public Boolean isShowAddButton() {
				return Boolean.TRUE;
			}
			
		});
		*/
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
		
		/*@Override
		public UniformResourceLocatorParameter instanciate(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter,UniformResourceLocator, SelectItem> itemCollection) {
			UniformResourceLocatorParameter uniformResourceLocatorParameter = inject(UniformResourceLocatorParameterBusiness.class).instanciateOne();
			
			return uniformResourceLocatorParameter;
		}*/
		
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
		}
		
		@Override
		public void delete(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter,UniformResourceLocator, SelectItem> itemCollection,UniformResourceLocatorParameterItem item) {
			super.delete(itemCollection, item);
			//inject(UniformResourceLocatorBusiness.class).remove(collection, item.getIdentifiable());
		}
		
		@Override
		public Boolean isShowAddButton() {
			return Boolean.TRUE;
		}
		
		@Override
		public void read(UniformResourceLocatorParameterItem item) {
			super.read(item);
			/*
			item.setCode(item.getIdentifiable().getSalableProduct().getProduct().getCode());
			item.setName(item.getIdentifiable().getSalableProduct().getProduct().getName());
			item.setUnitPrice(inject(NumberBusiness.class).format(item.getIdentifiable().getSalableProduct().getPrice()));
			item.setQuantity(item.getIdentifiable().getQuantity());
			item.setQuantifiedPrice(inject(NumberBusiness.class).format(item.getIdentifiable().getQuantifiedPrice()));
			item.setReduction(item.getIdentifiable().getReduction()==null?null:new BigDecimal(item.getIdentifiable().getReduction().intValue()));
			item.setTotalPrice(inject(NumberBusiness.class).format(item.getIdentifiable().getCost().getValue()));
			*/
			//item.setInstanceChoices(new ArrayList<>(inject(SalableProductInstanceBusiness.class).findByCollection(item.getIdentifiable().getSalableProduct())));
		}
		
		/*@Override
		public AbstractIdentifiable getMasterSelected(AbstractItemCollection<UniformResourceLocatorParameterItem, UniformResourceLocatorParameter, UniformResourceLocator, SelectItem> itemCollection,
				UniformResourceLocatorParameterItem uniformResourceLocatorParameterItem) {
			return uniformResourceLocatorParameterItem.getSalableProduct();
		}*/
	}
	
}
