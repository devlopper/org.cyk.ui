package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.Input.RendererStrategy;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractCollectionEditPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable,TYPE extends AbstractItemCollectionItem<ITEM>> extends AbstractCrudOnePage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected ItemCollection<TYPE,ITEM,COLLECTION> itemCollection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		itemCollection = instanciateItemCollection();
		if(itemCollection!=null)
			itemCollection.setInputChoice((org.cyk.ui.api.data.collector.control.InputChoice<AbstractIdentifiable, ?, ?, ?, ?, SelectItem>) form.getInputByFieldName(AbstractForm.FIELD_ONE_ITEM_MASTER_SELECTED));
	}
	
	protected ItemCollection<TYPE,ITEM,COLLECTION> instanciateItemCollection(){
		return null;
	}
	
	protected abstract AbstractCollection<?> getCollection();
	
	@Getter @Setter
	public static abstract class AbstractForm<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText protected String itemCodeSeparator;
		@Input(rendererStrategy=RendererStrategy.MANUAL) @InputChoice(nullable=false) @InputOneChoice @InputOneCombo protected AbstractIdentifiable oneItemMasterSelected;
		
		protected abstract AbstractCollection<?> getCollection();
		
		/**/
		
		public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator";
		public static final String FIELD_ONE_ITEM_MASTER_SELECTED = "oneItemMasterSelected";
		
		/**/
		
		@Getter @Setter
		public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractForm<COLLECTION,ITEM> implements Serializable{
			private static final long serialVersionUID = -4741435164709063863L;
			
			protected AbstractCollection<?> getCollection(){
				return identifiable;
			}
			
		}
	}
	
	/**/
	
	@Getter @Setter
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>,TYPE extends AbstractItemCollectionItem<ITEM>> extends AbstractCollectionEditPage<COLLECTION,ITEM,TYPE> implements Serializable {
		private static final long serialVersionUID = 3274187086682750183L;
	
		@Override
		protected AbstractCollection<?> getCollection() {
			return identifiable;
		}
		
		@Override
		protected void afterInitialisation() {
			super.afterInitialisation();
			identifiable.getItems().setSynchonizationEnabled(Boolean.TRUE);
			//itemCollection.setShowAddCommandableAtBottom(Boolean.TRUE);
			//((Commandable)itemCollection.getAddCommandable()).getButton().setImmediate(Boolean.TRUE);
		}
		
		@Override
		public void transfer(UICommand command, Object parameter) throws Exception {
			super.transfer(command, parameter);
			if(form.getSubmitCommandable().getCommand()==command){
				getIdentifiable().getItems().setCollection(itemCollection.getIdentifiables());
			}
		}
		
	}

	
}
