package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
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
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(Crud.isCreateOrUpdate(crud)){
			selectCollection(webManager.getIdentifiableFromRequestParameter(getCollectionClass(),Boolean.TRUE));
		}
	}
	
	protected void selectCollection(COLLECTION collection){
		setFieldValue(AbstractForm.FIELD_COLLECTION, collection);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM> getItemClass(){
		return (Class<ITEM>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected abstract AbstractCollectionItem<?> getItem();
	
	@SuppressWarnings("unchecked")
	protected Class<COLLECTION> getCollectionClass(){
		return (Class<COLLECTION>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
		
	@Override
	protected ITEM instanciateIdentifiable() {
		ITEM item;
		COLLECTION collection = webManager.getIdentifiableFromRequestParameter(getCollectionClass(),Boolean.TRUE);
		if(collection==null){
			item = super.instanciateIdentifiable();
		}else{
			return instanciateIdentifiable(collection);
		}
		return item;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected ITEM instanciateIdentifiable(COLLECTION collection){
		ITEM item = super.instanciateIdentifiable();
		if(item instanceof AbstractCollectionItem)
			((AbstractCollectionItem)item).setCollection(collection);
		return item;
	}
	
	public static class Extends<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractCollectionItemEditPage<ITEM,COLLECTION>{
		private static final long serialVersionUID = 1L;

		@Override
		protected AbstractCollectionItem<?> getItem() {
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
