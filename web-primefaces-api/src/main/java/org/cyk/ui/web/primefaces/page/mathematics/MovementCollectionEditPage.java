package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class MovementCollectionEditPage extends AbstractMovementCollectionEditPage<MovementCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MovementCollection getMovementCollection() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractDefaultForm<MovementCollection> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputNumber private BigDecimal value = BigDecimal.ZERO;
		 
		@Input @InputChoice @InputOneChoice @InputOneCombo private Interval interval;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private MovementAction incrementAction;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private MovementAction decrementAction;
		
		@Input @InputBooleanButton private Boolean supportDocumentIdentifier = Boolean.FALSE;
		
		
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_INTERVAL = "interval";
		public static final String FIELD_INCREMENT_ACTION = "incrementAction";
		public static final String FIELD_DECREMENT_ACTION = "decrementAction";
		public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
	}

}
