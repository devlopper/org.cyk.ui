package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollectionItemEditPage<IDENTIFIABLE extends AbstractIdentifiable,COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractCrudOnePage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractCollectionItem<?> getItem();
	
	@Override
	protected IDENTIFIABLE instanciateIdentifiable() {
		Long collectionIdentifier = requestParameterLong(getCollectionClass());
		if(collectionIdentifier==null)
			return super.instanciateIdentifiable();
		return instanciateIdentifiable(collectionIdentifier);
	}
	
	protected IDENTIFIABLE instanciateIdentifiable(Long collectionIdentifier){
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected Class<COLLECTION> getCollectionClass(){
		return (Class<COLLECTION>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM> getItemClass(){
		return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
	}
	
	public static class AbstractDefault<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractCollectionItemEditPage<ITEM,COLLECTION,ITEM>{
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
		
		@SuppressWarnings("unchecked")
		@Override
		protected ITEM instanciateIdentifiable(Long collectionIdentifier) {
			return ((AbstractCollectionItemBusiness<ITEM,COLLECTION>)inject(BusinessInterfaceLocator.class).injectTyped(getItemClass()))
					.instanciateOne(inject(BusinessInterfaceLocator.class).injectTyped(getCollectionClass()).find(collectionIdentifier));
		}
		
	}
	
	@Getter @Setter
	protected static abstract class AbstractForm<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractFormModel<ITEM> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull protected COLLECTION collection;
		
		public static final String FIELD_COLLECTION = "collection";
		
		@Override
		public void read() {
			super.read();
			identifiable.setCascadeOperationToMaster(Boolean.TRUE);
		}
		
		/**/
		
		public static abstract class AbstractDefault<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractForm<COLLECTION,ITEM> implements Serializable  {
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
