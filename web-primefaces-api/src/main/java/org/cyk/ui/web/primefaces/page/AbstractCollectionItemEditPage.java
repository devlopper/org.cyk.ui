package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter
public abstract class AbstractCollectionItemEditPage<ITEM extends AbstractIdentifiable> extends AbstractCrudOnePage<ITEM> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract AbstractCollectionItem<?> getItem();
			
	@Getter @Setter
	protected static abstract class AbstractForm<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractFormModel<ITEM> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull protected COLLECTION collection;
		
		public static final String FIELD_COLLECTION = "collection";
		
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
