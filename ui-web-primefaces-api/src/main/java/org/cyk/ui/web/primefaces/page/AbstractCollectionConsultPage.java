package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractCollectionConsultPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable,ITEM_DETAILS extends AbstractOutputDetails<?>> extends AbstractConsultPage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected Table<ITEM_DETAILS> itemTable;
	
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		 
		//final Class<ITEM> itemClass = getItemClass();
		//final Class<ITEM_DETAILS> itemDetailsClass = getItemDetailsClass();
		
		/*itemTable = (Table<ITEM_DETAILS>) createDetailsTable(itemDetailsClass, new DetailsConfigurationListener.Table.Adapter<ITEM,ITEM_DETAILS>(itemClass, itemDetailsClass){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ITEM> getIdentifiables() {
				return ((AbstractCollectionItemBusiness<ITEM, COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(itemClass)).findByCollection(identifiable);
			}
		});*/
		
		itemTable = (Table<ITEM_DETAILS>) createDetailsTable(getItemDetailsClass(), new DetailsConfigurationListener.Table.Adapter<ITEM,ITEM_DETAILS>(getItemClass(), getItemDetailsClass()){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ITEM> getIdentifiables() {
				return findByCollection(identifiable);
			}
			@Override
			public ColumnAdapter getColumnAdapter() { 
				return getDetailsConfiguration(getItemDetailsClass()).getTableColumnAdapter(null,AbstractCollectionConsultPage.this);
			}
			
			@Override
			public RowAdapter<ITEM_DETAILS> getRowAdapter() {
				return getItemTableRowAdapter();
			}
			
			@Override
			public Boolean getEnabledInDefaultTab() {
				return getEnableItemTableInDefaultTab();
			}
			
			@Override
			public String getTabId() {
				return getItemTableTabId();
			}
			
			@Override
			public Crud[] getCruds() {
				return getItemTableCruds();
			}
			
		});
	}
	
	protected Boolean getEnableItemTableInDefaultTab(){
		return Boolean.FALSE;
	}
	
	protected String getItemTableTabId(){
		return IdentifierProvider.Adapter.getTabOf(getItemClass());
	}
	
	protected RowAdapter<ITEM_DETAILS> getItemTableRowAdapter(){
		return null;
	}
	
	protected Crud[] getItemTableCruds(){
		return new Crud[]{Crud.READ,Crud.UPDATE,Crud.DELETE};
	}
	
	protected abstract Collection<ITEM> findByCollection(COLLECTION collection);
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM> getItemClass(){
		return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM_DETAILS> getItemDetailsClass(){
		return (Class<ITEM_DETAILS>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	/**/
	
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,ITEM_DETAILS extends AbstractOutputDetails<ITEM>> extends AbstractCollectionConsultPage<COLLECTION,ITEM,ITEM_DETAILS> {
		private static final long serialVersionUID = 1L;
		
		@Override @SuppressWarnings("unchecked")
		protected Collection<ITEM> findByCollection(COLLECTION collection) {
			return ((AbstractCollectionItemBusiness<ITEM, COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(getItemClass())).findByCollection(identifiable);
		}
				
	}
	
}
