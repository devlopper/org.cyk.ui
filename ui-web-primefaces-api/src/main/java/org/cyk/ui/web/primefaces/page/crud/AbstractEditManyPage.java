package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractEditManyPage<ENTITY extends AbstractIdentifiable,ITEM_COLLECTION_ITEM extends AbstractItemCollectionItem<ENTITY>> extends AbstractCrudOnePage<ENTITY> implements Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	protected ItemCollection<ITEM_COLLECTION_ITEM,ENTITY,?> elementCollection;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractEditManyPage.Listener<?,?> listener : Listener.COLLECTION)
			listener.initialisationStarted(this);
		Class<ENTITY> entityClass = (Class<ENTITY>) businessEntityInfos.getClazz();
		
		elementCollection = createItemCollection(getItemCollectionItemClass(), entityClass,null,getItemCollectionAdapter());
		elementCollection.getDeleteCommandable().setRendered(Boolean.FALSE);
		elementCollection.getApplicableValueQuestion().setRendered(Boolean.FALSE);
		elementCollection.getAddCommandable().setRendered(Boolean.FALSE);
		elementCollection.setShowItemLabel(Boolean.TRUE);
		elementCollection.setShowHeader(Boolean.FALSE);
			
		for(AbstractEditManyPage.Listener<?,?> listener : Listener.COLLECTION)
			listener.initialisationEnded(this);
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractEditManyPage.Listener<?,?> listener : Listener.COLLECTION)
			listener.afterInitialisationStarted(this);
		
		elementCollection.setLabel(languageBusiness.findClassLabelText(elementCollection.getIdentifiableClass()));
		
		for(AbstractEditManyPage.Listener<?,?> listener : Listener.COLLECTION)
			listener.afterInitialisationEnded(this);
	}
	
	public ItemCollectionWebAdapter<ITEM_COLLECTION_ITEM,ENTITY,?> getItemCollectionAdapter(){
		return new ItemCollectionAdapter<>(businessEntityInfos);
	}
	
	@Override
	protected void create() {
		Collection<AbstractIdentifiable> identifiables = new ArrayList<>();
		for(ENTITY entity : elementCollection.getIdentifiables()){
			identifiables.add(entity);
		}
		genericBusiness.update(identifiables);
	}
	
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		super.transfer(command, parameter);
		if(form.getSubmitCommandable().getCommand() == command ){
			elementCollection.write();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Class<ITEM_COLLECTION_ITEM> getItemCollectionItemClass(){
		return (Class<ITEM_COLLECTION_ITEM>) formModelClass;
	}
	
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		return null;
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		Class<?> aClass = super.__formModelClass__();
		aClass = aClass==null ? (identifiableConfiguration==null?businessEntityInfos.getClazz():identifiableConfiguration.getFormMap().getMany(Crud.UPDATE)):aClass;
		return aClass;
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}
	
	@Override
	protected Object data(Class<?> aClass) {
		return new Object();
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		super.serve(command, parameter);
		for(AbstractEditManyPage.Listener<?,?> listener : Listener.COLLECTION)
			listener.serve(this,parameter);
	}
	
	
	
	/**/
	
	/**/
	
	public static class ItemCollectionAdapter<ITEM_COLLECTION_ITEM extends AbstractItemCollectionItem<ENTITY>,ENTITY extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends ItemCollectionWebAdapter<ITEM_COLLECTION_ITEM,ENTITY,COLLECTION>  implements Serializable {
		private static final long serialVersionUID = 7806030819027062650L;
		protected BusinessEntityInfos businessEntityInfos;
		
		public ItemCollectionAdapter(BusinessEntityInfos businessEntityInfos) {
			super(null,null,null,null,null);
			this.businessEntityInfos = businessEntityInfos;
		}
		
		@Override
		public Collection<ENTITY> create() {
			Collection<Long> identifiers = WebManager.getInstance().decodeIdentifiersRequestParameter();
			@SuppressWarnings("unchecked")
			Collection<ENTITY> elements = (Collection<ENTITY>) RootBusinessLayer.getInstance().getGenericBusiness()
					.use((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz()).findByIdentifiers(identifiers);
			return elements;
		}
		
		@Override
		public void instanciated(AbstractItemCollection<ITEM_COLLECTION_ITEM, ENTITY,COLLECTION,SelectItem> itemCollection,ITEM_COLLECTION_ITEM item) {
			super.instanciated(itemCollection, item);
			item.setLabel(RootBusinessLayer.getInstance().getFormatterBusiness().format(item.getIdentifiable()));
		}	
	}
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		Collection<Listener<?,?>> COLLECTION = new ArrayList<>();
		
		/**/
		Class<AbstractItemCollectionItem<?>> getFormDataClass(AbstractEditManyPage<?,?> page);
		void serve(AbstractEditManyPage<?,?> page,Object data);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			@Override
			public Class<AbstractItemCollectionItem<?>> getFormDataClass(AbstractEditManyPage<?,?> page) {
				return null;
			}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractEditManyPage<?,?> page = (AbstractEditManyPage<?,?>) bean;
				initialiseEditPage(page);
			}
			
			protected void initialiseEditPage(AbstractEditManyPage<?,?> processPage){}
						
			@Override
			public void serve(AbstractEditManyPage<?,?> page,Object data) {}
			
			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends Listener.Adapter<ENTITY,IDENTIFIER_TYPE> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
					
			}
		}
		
	}

}
