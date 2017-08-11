package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;

import org.cyk.system.root.business.api.AbstractCollectionItemBusiness;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.IdentifiableRuntimeCollection;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.utility.common.helper.ClassHelper;

public class ItemCollectionAdapter<ITEM_COLLECTION_ITEM extends AbstractItemCollectionItem<ENTITY>,ENTITY extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends ItemCollectionWebAdapter<ITEM_COLLECTION_ITEM,ENTITY,COLLECTION>  implements Serializable {
	
	private static final long serialVersionUID = 7806030819027062650L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemCollectionAdapter(COLLECTION collection, Crud crud,FormOneData form,Class<ENTITY> identifiableClass) {
		super(collection, crud, form,identifiableClass);
	}
	
	/**/
	
	public static class Extends<ITEM_COLLECTION_ITEM extends AbstractItemCollectionItem<ENTITY>,ENTITY extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ENTITY>> 
		extends ItemCollectionAdapter<ITEM_COLLECTION_ITEM,ENTITY,COLLECTION> implements Serializable {

		private static final long serialVersionUID = -7407375913363834604L;

		protected Class<ENTITY> entityClass;
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Extends(COLLECTION collection, Crud crud, FormOneData form,Class<ENTITY> identifiableClass) {
			super(collection, crud, form,identifiableClass);
			entityClass = (Class<ENTITY>) new ClassHelper().getParameterAt(getClass(), 1, AbstractCollectionItem.class);
		}
		
		@Override
		public IdentifiableRuntimeCollection<ENTITY> getRuntimeCollection() {
			return getCollection().getItems();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Collection<ENTITY> findByCollection(COLLECTION collection) {
			AbstractCollectionItemBusiness<ENTITY, COLLECTION> business = (AbstractCollectionItemBusiness<ENTITY, COLLECTION>) inject(BusinessInterfaceLocator.class).injectTyped(entityClass);
			return business.findByCollection(getCollection());
		}
		
		/*@SuppressWarnings("unchecked")
		@Override
		public Collection<ENTITY> load() {
			AbstractCollectionItemBusiness<ENTITY, COLLECTION> business = (AbstractCollectionItemBusiness<ENTITY, COLLECTION>) inject(BusinessInterfaceLocator.class).injectTyped(entityClass);
			getCollection().getItems().setCollection(business.findByCollection(getCollection()));
			return getCollection().getItems().getCollection();
		}*/
		
		@SuppressWarnings("unchecked")
		@Override
		public ENTITY instanciate(AbstractItemCollection<ITEM_COLLECTION_ITEM, ENTITY, COLLECTION, SelectItem> itemCollection) {
			AbstractCollectionItemBusiness<ENTITY, COLLECTION> business = (AbstractCollectionItemBusiness<ENTITY, COLLECTION>) inject(BusinessInterfaceLocator.class).injectTyped(entityClass);
			ENTITY entity = business.instanciateOne();
			entity.setCollection(collection);
			return entity;
		}
		
		@Override
		public Boolean isShowAddButton() {
			return Boolean.TRUE;
		}
		
	}
}