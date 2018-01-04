package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableConsultPage;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;

public class IdentifiableConsultPageFormMaster extends IdentifiableConsultPage.FormMaster implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	public Master setFromRequestParameter(Class<?> aClass,String fieldName){
		if(fieldName.equals("movementAction"))
			return this;
		return super.setFromRequestParameter(aClass, fieldName);
	}
	
	@Override
	protected void __prepare__() {
		super.__prepare__();
		Form.Detail detail = getDetail();
		Class<?> actionOnClass = (Class<?>) getPropertiesMap().getActionOnClass();
		detail.setFieldsObjectFromMaster();
		
		if(ClassHelper.getInstance().isInstanceOf(AbstractCollection.class, actionOnClass)){
			detail.add(AbstractCollection.FIELD_ITEM_CODE_SEPARATOR).addBreak();
			detail.add(AbstractCollection.FIELD_ITEM_AGGREGATION_APPLIED).addBreak();
			
			if(MovementCollection.class.equals(actionOnClass)){
				MovementCollection movementCollection = (MovementCollection) getObject();
				//System.out.println("IdentifiableConsultPageFormMaster.__prepare__() 000 : "+movementCollection.getIncrementAction());
				detail.add(MovementCollection.FIELD_INTERVAL).addBreak();
				detail.add(MovementCollection.FIELD_VALUE).addBreak();
				detail.add(MovementCollection.FIELD_INCREMENT_ACTION).addBreak();
				detail.add(MovementCollection.FIELD_DECREMENT_ACTION).addBreak();
				detail.add(MovementCollection.FIELD_SUPPORT_DOCUMENT_IDENTIFIER).addBreak();
				detail.add(MovementCollection.FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL).addBreak();
				
				DataTable dataTable = instanciateDataTable(Movement.class,null,null,Boolean.TRUE,Movement.FIELD_COLLECTION,Movement.FIELD_PREVIOUS_CUMUL,Movement.FIELD_ACTION);
				dataTable.setOnPrepareAddMenu(Boolean.TRUE);
				dataTable.setOnPrepareAddColumnAction(true);
				dataTable.setOnPrepareAddMenuAddCommand(Boolean.FALSE);
				
				MovementAction movementAction = movementCollection.getIncrementAction();
				dataTable.addMainMenuNode(movementAction.getName(), IconHelper.Icon.FontAwesome.PLUS, UniformResourceLocatorHelper.getInstance()
						.getStringifier(Constant.Action.CREATE, Movement.class).addQueryParameterInstances(movementCollection,movementAction))
						._setLabelPropertyValue(movementAction.getName())
						;
				
				movementAction = movementCollection.getDecrementAction();
				dataTable.addMainMenuNode(movementAction.getName(), IconHelper.Icon.FontAwesome.MINUS, UniformResourceLocatorHelper.getInstance()
						.getStringifier(Constant.Action.CREATE, Movement.class).addQueryParameterInstances(movementCollection,movementAction))
						._setLabelPropertyValue(movementAction.getName())
						;
				
				//System.out.println("IdentifiableConsultPageFormMaster.__prepare__() : "+dataTable.getPropertiesMap().getMaster());
				dataTable.prepare();
				dataTable.build();
				
			}else if(IntervalCollection.class.equals(actionOnClass)){
				detail.add(IntervalCollection.FIELD_LOWEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_HIGHEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT).addBreak();
			}
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
			detail.add(AbstractCollectionItem.FIELD_COLLECTION).addBreak();
			
			if(Movement.class.equals(actionOnClass)){
				Movement movement = (Movement)getObject();
				if(movement.getValue()!=null)
					movement.setValueAbsolute(movement.getValue().abs());
				
				detail.addReadOnly(Movement.FIELD_PREVIOUS_CUMUL).addBreak();
				detail.add(Movement.FIELD_ACTION).addBreak();
				detail.add(Movement.FIELD_VALUE_ABSOLUTE).addBreak();
				detail.addReadOnly(Movement.FIELD_CUMUL).addBreak();
				detail.add(Movement.FIELD_SENDER_OR_RECEIVER_PERSON).addBreak();
				
			}else if(Interval.class.equals(actionOnClass)){
				detail.setFieldsObjectFromMaster(Interval.FIELD_LOW);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"lowest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster(Interval.FIELD_HIGH);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"highest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster();
				detail.add(Interval.FIELD_VALUE).addBreak();
				
			}
		}
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		detail.add(FieldHelper.getInstance().buildPath(Period.FIELD_FROM_DATE)).addBreak();
	}
	
}
