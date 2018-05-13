package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementGroup;
import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.event.Event;

public interface MovementGroupEditFormMasterPrepareListener extends MovementCollectionByPartyEditFormMasterPrepareListener<MovementGroup, MovementGroupItem> {
	
	public static class Adapter extends MovementCollectionByPartyEditFormMasterPrepareListener.Adapter.Default<MovementGroup, MovementGroupItem> implements MovementGroupEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementGroupEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Class<MovementGroup> getCollectionClass() {
				return MovementGroup.class;
			}
			
			@Override
			public Class<MovementGroupItem> getItemClass() {
				return MovementGroupItem.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.PartyControlGetAdapter> getPartyControlGetAdapterClass() {
				return MovementGroupEditFormMasterPrepareListener.PartyControlGetAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
				return MovementGroupEditFormMasterPrepareListener.ItemsDataTableCellAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
				return MovementGroupEditFormMasterPrepareListener.ItemsDataTableColumnAdapter.class;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			protected void prepareItemsDataTable(DataTable dataTable) {
				((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
					private static final long serialVersionUID = 1L;
							
					public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
						MovementGroupItem movementGroupItem = (MovementGroupItem) ((Row) element).getPropertiesMap().getValue();
						InstanceHelper.getInstance().computeChanges(movementGroupItem.getMovement());
					}		
					
				});
				dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_COLLECTION));
				super.prepareItemsDataTable(dataTable);
			}
		}
		
	}
	
	/**/
	
	public static class PartyControlGetAdapter extends MovementCollectionByPartyEditFormMasterPrepareListener.PartyControlGetAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
	public static class ItemsDataTableColumnAdapter extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableColumnAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
			super.addOne(instance, element, source, sourceObject);
			if(element instanceof Column){
				Column column = (Column)element;
				Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)column._getPropertyAction());
				if(FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_CUMUL).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_PREVIOUS_CUMUL).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}
			}
		}
	}
	
	public static class ItemsDataTableCellAdapter extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableCellAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public Cell instanciateOne(Column column, Row row) {
			final Cell cell = super.instanciateOne(column, row);
			if(ArrayUtils.contains(new String[]{FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_VALUE)},column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{FieldHelper.getInstance().buildPath(MovementGroupItem.FIELD_MOVEMENT,Movement.FIELD_CUMUL)},new String[]{});
				
			}
			
			return cell;
		}
		
	}
	
}