package org.cyk.ui.web.primefaces;

import java.io.Serializable;
import java.util.Arrays;

import org.cyk.system.root.model.AbstractCollection;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.Rud;
import org.cyk.system.root.model.geography.GlobalPosition;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.globalidentification.AbstractJoinGlobalIdentifier;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementAction;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionType;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.store.Store;
import org.cyk.system.root.model.time.IdentifiablePeriod;
import org.cyk.system.root.model.time.IdentifiablePeriodCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.time.Period;
import org.cyk.system.root.model.userinterface.style.CascadeStyleSheet;
import org.cyk.system.root.model.value.LongValue;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementIdentifiableEditPageFormMaster;
import org.cyk.ui.web.primefaces.resources.page.controlpanel.IdentifiableConsultPage;
import org.cyk.ui.web.primefaces.store.StoreIdentifiableEditPageFormMaster;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.IconHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.cyk.utility.common.userinterface.container.Form;

public class IdentifiableConsultPageFormMaster extends IdentifiableConsultPage.FormMaster implements Serializable {
	private static final long serialVersionUID = -6211058744595898478L;
	
	public Master setFromRequestParameter(Class<?> aClass,String fieldName){
		/*if(MovementCollection.class.equals(getPropertiesMap().getActionOnClass())){
			
		}else if(Movement.class.equals(getPropertiesMap().getActionOnClass())){
			if(MovementCollection.class.equals(aClass))
				fieldName = Movement.FIELD_COLLECTION;
			else if(MovementAction.class.equals(aClass))
				fieldName = Movement.FIELD_ACTION;
		}*/
		//if(fieldName.equals("movementAction"))
		//	return this;
		return super.setFromRequestParameter(aClass, fieldName);
	}
	
