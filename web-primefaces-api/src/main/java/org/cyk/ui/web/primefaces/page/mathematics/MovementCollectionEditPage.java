package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

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
		/*
		@Input @InputNumber private BigDecimal value = BigDecimal.ZERO;
		 
		@Input @InputChoice @InputOneChoice @InputOneCombo private Interval interval;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private MovementAction incrementAction;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private MovementAction decrementAction;
		
		@Input @InputBooleanButton private Boolean supportDocumentIdentifier = Boolean.FALSE;
		*/
		
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_INTERVAL = "interval";
		public static final String FIELD_INCREMENT_ACTION = "incrementAction";
		public static final String FIELD_DECREMENT_ACTION = "decrementAction";
		public static final String FIELD_SUPPORT_DOCUMENT_IDENTIFIER = "supportDocumentIdentifier";
	}
	
	public static class Adapter extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<MovementCollection> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(MovementCollection.class);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(Form.FIELD_CODE,Form.FIELD_NAME,Form.FIELD_ITEM_CODE_SEPARATOR,Form.FIELD_VALUE);
			
			configuration = createFormConfiguration(Crud.UPDATE);
			configuration.addRequiredFieldNames(Form.FIELD_CODE,Form.FIELD_NAME,Form.FIELD_ITEM_CODE_SEPARATOR,Form.FIELD_VALUE);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addRequiredFieldNames(Form.FIELD_CODE,Form.FIELD_NAME);
		}
		
	}

}
