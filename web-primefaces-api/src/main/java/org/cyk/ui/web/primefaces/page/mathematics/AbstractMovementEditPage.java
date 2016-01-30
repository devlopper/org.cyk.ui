package org.cyk.ui.web.primefaces.page.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.AbstractIdentifiable;
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

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractMovementEditPage<MOVEMENT extends AbstractIdentifiable> extends AbstractCrudOnePage<MOVEMENT> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Movement getMovement();
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		createAjaxBuilder(AbstractMovementForm.FIELD_COLLECTION).updatedFieldNames(AbstractMovementForm.FIELD_ACTION,AbstractMovementForm.FIELD_VALUE)
		.method(MovementCollection.class,new ListenValueMethod<MovementCollection>() {
			@Override
			public void execute(MovementCollection movementCollection) {
				selectMovementCollection(movementCollection);
			}
		}).build();
		createAjaxBuilder(AbstractMovementForm.FIELD_ACTION).updatedFieldNames(AbstractMovementForm.FIELD_VALUE)
		.method(MovementAction.class,new ListenValueMethod<MovementAction>() {
			@Override
			public void execute(MovementAction movementAction) {
				selectMovementAction(movementAction);
			}
		}).build();
		
		//((Form)form.getData()).setCollection(identifiable.getCollection());
		selectMovementCollection(getMovement().getCollection());
		
	}
	
	protected void selectMovementCollection(MovementCollection movementCollection){
		setChoices(AbstractMovementForm.FIELD_ACTION, Arrays.asList(movementCollection.getIncrementAction(),movementCollection.getDecrementAction()));
		selectMovementAction(null);
	}
	@SuppressWarnings("unchecked")
	protected void selectMovementAction(MovementAction movementAction){
		((AbstractMovementForm<MOVEMENT>)form.getData()).setValue(null);
	}
	
	@Getter @Setter
	protected static abstract class AbstractMovementForm<MOVEMENT extends AbstractIdentifiable> extends AbstractFormModel<MOVEMENT> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull protected MovementCollection collection;
		@Input @InputChoice(load=false) @InputOneChoice @InputOneCombo @NotNull protected MovementAction action;
		@Input @InputNumber @NotNull protected BigDecimal value;
		
		protected abstract Movement getMovement();
		
		@Override
		public void read() {
			super.read();
			collection = getMovement().getCollection();
			action = getMovement().getAction();
			collection = getMovement().getCollection();
			if(getMovement().getValue()!=null)
				value = getMovement().getValue().abs();
		}
		
		@Override
		public void write() {
			super.write();
			getMovement().setCollection(collection);
			getMovement().setAction(action);
			getMovement().setValue(value);
			if(getMovement().getCollection().getDecrementAction().equals(getMovement().getAction()))
				getMovement().setValue(value.negate());
		}
		
		/**/
		
		public static final String FIELD_COLLECTION = "collection";
		public static final String FIELD_ACTION = "action";
		public static final String FIELD_VALUE = "value";
	}

}