	@Override
	protected void ____add____() {
		if(Person.class.equals(getPropertiesMap().getActionOnClass())){
			
		}else
			super.____add____();
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
				detail.add(MovementCollection.FIELD_VALUE).addBreak();
				
				addDataTableMovement(movementCollection);
				addDataTableJoinGlobalIdentifier(PartyIdentifiableGlobalIdentifier.class);
				addDataTableJoinGlobalIdentifier(IdentifiablePeriodCollectionIdentifiableGlobalIdentifier.class);
				
			}else if(MovementCollectionValuesTransferItemCollection.class.equals(actionOnClass)){
				MovementIdentifiableEditPageFormMaster.prepareMovementCollectionValuesTransferItemCollection(detail,null);
			}else if(IntervalCollection.class.equals(actionOnClass)){
				detail.add(IntervalCollection.FIELD_LOWEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_HIGHEST_VALUE).addBreak();
				detail.add(IntervalCollection.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT).addBreak();
			}
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractCollectionItem.class, actionOnClass)){
			detail.add(AbstractCollectionItem.FIELD_COLLECTION).addBreak();
			
			if(Movement.class.equals(actionOnClass)){
				Movement movement = (Movement)getObject();
				if(movement.getValue()!=null){
					movement.setValueAbsolute(movement.getValue().abs());
					//movement.set__identifiablePeriod__(inject(IdentifiablePeriodBusiness.class).findby);
				}
				
				detail.add(Movement.FIELD___IDENTIFIABLE___PERIOD).addBreak();
				detail.add(Movement.FIELD_PREVIOUS_CUMUL).addBreak();
				detail.add(Movement.FIELD_ACTION).addBreak();
				detail.add(Movement.FIELD_VALUE_ABSOLUTE).addBreak();
				detail.add(Movement.FIELD_CUMUL).addBreak();
				detail.add(Movement.FIELD_SENDER_OR_RECEIVER_PARTY).addBreak();
				
			}else if(Interval.class.equals(actionOnClass)){
				detail.setFieldsObjectFromMaster(Interval.FIELD_LOW);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"lowest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster(Interval.FIELD_HIGH);
				detail.addFieldName(IntervalExtremity.FIELD_VALUE,"highest.value");
				detail.add(IntervalExtremity.FIELD_EXCLUDED).addBreak();
				detail.setFieldsObjectFromMaster();
				detail.add(Interval.FIELD_VALUE).addBreak();
				
			}else if(IdentifiablePeriod.class.equals(actionOnClass)){
				addExistencePeriodFromDate();
				addExistencePeriodToDate();
				addClosed();
			}
		}else if(Locality.class.equals(actionOnClass)){
			detail.add(Locality.FIELD_RESIDENT_NAME).addBreak();
			detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_POSITION);
			detail.add(GlobalPosition.FIELD_LATITUDE).addBreak();
			detail.add(GlobalPosition.FIELD_LONGITUDE).addBreak();
			detail.add(GlobalPosition.FIELD_ALTITUDE).addBreak();
			detail.setFieldsObjectFromMaster(Locality.FIELD_GLOBAL_IDENTIFIER);
			detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
		}else if(MovementCollectionValuesTransfer.class.equals(actionOnClass)){
			MovementIdentifiableEditPageFormMaster.prepareMovementCollectionValuesTransfer(detail, actionOnClass);
		}else if(MovementCollectionValuesTransferAcknowledgement.class.equals(actionOnClass)){
			MovementIdentifiableEditPageFormMaster.prepareMovementCollectionValuesTransferAcknowledgement(detail, actionOnClass);
		}else if(MovementCollectionType.class.equals(actionOnClass)){
			detail.add(MovementCollectionType.FIELD_INTERVAL).addBreak();
			detail.add(MovementCollectionType.FIELD_INCREMENT_ACTION).addBreak();
			detail.add(MovementCollectionType.FIELD_DECREMENT_ACTION).addBreak();
			detail.add(MovementCollectionType.FIELD_IDENTIFIABLE_PERIOD_COLLECTION_TYPE).addBreak(); 
			detail.add(MovementCollectionType.FIELD_SUPPORT_DOCUMENT_IDENTIFIER).addBreak();
			detail.add(MovementCollectionType.FIELD_DOCUMENT_IDENTIFIER_COUNT_INTERVAL).addBreak();
		}else if(ClassHelper.getInstance().isHierarchy(actionOnClass)){
			if(Store.class.equals(actionOnClass)){
				StoreIdentifiableEditPageFormMaster.prepareStore(detail);
			}
		}else if(GlobalIdentifier.class.equals(actionOnClass)){
			detail.add(GlobalIdentifier.FIELD_IMAGE).addBreak();
			detail.add(GlobalIdentifier.FIELD_ABBREVIATION).addBreak();
			detail.add(GlobalIdentifier.FIELD_ACTIVATED).addBreak();
			detail.add(GlobalIdentifier.FIELD_CLOSED).addBreak();
			detail.add(GlobalIdentifier.FIELD_CONSTANT).addBreak();
			detail.add(GlobalIdentifier.FIELD_CREATION_DATE).addBreak();
			detail.add(GlobalIdentifier.FIELD_DEFAULTED).addBreak();
			detail.add(GlobalIdentifier.FIELD_DERIVED).addBreak();
			detail.add(GlobalIdentifier.FIELD_DESCRIPTION).addBreak();
			detail.add(GlobalIdentifier.FIELD_EXTERNAL_IDENTIFIER).addBreak();
			detail.add(GlobalIdentifier.FIELD_INITIALIZED).addBreak();
			detail.add(GlobalIdentifier.FIELD_MALE).addBreak();
			detail.add(GlobalIdentifier.FIELD_ORDER_NUMBER).addBreak();
			detail.add(GlobalIdentifier.FIELD_OTHER_DETAILS).addBreak();
			detail.add(GlobalIdentifier.FIELD_REQUIRED).addBreak();
			detail.add(GlobalIdentifier.FIELD_USABLE).addBreak();
			detail.add(GlobalIdentifier.FIELD_WEIGHT).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
			detail.add(Period.FIELD_FROM_DATE).addBreak();
			detail.add(Period.FIELD_TO_DATE).addBreak();
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_NUMBER_OF_MILLISECOND);
			detail.add(LongValue.FIELD_PREFERRED_PROPERTY).addBreak();
			detail.add(LongValue.FIELD_USER).addBreak();
			detail.add(LongValue.FIELD_SYSTEM).addBreak();
			detail.add(LongValue.FIELD_GAP).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_CASCADE_STYLE_SHEET);
			detail.add(CascadeStyleSheet.FIELD_INLINE).addBreak();
			detail.add(CascadeStyleSheet.FIELD_CLASS).addBreak();
			detail.add(CascadeStyleSheet.FIELD_UNIQUE_CLASS).addBreak();
			
			detail.setFieldsObjectFromMaster(GlobalIdentifier.FIELD_RUD);
			detail.add(Rud.FIELD_READABLE).addBreak();
			detail.add(Rud.FIELD_UPDATABLE).addBreak();
			detail.add(Rud.FIELD_DELETABLE).addBreak();
			
			/*detail.add(GlobalIdentifier.FIELD_).addBreak();
			detail.add(GlobalIdentifier.FIELD_).addBreak();
			*/
		}else if(Person.class.equals(actionOnClass)){
			IdentifiableEditPageFormMaster.preparePerson(detail);
		}else if(ClassHelper.getInstance().isInstanceOf(AbstractActor.class, actionOnClass)){
			IdentifiableEditPageFormMaster.prepareActor(detail);
		}
		
		
		/*
		detail.setFieldsObjectFromMaster(AbstractIdentifiable.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD);
		detail.add(FieldHelper.getInstance().buildPath(Period.FIELD_FROM_DATE)).addBreak();
		*/
	}
	
	protected void addDataTableJoinGlobalIdentifier(Class<? extends AbstractJoinGlobalIdentifier> aClass){
		DataTable dataTable = instanciateDataTable(aClass,null,null,Boolean.TRUE);
		dataTable.getPropertiesMap().setAddCommandQueryKeyValues(Arrays.asList(UniformResourceLocatorHelper.QueryParameter.Name.CLASS_IDENTIFIABLE_GLOBAL_IDENTIFIER
				,dataTable.getPropertiesMap().getMaster().getClass()
				));
		dataTable.prepare();
		dataTable.build();
	}
	
	protected void addDataTableMovement(MovementCollection movementCollection){
		DataTable dataTable = instanciateDataTable(Movement.class,null,null,Boolean.TRUE);
		dataTable.getPropertiesMap().setMaster(movementCollection);
		if(movementCollection.getType().getIncrementAction()!=null || movementCollection.getType().getDecrementAction()!=null){
			dataTable.getPropertiesMap().setOnPrepareAddMenuAddCommand(Boolean.FALSE);
			if(movementCollection.getType().getIncrementAction()!=null)
				addDataTableMovementAction(dataTable, movementCollection, movementCollection.getType().getIncrementAction(), IconHelper.Icon.FontAwesome.ARROW_UP);
			if(movementCollection.getType().getDecrementAction()!=null)
				addDataTableMovementAction(dataTable, movementCollection, movementCollection.getType().getDecrementAction(), IconHelper.Icon.FontAwesome.ARROW_DOWN);
		}
		
		dataTable.prepare();
		dataTable.build();
	}
	
	protected void addDataTableMovementAction(DataTable dataTable,MovementCollection movementCollection,MovementAction movementAction,Object icon){
		if(movementAction!=null){
			MenuNode menuNode = dataTable.addMainMenuNode(movementAction.getName(), icon, UniformResourceLocatorHelper.getInstance()
					.getStringifier(Constant.Action.CREATE, Movement.class).addQueryParameterInstances(movementCollection,movementAction));
			menuNode._setLabelPropertyValue(movementAction.getName());
			menuNode._setPropertyTitleFromLabel();
		}
	}
	
	/**/
	
	protected void addExistencePeriodFromDate(){
		IdentifiableEditPageFormMaster.addExistencePeriodFromDate(getDetail());
	}
	
	protected void addExistencePeriodToDate(){
		IdentifiableEditPageFormMaster.addExistencePeriodToDate(getDetail());
	}
	
	protected void addOwner(){
		IdentifiableEditPageFormMaster.addOwner(getDetail());
	}
	
	protected void addClosed(){
		IdentifiableEditPageFormMaster.addClosed(getDetail());
	}
	
	protected void addImage(){
		IdentifiableEditPageFormMaster.addImage(getDetail());
	}
	
	protected void addDescription(){
		IdentifiableEditPageFormMaster.addDescription(getDetail());
	}
}
