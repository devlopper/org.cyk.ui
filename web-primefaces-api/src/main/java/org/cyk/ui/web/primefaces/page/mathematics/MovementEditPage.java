package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class MovementEditPage extends AbstractCrudOnePage<Movement> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		createAjaxBuilder(Form.FIELD_COLLECTION).updatedFieldNames(Form.FIELD_ACTION,Form.FIELD_VALUE)
		.method(MovementCollection.class,new ListenValueMethod<MovementCollection>() {
			@Override
			public void execute(MovementCollection movementCollection) {
				selectMovementCollection(movementCollection);
			}
		}).build();
		createAjaxBuilder(Form.FIELD_ACTION).updatedFieldNames(Form.FIELD_VALUE)
		.method(MovementAction.class,new ListenValueMethod<MovementAction>() {
			@Override
			public void execute(MovementAction movementAction) {
				selectMovementAction(movementAction);
			}
		}).build();
		
		//((Form)form.getData()).setCollection(identifiable.getCollection());
		selectMovementCollection(identifiable.getCollection());
		
	}
	
	@Override
	protected Movement instanciateIdentifiable() {
		Long movementCollectionIdentifier = requestParameterLong(MovementCollection.class);
		if(movementCollectionIdentifier==null)
			return super.instanciateIdentifiable();
		return RootBusinessLayer.getInstance().getMovementBusiness().instanciate(RootBusinessLayer.getInstance()
				.getMovementCollectionBusiness().find(movementCollectionIdentifier), Boolean.TRUE);
	}
	
	private void selectMovementCollection(MovementCollection movementCollection){
		setChoices(Form.FIELD_ACTION, Arrays.asList(movementCollection.getIncrementAction(),movementCollection.getDecrementAction()));
		selectMovementAction(null);
	}
	private void selectMovementAction(MovementAction movementAction){
		((Form)form.getData()).setValue(null);
	}
	
	@Override
	protected void create() {
		super.create();
		//debug(identifiable);
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<Movement> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private MovementCollection collection;
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull private MovementAction action;
		@Input @InputNumber @NotNull private BigDecimal value;
		
		@Override
		public void read() {
			super.read();
			collection = identifiable.getCollection();
			if(identifiable.getValue()!=null)
				value = identifiable.getValue().abs();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.setCollection(collection);
			if(identifiable.getCollection().getDecrementAction().equals(identifiable.getAction()))
				identifiable.setValue(value.negate());
		}
		
		/**/
		
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_VALUE = "value";
	}

}
