package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractCollectionEditPage<COLLECTION extends AbstractIdentifiable> extends AbstractCrudOnePage<COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractCollection<?> getCollection();
	
	@Getter @Setter
	public static abstract class AbstractForm<COLLECTION extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
		protected abstract AbstractCollection<?> getCollection();
		
		/**/
		
		@Getter @Setter
		public static abstract class Default<COLLECTION extends AbstractCollection<?>> extends AbstractForm<COLLECTION> implements Serializable{
			private static final long serialVersionUID = -4741435164709063863L;
			
			@Input @InputText protected String itemCodeSeparator;
			
			protected AbstractCollection<?> getCollection(){
				return identifiable;
			}
			
			public static final String FIELD_ITEM_CODE_SEPARATOR = "itemCodeSeparator";
		}
	}
	
}
