package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.mathematics.MovementCollectionBusiness;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

@Getter @Setter
public abstract class AbstractMovementEditPage<MOVEMENT extends AbstractIdentifiable> extends AbstractCollectionItemEditPage<MOVEMENT> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static Boolean SHOW_COLLECTION_FIELD = Boolean.TRUE;
	public static Boolean SHOW_ACTION_FIELD = Boolean.TRUE;
	
	protected MovementAction movementAction;
	
	protected abstract Movement getMovement();
	
	@Override
	protected AbstractCollectionItem<?> getItem() {
		return getMovement();
	}
	
	protected BigDecimal getCurrentTotal(){
		return getMovement().getCollection().getValue();
	}
	
	protected BigDecimal computeNextTotal(BigDecimal increment){
		return inject(MovementCollectionBusiness.class)
				.computeValue(getMovement().getCollection(), (MovementAction) form.findInputByFieldName(AbstractMovementForm.FIELD_ACTION).getValue(), increment);
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		movementAction  = webManager.getIdentifiableFromRequestParameter(MovementAction.class, Boolean.TRUE);
		/*form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 448634403892908003L;

			@Override
			public Boolean build(Field field) {
				if(field.getName().equals(AbstractMovementForm.FIELD_COLLECTION))
					return Boolean.TRUE.equals(showCollectionField());
				if(field.getName().equals(AbstractMovementForm.FIELD_ACTION))
					return Boolean.TRUE.equals(showActionField());
				return super.build(field);
			}
		});*/
	}
	
	protected Boolean showCollectionField(){
		return SHOW_COLLECTION_FIELD;
	}
	protected Boolean showActionField(){
		return SHOW_ACTION_FIELD;
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		/*
		if(Boolean.TRUE.equals(showCollectionField()))
			createAjaxBuilder(AbstractMovementForm.FIELD_COLLECTION).updatedFieldNames(AbstractMovementForm.FIELD_CURRENT_TOTAL,AbstractMovementForm.FIELD_ACTION
					,AbstractMovementForm.FIELD_VALUE,AbstractMovementForm.FIELD_NEXT_TOTAL)
			.method(MovementCollection.class,new ListenValueMethod<MovementCollection>() {
				@Override
				public void execute(MovementCollection movementCollection) {
					selectMovementCollection(movementCollection);
				}
			}).build();
		if(Boolean.TRUE.equals(showActionField()))
			createAjaxBuilder(AbstractMovementForm.FIELD_ACTION).updatedFieldNames(AbstractMovementForm.FIELD_VALUE,AbstractMovementForm.FIELD_NEXT_TOTAL)
			.method(MovementAction.class,new ListenValueMethod<MovementAction>() {
				@Override
				public void execute(MovementAction movementAction) {
					selectMovementAction(movementAction);
				}
			}).build();
		
		createAjaxBuilder(AbstractMovementForm.FIELD_VALUE).updatedFieldNames(AbstractMovementForm.FIELD_NEXT_TOTAL)
		.method(BigDecimal.class,new ListenValueMethod<BigDecimal>() {
			@Override
			public void execute(BigDecimal value) {
				updateNextTotal(value);
			}
		}).build();
		selectMovementCollection(getMovement().getCollection());
		*/
		
		//form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputOneChoice.class, Form.FIELD_ACTION).setValue(getCashRegisterMovement().getMovement().getAction());
		
		if(movementAction==null){
			org.cyk.ui.api.data.collector.control.InputNumber<?,?,?,?,?> inputNumber = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_VALUE);
			if(inputNumber!=null)
				inputNumber.setMinimumToInfinite();
		}
		//form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_CURRENT_TOTAL).setMinimumToInfinite();
		//form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_NEXT_TOTAL).setMinimumToInfinite();
	}
	
	protected void selectMovementCollection(MovementCollection movementCollection){
		setFieldValue(AbstractMovementForm.FIELD_COLLECTION, movementCollection);
		setChoices(AbstractMovementForm.FIELD_ACTION, movementCollection==null?null
				:Arrays.asList(movementCollection.getIncrementAction(),movementCollection.getDecrementAction()));
		setFieldValue(AbstractMovementForm.FIELD_CURRENT_TOTAL, movementCollection==null?null:getCurrentTotal());
		Movement movement = getMovement();
		if(movement!=null && movementAction!=null)
			movement.setAction(movementAction);
		selectMovementAction(movement==null?movementAction:movement.getAction());
	}
	protected void selectMovementAction(MovementAction movementAction){
		setFieldValue(AbstractMovementForm.FIELD_ACTION, movementAction);
		form.findInputByFieldName(AbstractMovementForm.FIELD_ACTION).setDisabled(movementAction!=null);
		updateNextTotal(null);
	}
	
	protected void updateNextTotal(BigDecimal increment){
		setFieldValue(AbstractMovementForm.FIELD_VALUE, increment);
		setFieldValue(AbstractMovementForm.FIELD_NEXT_TOTAL, increment==null?null:computeNextTotal(increment));
	}
	
	@Getter @Setter
	protected static abstract class AbstractMovementForm<MOVEMENT extends AbstractIdentifiable> extends AbstractCollectionItemEditPage.AbstractForm<MovementCollection,MOVEMENT> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		//@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull protected MovementCollection collection;
		@Input(readOnly=true,disabled=true) @InputNumber @NotNull protected BigDecimal currentTotal;
		/*@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull*/ protected MovementAction action;
		@Input @InputNumber @NotNull protected BigDecimal value;
		@Input(readOnly=true,disabled=true) @InputNumber @NotNull protected BigDecimal nextTotal;
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL) protected PeriodFormModel existencePeriod = new PeriodFormModel();
		
		protected abstract Movement getMovement();
		
		@Override
		public void read() {
			super.read();
			//collection = getMovement().getCollection();
			action = getMovement().getAction();
			if(getMovement().getValue()!=null)
				value = getMovement().getValue().abs();
			existencePeriod.set(getMovement().getExistencePeriod());
		}
		
		@Override
		public void write() {
			super.write();
			//getMovement().setCollection(collection);
			getMovement().setAction(action);
			getMovement().setValue(value);
			getMovement().setBirthDate(existencePeriod.getFromDate());
			if(getMovement().getCollection().getDecrementAction()!=null && getMovement().getCollection().getDecrementAction().equals(getMovement().getAction()))
				getMovement().setValue(value.negate());
		}
		
		/**/
		
		//public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_CURRENT_TOTAL = "currentTotal";
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_NEXT_TOTAL = "nextTotal";
		public static final String FIELD_EXISTENCE_PERIOD = "existencePeriod";
	}

}
