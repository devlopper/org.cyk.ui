package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.event.Event;

public interface MovementCollectionInventoryEditFormMasterPrepareListener extends MovementCollectionByPartyEditFormMasterPrepareListener<MovementCollectionInventory, MovementCollectionInventoryItem> {
	
	public static class Adapter extends MovementCollectionByPartyEditFormMasterPrepareListener.Adapter.Default<MovementCollectionInventory, MovementCollectionInventoryItem> implements MovementCollectionInventoryEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Class<MovementCollectionInventory> getCollectionClass() {
				return MovementCollectionInventory.class;
			}
			
			@Override
			public Class<MovementCollectionInventoryItem> getItemClass() {
				return MovementCollectionInventoryItem.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.PartyControlGetAdapter> getPartyControlGetAdapterClass() {
				return MovementCollectionInventoryEditFormMasterPrepareListener.PartyControlGetAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
				return MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableCellAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
				return MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableColumnAdapter.class;
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
				if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_VALUE_GAP).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_MOVEMENT_COLLECTION,MovementCollection.FIELD_VALUE).equals(column.getPropertiesMap().getFieldName())){
					//((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().setValue(StringHelper.getInstance().get("value.actual", new Object[]{}));
					//column.getLabel().getPropertiesMap().setValue(StringHelper.getInstance().get("value.actual", new Object[]{}));
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
			if(ArrayUtils.contains(new String[]{MovementCollectionInventoryItem.FIELD_VALUE},column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{MovementCollectionInventoryItem.FIELD_VALUE_GAP},new String[]{});	
			}
			return cell;
		}
		
	}
	
}