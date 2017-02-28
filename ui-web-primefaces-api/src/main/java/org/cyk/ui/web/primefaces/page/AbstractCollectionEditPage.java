package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractCollectionEditPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable,TYPE extends AbstractItemCollectionItem<ITEM>> extends AbstractCrudOnePage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected ItemCollection<TYPE,ITEM,COLLECTION> itemCollection;
	
	protected abstract AbstractCollection<?> getCollection();
	
	@Getter @Setter
	public static abstract class AbstractForm<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText protected String itemCodeSeparator;
		
		protected abstract AbstractCollection<?> getCollection();
		
		/**/
		
		public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator";
		
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
		
	}

	
}
