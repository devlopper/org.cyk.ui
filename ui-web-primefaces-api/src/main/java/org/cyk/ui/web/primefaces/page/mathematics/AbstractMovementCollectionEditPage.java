package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.web.primefaces.page.AbstractCollectionEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;

@Getter @Setter
public abstract class AbstractMovementCollectionEditPage<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractCollectionEditPage<COLLECTION,ITEM> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract MovementCollection getMovementCollection();
	
	@Override
	protected AbstractCollection<?> getCollection() {
		return getMovementCollection();
	}
	
	@Getter @Setter
	public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractMovementCollectionEditPage<COLLECTION,ITEM> implements Serializable {
		private static final long serialVersionUID = 3274187086682750183L;
		
	}
	
	@Getter @Setter
	public static abstract class Form<COLLECTION extends AbstractIdentifiable,ITEM extends AbstractIdentifiable> extends AbstractCollectionEditPage.AbstractForm<COLLECTION,ITEM> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputNumber protected BigDecimal value = BigDecimal.ZERO;
		 
		@Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete protected Interval interval;
		
		@Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete protected MovementAction incrementAction;
		
		@Input @InputChoice @InputOneChoice @InputChoiceAutoComplete @InputOneAutoComplete protected MovementAction decrementAction;
		
		@Input @InputBooleanButton protected Boolean supportDocumentIdentifier = Boolean.FALSE;
		
		protected abstract MovementCollection getMovementCollection();
		
		@Override
		protected AbstractCollection<?> getCollection() {
			return getMovementCollection();
		}
		
		/**/
		
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_INTERVAL = "interval";
		public static final String FIELD_INCREMENT_ACTION = "incrementAction";
		public static final String FIELD_DECREMENT_ACTION = "decrementAction";
		public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
		
		/**/
		
		@Getter @Setter
		public static abstract class Extends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends Form<COLLECTION,ITEM> implements Serializable{
			private static final long serialVersionUID = -4741435164709063863L;
						
		}
		
		/**/
		
	}
	
	/*@Getter @Setter
	public static abstract class FormExtends<COLLECTION extends AbstractCollection<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractForm.Extends<COLLECTION,ITEM> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;

		
	}*/

}
