package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.ui.web.primefaces.page.crud.AbstractConsultPage;

@Getter @Setter
public abstract class AbstractCollectionConsultPage<IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,ITEM_DETAILS extends AbstractOutputDetails<?>> extends AbstractConsultPage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected Table<ITEM_DETAILS> itemTable;
	
	protected abstract COLLECTION getCollection();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void consultInitialisation() {
		super.consultInitialisation();
		 
		final Class<ITEM> itemClass = getItemClass();
		final Class<ITEM_DETAILS> itemDetailsClass = getItemDetailsClass();
		
		itemTable = (Table<ITEM_DETAILS>) createDetailsTable(itemDetailsClass, new DetailsConfigurationListener.Table.Adapter<ITEM,ITEM_DETAILS>(itemClass, itemDetailsClass){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ITEM> getIdentifiables() {
				return ((AbstractCollectionItemBusiness<ITEM, COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(itemClass)).findByCollection(identifiable);
			}
			@Override
			public ColumnAdapter getColumnAdapter() { 
				return getDetailsConfiguration(itemDetailsClass).getTableColumnAdapter(null,AbstractCollectionConsultPage.this);
			}
			
		});
		
		/*itemTable = (Table<ITEM_DETAILS>) createDetailsTable(itemDetailsClass, new DetailsConfigurationListener.Table.Adapter<ITEM,ITEM_DETAILS>(itemClass, itemDetailsClass){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ITEM> getIdentifiables() {
				return ((AbstractCollectionItemBusiness<ITEM, COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(itemClass)).findByCollection(identifiable);
			}
		});*/
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM> getItemClass(){
		return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM_DETAILS> getItemDetailsClass(){
		return (Class<ITEM_DETAILS>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[3];
	}
	
	/**/
	
	public static abstract class AbstractDefault<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,ITEM_DETAILS extends AbstractOutputDetails<?>> extends AbstractCollectionConsultPage<COLLECTION,COLLECTION,ITEM,ITEM_DETAILS> {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected COLLECTION getCollection() {
			return identifiable;
		}
		
		@Override @SuppressWarnings("unchecked")
		protected Class<ITEM> getItemClass() {
			return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		}
		
		@Override @SuppressWarnings("unchecked")
		protected Class<ITEM_DETAILS> getItemDetailsClass() {
			return (Class<ITEM_DETAILS>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
		}
	}
	
}
