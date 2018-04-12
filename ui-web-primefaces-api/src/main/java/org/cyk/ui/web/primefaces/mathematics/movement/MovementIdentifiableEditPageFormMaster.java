package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementsTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementsTransferItemCollectionItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.event.Event;

public class MovementIdentifiableEditPageFormMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static void prepareMovement(final Form.Detail detail,Class<?> aClass){
		Constant.Action action = (Constant.Action)detail._getPropertyAction();
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate(action);
		Movement movement = (Movement)detail.getMaster().getObject();
		movement.setParentActionIsOppositeOfChildAction(Boolean.TRUE);
		//if(movement.getIdentifiables()==null)
		//	movement.setIdentifiables(new IdentifiableRuntimeCollection<AbstractIdentifiable>());
		
		if(Constant.Action.CREATE.equals(action)){
			if(movement.getCollection()!=null)
				movement.setPreviousCumul(movement.getCollection().getValue());
		}
		movement.setValueSettableFromAbsolute(Boolean.TRUE);
		if(movement.getValue()!=null)
			movement.setValueAbsolute(movement.getValue().abs());
		
		detail.addReadOnly(Movement.FIELD___IDENTIFIABLE___PERIOD).addBreak();
		detail.addReadOnly(Movement.FIELD_PREVIOUS_CUMUL).addBreak();
		detail.add(Movement.FIELD_ACTION).addBreak();
		detail.add(Movement.FIELD_VALUE_ABSOLUTE).addBreak();
		detail.addReadOnly(Movement.FIELD_CUMUL).addBreak();
		detail.add(Movement.FIELD_MODE).addBreak();
		detail.add(Movement.FIELD_SENDER_OR_RECEIVER_PARTY).addBreak();
		detail.add(Movement.FIELD_PARENT_ACTION_IS_OPPOSITE_OF_CHILD_ACTION).addBreak();
		
		//addExistencePeriodFromDate();
		
		//detail.add(Movement.FIELD_DESTINATION_MOVEMENT_COLLECTION).addBreak();
		//detail.add(Movement.FIELD_PARENT).addBreak();
		
		//detail.getInputByFieldName(Movement.FIELD_ACTION).getPropertiesMap().setDisabled( ((Movement)getObject()).getAction() != null );
		
		DataTable dataTable = detail.getMaster().instanciateDataTable(Movement.class,isCreateOrUpdate ? MovementCollection.class : null,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
		dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.TRUE);
		dataTable.getPropertiesMap().setMasterFieldName(Movement.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(Movement.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setFormMasterObjectActionOnClassCollectionInstanceFieldName(Movement.FIELD_IDENTIFIABLES);
		
		if(isCreateOrUpdate){
			/* events */
			Event.instanciateOne(detail, AbstractCollectionItem.FIELD_COLLECTION, new String[]{Movement.FIELD_PREVIOUS_CUMUL,Movement.FIELD_CUMUL});					
			Event.instanciateOne(detail, Movement.FIELD_ACTION, new String[]{Movement.FIELD_CUMUL});					
			Event.instanciateOne(detail, Movement.FIELD_VALUE_ABSOLUTE, new String[]{Movement.FIELD_CUMUL});	
			
			dataTable.getPropertiesMap().setCellListener(new DataTable.Cell.Listener.Adapter.Default(){
				private static final long serialVersionUID = 1L;
				public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
					final DataTable.Cell cell = super.instanciateOne(column, row);
					
					if(ArrayUtils.contains(new String[]{Movement.FIELD_VALUE_ABSOLUTE},column.getPropertiesMap().getFieldName())){
						Event.instanciateOne(cell, new String[]{Movement.FIELD_CUMUL},new String[]{});
					}
					return cell;
				}
			});
			/*
			dataTable.getPropertyRowPropertiesPropertyRemoveCommandProperties().setUpdatedFieldNames(Arrays.asList(FieldHelper.getInstance()
					.buildPath(fieldName,SalableProductCollection.FIELD_COST,Cost.FIELD_VALUE)));
			dataTable.getPropertyRowPropertiesPropertyRemoveCommandProperties().setUpdatedColumnFieldNames(Arrays.asList(FieldHelper.getInstance()
					.buildPath(SalableProductCollectionItem.FIELD_COST,Cost.FIELD_VALUE)));
			*/
		}
		
		((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
			private static final long serialVersionUID = 1L;
					
			public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
				DataTable.Row row = (DataTable.Row) element;
				Movement movement = (Movement) row.getPropertiesMap().getValue();
				movement.setParent((Movement) detail.getMaster().getObject());
				movement.setPreviousCumul(movement.getCollection().getValue());
				movement.setValueSettableFromAbsolute(Boolean.TRUE);
				movement.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE);
			}		
			
		});
		
		/*dataTable.addRowsCollectionInstanceListener(DataTable.RowsCollectionInstanceListener new CollectionHelper.Instance.Listener.Adapter<Object>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Object> instance,Object element, Object source, Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				//((DataTable.Row)element)
				debug(element);
			}
		});*/
		
		dataTable.addColumnListener(new CollectionHelper.Instance.Listener.Adapter<Component>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				if(element instanceof DataTable.Column){
					DataTable.Column column = (DataTable.Column)element;
					if(Movement.FIELD_PREVIOUS_CUMUL.equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}else if(Movement.FIELD_CUMUL.equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}
				}
			}
		});
		
		dataTable.prepare();
		dataTable.build();	
	}
	
	public static void prepareMovementsTransferItemCollection(Form.Detail detail,final String fieldName){
		MovementsTransferItemCollection movementsTransferItemCollection = (MovementsTransferItemCollection) (StringHelper.getInstance().isBlank(fieldName) ? detail.getMaster().getObject() 
				: FieldHelper.getInstance().read(detail.getMaster().getObject(), fieldName));
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
		movementsTransferItemCollection.getItems().setSynchonizationEnabled(isCreateOrUpdate);
		movementsTransferItemCollection.getItems().removeAll(); // will be filled up by the data table load call
		
		//DataTable dataTable = detail.getMaster().instanciateDataTable(MovementsTransferItemCollectionItem.class,isCreateOrUpdate ? MovementCollection.class : null,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
		DataTable dataTable = detail.getMaster().instanciateDataTable(MovementsTransferItemCollectionItem.class,MovementCollection.class,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
		
		dataTable.addColumnListener(new CollectionHelper.Instance.Listener.Adapter<Component>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				if(element instanceof DataTable.Column){
					DataTable.Column column = (DataTable.Column)element;
					if(MovementsTransferItemCollectionItem.FIELD_SOURCE_MOVEMENT_COLLECTION.equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}
				}
			}
		});
		
		dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.FALSE);
		dataTable.getPropertiesMap().setMasterFieldName(MovementsTransferItemCollectionItem.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setMaster(movementsTransferItemCollection);
		dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(MovementsTransferItemCollectionItem.FIELD_SOURCE_MOVEMENT_COLLECTION);
		
		dataTable.prepare();
		dataTable.build();	
	}
	
	public static void prepareMovementsTransfer(Form.Detail detail,Class<?> aClass){
		prepareMovementsTransferItemCollection(detail, MovementsTransfer.FIELD_ITEMS);
	}
	
	public static void prepareMovementsTransferAcknowledgement(Form.Detail detail,Class<?> aClass){
		detail.add(MovementsTransferAcknowledgement.FIELD_MOVEMENTS_TRANSFER).addBreak();
		prepareMovementsTransferItemCollection(detail, MovementsTransferAcknowledgement.FIELD_ITEMS);
	}
	
}