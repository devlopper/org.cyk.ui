package org.cyk.ui.web.primefaces.page.value;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.value.ValueCollectionItemBusiness;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueCollectionItem;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.api.model.value.ValueFormModel;
import org.cyk.ui.web.primefaces.Commandable;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Named @ViewScoped
public class ValueCollectionEditPage extends AbstractCollectionEditPage.Extends<ValueCollection,ValueCollectionItem,ValueCollectionEditPage.ValueCollectionItemItem> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		identifiable.getItems().setSynchonizationEnabled(Boolean.TRUE);
		itemCollection = createItemCollection(ValueCollectionItemItem.class, ValueCollectionItem.class,identifiable 
				,new org.cyk.ui.web.primefaces.ItemCollectionAdapter<ValueCollectionItemItem,ValueCollectionItem,ValueCollection>(identifiable,crud,form){
					private static final long serialVersionUID = 1L;
			
					@Override
					public Collection<ValueCollectionItem> load() {
						getCollection().getItems().setCollection(inject(ValueCollectionItemBusiness.class).findByCollection(getCollection()));
						return getCollection().getItems().getCollection();
					}
					
					@Override
					public ValueCollectionItem instanciate(AbstractItemCollection<ValueCollectionItemItem, ValueCollectionItem, ValueCollection, SelectItem> itemCollection) {
						ValueCollectionItem valueCollectionItem = inject(ValueCollectionItemBusiness.class).instanciateOne();
						valueCollectionItem.setCollection(collection);
						return valueCollectionItem;
					}
					
					@Override
					public Boolean isShowAddButton() {
						return Boolean.TRUE;
					}
					
					
					
		});
		
		itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
		((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
	}
	
	@Getter @Setter
	public static class Form extends AbstractForm.Extends<ValueCollection,ValueCollectionItem> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
	}

	@Getter @Setter
	public static class ValueCollectionItemItem extends AbstractItemCollectionItem<ValueCollectionItem> implements Serializable {
		private static final long serialVersionUID = 3828481396841243726L;

		private ValueFormModel value = new ValueFormModel();
		
	}
}
