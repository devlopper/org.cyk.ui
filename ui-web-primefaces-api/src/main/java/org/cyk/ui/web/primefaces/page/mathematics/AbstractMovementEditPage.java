package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractCollectionItemEditPage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMovementEditPage<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCollectionItemEditPage<ITEM,COLLECTION> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected MovementAction movementAction;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		movementAction  = webManager.getIdentifiableFromRequestParameter(MovementAction.class, Boolean.TRUE);
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 448634403892908003L;

			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Object data, Field field) {
				if( field.getName().equals(AbstractMovementForm.FIELD_COLLECTION) )
					return inject(LanguageBusiness.class).findClassLabelText(getCollectionClass());
				return super.fiedLabel(controlSet, data, field);
			}
		});
	}
	
	protected abstract Movement getMovement();
	
	@Override
	protected AbstractCollectionItem<?> getItem() {
		return getMovement();
	}
	
	protected BigDecimal getCurrentTotal(){
		Movement movement = getMovement();
		if(movement==null || movement.getCollection()==null)
			return null;
		return movement.getCollection().getValue();
	}
	
	protected BigDecimal getNextTotal(BigDecimal increment){
		Movement movement = getMovement();
		if(movement==null || movement.getCollection()==null)
			return null;
		//return inject(MovementCollectionBusiness.class)
		//		.computeValue(getMovement().getCollection(), (MovementAction) form.getInputByFieldName(AbstractMovementForm.FIELD_ACTION).getValue(), increment);
		return null;
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		if(Crud.isCreateOrUpdate(crud)){
			createAjaxBuilder(AbstractMovementForm.FIELD_COLLECTION).updatedFieldNames(AbstractMovementForm.FIELD_CURRENT_TOTAL,AbstractMovementForm.FIELD_ACTION
					,AbstractMovementForm.FIELD_VALUE,AbstractMovementForm.FIELD_NEXT_TOTAL)
			.method(getCollectionClass(),new ListenValueMethod<COLLECTION>() {
				@Override
				public void execute(COLLECTION collection) {
					selectCollection(collection);
				}
			}).build();
			
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
					updateNextTotal(value.subtract(getMovement()==null || getMovement().getValue()==null?BigDecimal.ZERO:getMovement().getValue()));
				}
			}).build();
			
			Movement movement = getMovement();
			if(movement!=null){
				//selectCollection(getMovement().getCollection());
				if(Crud.UPDATE.equals(crud)){
					setFieldValue(AbstractMovementForm.FIELD_VALUE, movement.getValue());
					//if(movement.getCollection()!=null)
					setFieldValue(AbstractMovementForm.FIELD_NEXT_TOTAL, /*movement.getCollection().getValue()*/getNextTotal(BigDecimal.ZERO));
				}
				//if(getMovement().getCollection()!=null && getMovement().getCollection().getDecrementAction()!=null)
				//	setChoices(AbstractMovementForm.FIELD_ACTION, Arrays.asList(getMovement().getCollection().getDecrementAction(),getMovement().getCollection().getIncrementAction()));
				//form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputOneChoice.class, AbstractMovementForm.FIELD_ACTION).setValue(getMovement().getAction());
			}
			
			if(movementAction==null){
				org.cyk.ui.api.data.collector.control.InputNumber<?,?,?,?,?> inputNumber = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_VALUE);
				if(inputNumber!=null)
					inputNumber.setMinimumToInfinite();
			}
			form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_CURRENT_TOTAL).setMinimumToInfinite();
			form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputNumber.class, AbstractMovementForm.FIELD_NEXT_TOTAL).setMinimumToInfinite();
			
			
		}
	}
	
	protected abstract MovementCollection getMovementCollection(COLLECTION collection);
	
	@Override
	protected void selectCollection(COLLECTION collection){
		super.selectCollection(collection);
		Movement movement = getMovement();
		if(movement==null)
			return;
		movement.setCollection(getMovementCollection(collection));
		
		//setChoices(AbstractMovementForm.FIELD_ACTION, movement.getCollection()==null || movement.getCollection().getIncrementAction()==null?null
		//		:Arrays.asList(movement.getCollection().getIncrementAction(),movement.getCollection().getDecrementAction()));
		updateCurrentTotal();
	
		if(movement!=null && movementAction!=null)
			movement.setAction(movementAction);
		selectMovementAction(movement==null?movementAction:movement.getAction());
	}
	
	protected void selectMovementAction(MovementAction movementAction){
		setFieldValue(AbstractMovementForm.FIELD_ACTION, movementAction);
		form.getInputByFieldName(AbstractMovementForm.FIELD_ACTION).setDisabled(movementAction!=null);
		updateNextTotal(null);
	}
	
	protected void updateCurrentTotal(){
		setFieldValue(AbstractMovementForm.FIELD_CURRENT_TOTAL, getCurrentTotal());
	}
	
	protected void updateNextTotal(BigDecimal increment){
		setFieldValue(AbstractMovementForm.FIELD_VALUE, increment);
		setFieldValue(AbstractMovementForm.FIELD_NEXT_TOTAL, getNextTotal(increment));
	}
	
	public static abstract class Extends<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractMovementEditPage<ITEM,COLLECTION> implements Serializable {
		private static final long serialVersionUID = 1L;
				
	}
	
	@Getter @Setter
	protected static abstract class AbstractMovementForm<ITEM extends AbstractIdentifiable,COLLECTION extends AbstractIdentifiable> extends AbstractCollectionItemEditPage.AbstractForm<ITEM,COLLECTION> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input(readOnly=true,disabled=true) @InputNumber @NotNull protected BigDecimal currentTotal;
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull protected MovementAction action;
		@Input @InputNumber @NotNull protected BigDecimal value;
		@Input(readOnly=true,disabled=true) @InputNumber @NotNull protected BigDecimal nextTotal;
		//@Input @InputCalendar(format=Format.DATETIME_SHORT) @NotNull protected Date date;
		
		protected abstract Movement getMovement();
		
		@Override
		public void read() {
			super.read();
			Movement movement = getMovement();
			if(movement==null){
				
			}else{
				action = movement.getAction();
				if(movement.getValue()!=null)
					value = movement.getValue().abs();
				//date = getMovement().getBirthDate();	
			}
			
		}
		
		@Override
		public void write() {
			super.write();
			getMovement().setAction(action);
			getMovement().setValue(value);
			//getMovement().setBirthDate(date);
			//if(getMovement().getCollection().getDecrementAction()!=null && getMovement().getCollection().getDecrementAction().equals(getMovement().getAction()))
			//	getMovement().setValue(value.negate());
		}
		
		/**/
		
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_CURRENT_TOTAL = "currentTotal";
		public static final String FIELD_VALUE = "value";
		public static final String FIELD_NEXT_TOTAL = "nextTotal";
		//public static final String FIELD_DATE = "date";
		
		/**/
		
		public static abstract class AbstractDefaultMovementForm<ITEM extends AbstractCollectionItem<COLLECTION>,COLLECTION extends AbstractCollection<ITEM>> extends AbstractMovementForm<ITEM,COLLECTION> implements Serializable {
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
