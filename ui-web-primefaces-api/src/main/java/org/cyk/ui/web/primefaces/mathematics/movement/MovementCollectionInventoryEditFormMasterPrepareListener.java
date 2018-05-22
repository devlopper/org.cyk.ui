package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.output.OutputText;

public interface MovementCollectionInventoryEditFormMasterPrepareListener extends MovementCollectionByPartyEditFormMasterPrepareListener<MovementCollectionInventory, MovementCollectionInventoryItem> {
	
	public static class Adapter extends MovementCollectionByPartyEditFormMasterPrepareListener.Adapter.Default<MovementCollectionInventory, MovementCollectionInventoryItem> implements MovementCollectionInventoryEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addFields(FormDetail detail) {
				//((MovementCollectionInventory)detail.getMaster().getObject()).setParty(party);
				super.addFields(detail);
				
			}
			
			@Override
			protected void prepareItemsDataTable(DataTable dataTable) {
				dataTable.getPropertiesMap().setOnPrepareAddColumnAction(Boolean.FALSE);
				super.prepareItemsDataTable(dataTable);
				if(dataTable.getPropertiesMap().getMainMenu() instanceof Component)
					((Component)dataTable.getPropertiesMap().getMainMenu()).getPropertiesMap().setRendered(Boolean.FALSE);
				if(dataTable.getPropertiesMap().getAddCommandComponent() instanceof Component)
					((Component)dataTable.getPropertiesMap().getAddCommandComponent()).getPropertiesMap().setRendered(Boolean.FALSE);
				
				((Component)dataTable.getPropertiesMap().getColumns()).getPropertiesMap().setHeaderRendered(Boolean.FALSE);
			}
			
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
		
		@Override
		public String getLabelValueIdentifier() {
			return "store";
		}
		
	}
	
	public static class ItemsDataTableColumnAdapter extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableColumnAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
			super.addOne(instance, element, source, sourceObject);
			if(element instanceof Column){
				Column column = (Column)element;
				DataTable dataTable = (DataTable) column.getPropertiesMap().getDataTable();
				Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate(column.getPropertyAction());
				if(dataTable.getChoiceValueClassMasterFieldName().equals(column.getPropertiesMap().getFieldName())){
					column.getPropertyInstanciateIfNull(Properties.HEADER, OutputText.class).computeAndSetPropertyValueFromStringIdentifier("product");	
				}if(MovementIdentifiablePages.getFieldMovementCollectionInventoryItemMovementCollectionValue(dataTable).equals(column.getPropertiesMap().getFieldName())){
					column.getPropertyInstanciateIfNull(Properties.HEADER, OutputText.class).computeAndSetPropertyValueFromStringIdentifier("current.stock");
					if(isCreateOrUpdate){
						column.setCellValueType(Cell.ValueType.TEXT);
					}	
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_VALUE).equals(column.getPropertiesMap().getFieldName())){
					column.getPropertyInstanciateIfNull(Properties.HEADER, OutputText.class).computeAndSetPropertyValueFromStringIdentifier("found.stock");
					//if(isCreateOrUpdate)
					//	column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_VALUE_GAP).equals(column.getPropertiesMap().getFieldName())){
					column.getPropertyInstanciateIfNull(Properties.HEADER, OutputText.class).computeAndSetPropertyValueFromStringIdentifier("stock.gap");
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
			DataTable dataTable = (DataTable) column.getPropertiesMap().getDataTable();
			if(ArrayUtils.contains(new String[]{MovementCollectionInventoryItem.FIELD_VALUE},column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{MovementCollectionInventoryItem.FIELD_VALUE_GAP},new String[]{});	
			}else if(MovementIdentifiablePages.getFieldMovementCollectionInventoryItemMovementCollectionValue(dataTable).equals(column.getPropertiesMap().getFieldName())){
				OutputText outputText = cell.getPropertiesMap().get(Properties.VALUE, OutputText.class);
				if(StringHelper.getInstance().isBlank((String)outputText.getPropertiesMap().getValue()))
					outputText.getPropertiesMap().setValue("0");
			}
			return cell;
		}
		
	}
	
}