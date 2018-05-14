package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.globalidentification.GlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.event.Event;

public class MovementIdentifiablePages implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.class, MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.Adapter.Default.class,Boolean.FALSE);
		ClassHelper.getInstance().map(MovementCollectionInventoryEditFormMasterPrepareListener.class, MovementCollectionInventoryEditFormMasterPrepareListener.Adapter.Default.class,Boolean.FALSE);
		ClassHelper.getInstance().map(MovementGroupEditFormMasterPrepareListener.class, MovementGroupEditFormMasterPrepareListener.Adapter.Default.class,Boolean.FALSE);
	}
	
	public static void prepareMovementCollectionEditFormMaster(final FormDetail detail,Class<?> aClass){
		detail.add(MovementCollection.FIELD_VALUE).addBreak();
		detail.add(MovementCollection.FIELD_IS_CREATE_BUFFER_AUTOMATICALLY).addBreak();
		//IdentifiableEditPageFormMaster.addOwner(detail);
	}
	
	public static void processMovementCollectionColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.add(MovementCollection.FIELD_VALUE);
	}
	
	@SuppressWarnings("unchecked")
	public static void prepareMovementEditFormMaster(final FormDetail detail,Class<?> aClass){
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
		
		DataTable dataTable = detail.getMaster().instanciateDataTable(Movement.class,isCreateOrUpdate ? MovementCollection.class : null,new Cell.Listener.Adapter.Default(),Boolean.TRUE);
		dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.TRUE);
		dataTable.getPropertiesMap().setMasterFieldName(Movement.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(Movement.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setFormMasterObjectActionOnClassCollectionInstanceFieldName(Movement.FIELD_IDENTIFIABLES);
		
		if(isCreateOrUpdate){
			/* events */
			Event.instanciateOne(detail, AbstractCollectionItem.FIELD_COLLECTION, new String[]{Movement.FIELD_PREVIOUS_CUMUL,Movement.FIELD_CUMUL});					
			Event.instanciateOne(detail, Movement.FIELD_ACTION, new String[]{Movement.FIELD_CUMUL});					
			Event.instanciateOne(detail, Movement.FIELD_VALUE_ABSOLUTE, new String[]{Movement.FIELD_CUMUL});	
			
			dataTable.getPropertiesMap().setCellListener(new Cell.Listener.Adapter.Default(){
				private static final long serialVersionUID = 1L;
				public Cell instanciateOne(Column column, Row row) {
					final Cell cell = super.instanciateOne(column, row);
					
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
				Row row = (Row) element;
				Movement movement = (Movement) row.getPropertiesMap().getValue();
				movement.setParent((Movement) detail.getMaster().getObject());
				movement.setPreviousCumul(movement.getCollection().getValue());
				movement.setValueSettableFromAbsolute(Boolean.TRUE);
				movement.__setBirthDateComputedByUser__(Boolean.TRUE).setValueSettableFromAbsolute(Boolean.TRUE);
			}		
			
		});
		
		/*dataTable.addRowsCollectionInstanceListener(RowsCollectionInstanceListener new CollectionHelper.Instance.Listener.Adapter<Object>(){
			private static final long serialVersionUID = 1L;
			@Override
			public void addOne(Instance<Object> instance,Object element, Object source, Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				//((Row)element)
				debug(element);
			}
		});*/
		
		dataTable.addColumnListener(new CollectionHelper.Instance.Listener.Adapter<Component>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				if(element instanceof Column){
					Column column = (Column)element;
					if(Movement.FIELD_PREVIOUS_CUMUL.equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(Cell.ValueType.TEXT);
					}else if(Movement.FIELD_CUMUL.equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(Cell.ValueType.TEXT);
					}
				}
			}
		});
		
		dataTable.prepare();
		dataTable.build();	
	}
	
	public static void processMovementColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.remove(FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		if(dataTable.getPropertiesMap().getMaster() instanceof Movement){
			fieldNames.removeAll(Arrays.asList(Movement.FIELD_COLLECTION,FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)));
			fieldNames.addAll(Arrays.asList(Movement.FIELD_PREVIOUS_CUMUL,Movement.FIELD_VALUE_ABSOLUTE,Movement.FIELD_CUMUL));
		}else {
			fieldNames.add(Movement.FIELD_ACTION);	
			fieldNames.add(Movement.FIELD_VALUE);
			fieldNames.add(Movement.FIELD_CUMUL);
			fieldNames.add(Movement.FIELD_REASON);
			fieldNames.add(FieldHelper.getInstance().buildPath(Movement.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));	
		}
	}
	
	public static void prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(final FormDetail detail,final String fieldName,MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener listener){		
		listener.addFields(detail);		
		listener.addItemsDataTable(detail);
	}
	
	public static void prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(final FormDetail detail,final String fieldName){
		prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(detail, fieldName, ClassHelper.getInstance()
				.instanciateOne(MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.class).setFieldName(fieldName));
	}
	
	public static void processMovementCollectionValuesTransferItemCollectionItemColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.remove(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		if(dataTable.getPropertiesMap().getMaster() instanceof MovementCollectionValuesTransferItemCollection){
			fieldNames.removeAll(Arrays.asList(MovementCollectionValuesTransferItemCollectionItem.FIELD_COLLECTION,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)));
		}else {
			fieldNames.add(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));	
		}
		/*
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE_MOVEMENT_COLLECTION
				,MovementCollection.FIELD_VALUE),MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION_MOVEMENT_COLLECTION,MovementCollectionValuesTransferItemCollectionItem.FIELD_VALUE));
		*/
		
		fieldNames.addAll(Arrays.asList(
			 FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_PREVIOUS_CUMUL)
			,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_CUMUL)
			,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_VALUE_ABSOLUTE)
			
			,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_COLLECTION)
			,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_PREVIOUS_CUMUL)
			,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL)
			));
	}
	
	public static void prepareMovementCollectionValuesTransferEditFormMaster(FormDetail detail,Class<?> aClass){
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
		
		detail.add(MovementCollectionValuesTransfer.FIELD_SENDER).addBreak();
		detail.add(MovementCollectionValuesTransfer.FIELD_RECEIVER).addBreak();
		
		if(isCreateOrUpdate){
			Event.instanciateOne(detail, MovementCollectionValuesTransfer.FIELD_SENDER, new String[]{});
			Event.instanciateOne(detail, MovementCollectionValuesTransfer.FIELD_RECEIVER, new String[]{});
		}
		
		prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(detail, MovementCollectionValuesTransfer.FIELD_ITEMS);
	}
	
	public static void prepareMovementCollectionValuesTransferAcknowledgementEditFormMaster(FormDetail detail,Class<?> aClass){
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
		detail.add(MovementCollectionValuesTransferAcknowledgement.FIELD_TRANSFER).addBreak();
	
		prepareMovementCollectionValuesTransferItemCollectionEditFormMaster(detail, MovementCollectionValuesTransferAcknowledgement.FIELD_ITEMS);
		
		if(isCreateOrUpdate){
			/*Event event = Event.instanciateOne(detail, MovementCollectionValuesTransferAcknowledgement.FIELD_TRANSFER, new String[]{});
			//event.getPropertiesMap().addString(Properties.UPDATE,"@(."+movementCollectionValuesTransferItemCollection.getPropertiesMap().getIdentifierAsStyleClass()+")");
			
			event.getListener().addActionListener(new Event.ActionAdapter(event, detail, null, new LoggingHelper.Message.Builder.Adapter.Default()){
				private static final long serialVersionUID = 1L;

				@Override
				public void __execute__(Action<?, ?> action) {
					super.__execute__(action);
					movementCollectionValuesTransferItemCollection.addManyRow(((MovementCollectionValuesTransferAcknowledgement)detail.getMaster().getObject()).getItems().getItems());
				}
			});*/
		}
	}
	
	public static void prepareMovementCollectionInventoryEditFormMaster(final FormDetail detail,final MovementCollectionInventoryEditFormMasterPrepareListener listener){
		listener.addFields(detail);		
		listener.addItemsDataTable(detail);
	}
	
	public static void prepareMovementCollectionInventoryEditFormMaster(final FormDetail detail){
		prepareMovementCollectionInventoryEditFormMaster(detail, ClassHelper.getInstance().instanciateOne(MovementCollectionInventoryEditFormMasterPrepareListener.class));
	}
	
	public static void processMovementCollectionInventoryColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.removeAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionInventory.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME)
				,MovementCollectionInventory.FIELD_TYPE));
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionInventory.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD
				,Period.FIELD_FROM_DATE)));
	}
	
	public static void processMovementCollectionInventoryItemColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.remove(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		if(dataTable.getPropertiesMap().getMaster() instanceof MovementCollectionInventory){
			fieldNames.removeAll(Arrays.asList(MovementCollectionInventoryItem.FIELD_COLLECTION,FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)));
		}else {
			fieldNames.add(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_EXISTENCE_PERIOD,Period.FIELD_FROM_DATE));	
		}
		/*
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE_MOVEMENT_COLLECTION
				,MovementCollection.FIELD_VALUE),MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION_MOVEMENT_COLLECTION,MovementCollectionValuesTransferItemCollectionItem.FIELD_VALUE));
		*/
		
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_MOVEMENT_COLLECTION,MovementCollection.FIELD_VALUE) 
				, MovementCollectionInventoryItem.FIELD_VALUE,MovementCollectionInventoryItem.FIELD_VALUE_GAP));
	}
	
	public static void prepareMovementGroupEditFormMaster(final FormDetail detail,MovementGroupEditFormMasterPrepareListener listener){
		listener.addFields(detail);		
		listener.addItemsDataTable(detail);
	}
	
	public static void prepareMovementGroupEditFormMaster(final FormDetail detail){
		prepareMovementGroupEditFormMaster(detail, ClassHelper.getInstance().instanciateOne(MovementGroupEditFormMasterPrepareListener.class));
	}
	
	public static void processMovementGroupItemColumnsFieldNames(DataTable dataTable,Collection<String> fieldNames) {
		fieldNames.remove(FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_NAME));
		if(dataTable.getPropertiesMap().getMaster() instanceof MovementGroup){
			fieldNames.removeAll(Arrays.asList(MovementGroupItem.FIELD_COLLECTION,FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_GLOBAL_IDENTIFIER,GlobalIdentifier.FIELD_CODE)));
		}else {
				
		}
		/*
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE_MOVEMENT_COLLECTION
				,MovementCollection.FIELD_VALUE),MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION_MOVEMENT_COLLECTION,MovementCollectionValuesTransferItemCollectionItem.FIELD_VALUE));
		*/
		
		fieldNames.addAll(Arrays.asList(FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_PREVIOUS_CUMUL)
				,FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_VALUE)
				,FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_CUMUL)));
	}
	
	/**/
	
}