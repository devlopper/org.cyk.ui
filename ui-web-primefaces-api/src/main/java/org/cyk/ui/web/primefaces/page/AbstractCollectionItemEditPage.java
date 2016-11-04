package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter
public abstract class AbstractCollectionItemEditPage<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCrudOnePage<ITEM> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractCollectionItem<?> getItem();
	
	@SuppressWarnings("unchecked")
	protected Class<COLLECTION> getCollectionClass(){
		return (Class<COLLECTION>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM> getItemClass(){
		return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public static class AbstractDefault<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractCollectionItemEditPage<ITEM,COLLECTION>{
		private static final long serialVersionUID = 1L;

		@Override
		protected AbstractCollectionItem<?> getItem() {
			return identifiable;
		}
		
		@SuppressWarnings("unchecked")
		protected Class<COLLECTION> getCollectionClass(){
			return (Class<COLLECTION>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		}
		
		@SuppressWarnings("unchecked")
		protected Class<ITEM> getItemClass(){
			return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected ITEM instanciateIdentifiable() {
			ITEM identifiable = super.instanciateIdentifiable();
			Long collectionIdentifier = requestParameterLong(getCollectionClass());
			if(collectionIdentifier==null){
				
			}else{
				COLLECTION collection = inject(BusinessInterfaceLocator.class).injectTyped(getCollectionClass()).find(collectionIdentifier);
				if(identifiable instanceof AbstractCollectionItem)
					((AbstractCollectionItem)identifiable).setCollection(collection);
			}
			return identifiable;
		}
		
	}
	
	@Getter @Setter
	protected static abstract class AbstractForm<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<ITEM> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull protected COLLECTION collection;
		
		public static final String FIELD_COLLECTION = "collection";
		
		@Override
		public void read() {
			super.read();
			identifiable.setCascadeOperationToMaster(Boolean.TRUE);
		}
		
		/**/
		
		public static abstract class AbstractDefault<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractForm<ITEM,COLLECTION> implements Serializable  {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void read() {
				super.read();
				collection = getIdentifiable().getCollection();
			}
			
			@Override
			public void write() {
				super.write();
				getIdentifiable().setCollection(collection);
			}
			
		}
		
	}

}
