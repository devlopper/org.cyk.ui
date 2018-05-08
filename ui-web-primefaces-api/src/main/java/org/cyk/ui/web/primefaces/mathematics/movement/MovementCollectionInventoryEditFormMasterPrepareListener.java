package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventory;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Store;
import org.cyk.utility.common.Action;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.FieldHelper.Constraints;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;

public interface MovementCollectionInventoryEditFormMasterPrepareListener {
	
	void addPartyField(Form.Detail detail);
	Collection<Party> findParties(Form.Detail detail);
	Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass();
	Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass();
	Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass();
	void addItemsDataTable(Form.Detail detail);
	
	public static class Adapter extends AbstractBean implements MovementCollectionInventoryEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addPartyField(Detail detail) {
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
				detail.addByControlGetListener(ClassHelper.getInstance().instanciateOne(getPartyControlGetAdapterClass()),MovementCollectionInventory.FIELD_PARTY).addBreak();
				
				if(isCreateOrUpdate){
					
				}
			}
		
			@Override
			public Collection<Party> findParties(Detail detail) {
				return inject(PartyBusiness.class).findByIdentifiablesByBusinessRoleCode(InstanceHelper.getInstance().get(Store.class), RootConstant.Code.BusinessRole.COMPANY);
			}
			
			@Override
			public void addItemsDataTable(Detail detail) {
				MovementCollectionInventory movementCollectionInventory = (MovementCollectionInventory) detail.getMaster().getObject();
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
				final DataTable movementCollectionInventoryItemCollection = detail.getMaster().instanciateDataTable(MovementCollectionInventoryItem.class,MovementCollection.class,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
				
				if(isCreateOrUpdate){
					/* events */
					
					Event event = Event.instanciateOne(detail, MovementCollectionInventory.FIELD_PARTY, new String[]{});
					
					event.getListener().addActionListener(new Event.ActionAdapter(event, detail, null, new LoggingHelper.Message.Builder.Adapter.Default()){
						private static final long serialVersionUID = 1L;

						@Override
						public void __execute__(Action<?, ?> action) {
							super.__execute__(action);
							movementCollectionInventoryItemCollection.addManyRow(((MovementCollectionInventory)detail.getMaster().getObject()).getItems());
						}
					});
					
					movementCollectionInventoryItemCollection.getPropertiesMap().setCellListener(ClassHelper.getInstance().instanciateOne(getItemsDataTableCellAdapterClass()));
					
				}
				
				movementCollectionInventoryItemCollection.addColumnListener(ClassHelper.getInstance().instanciateOne(getItemsDataTableColumnAdapterClass()));
				
				movementCollectionInventoryItemCollection.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.TRUE);
				movementCollectionInventoryItemCollection.getPropertiesMap().setMasterFieldName(MovementCollectionInventoryItem.FIELD_COLLECTION);
				movementCollectionInventoryItemCollection.getPropertiesMap().setMaster(movementCollectionInventory);
				movementCollectionInventoryItemCollection.getPropertiesMap().setIsAutomaticallyAddChoiceValues(Boolean.FALSE);
				//dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION));
				
				prepareItemsDataTable(movementCollectionInventoryItemCollection);
				movementCollectionInventoryItemCollection.build();	
								
			}
		
			protected void prepareItemsDataTable(DataTable movementCollectionInventoryItemCollection){
				movementCollectionInventoryItemCollection.prepare();
			}
			
			@Override
			public Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass() {
				return PartyControlGetAdapter.class;
			}
			
			@Override
			public Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
				return ItemsDataTableCellAdapter.class;
			}
			
			@Override
			public Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
				return ItemsDataTableColumnAdapter.class;
			}
		}
		
		@Override
		public void addPartyField(Detail detail) {}
		
		@Override
		public Collection<Party> findParties(Detail detail) {
			return null;
		}
		
		@Override
		public Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass() {
			return null;
		}
		
		@Override
		public Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
			return null;
		}
		
		@Override
		public Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
			return null;
		}
		
		@Override
		public void addItemsDataTable(Detail detail) {}

		
	}
	
	/**/
	
	public static class PartyControlGetAdapter extends Control.Listener.Get.Adapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Collection<Party> findInstances(Form.Detail detail){
			return inject(PartyBusiness.class).findByIdentifiablesByBusinessRoleCode(InstanceHelper.getInstance().get(Store.class), RootConstant.Code.BusinessRole.COMPANY);
		}
		
		@Override
		public void processBeforeInitialise(Control control, Detail detail, Object object, Field field,Constraints constraints) {
			super.processBeforeInitialise(control, detail, object, field, constraints);
			if(control instanceof InputChoice)
				((InputChoice<?>)control).setInstances(findInstances(detail));
		}
		
	}
	
	public static class ItemsDataTableColumnAdapter extends CollectionHelper.Instance.Listener.Adapter<Component> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
			super.addOne(instance, element, source, sourceObject);
			if(element instanceof DataTable.Column){
				DataTable.Column column = (DataTable.Column)element;
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)column._getPropertyAction());
				if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_VALUE_GAP).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(DataTable.Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_MOVEMENT_COLLECTION,MovementCollection.FIELD_VALUE).equals(column.getPropertiesMap().getFieldName())){
					//((OutputText)column.getPropertiesMap().getHeader()).getPropertiesMap().setValue(StringHelper.getInstance().get("value.actual", new Object[]{}));
					//column.getLabel().getPropertiesMap().setValue(StringHelper.getInstance().get("value.actual", new Object[]{}));
					if(isCreateOrUpdate)
						column.setCellValueType(DataTable.Cell.ValueType.TEXT);
				}
			}
		}
	}
	
	public static class ItemsDataTableCellAdapter extends DataTable.Cell.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
			final DataTable.Cell cell = super.instanciateOne(column, row);
			if(ArrayUtils.contains(new String[]{MovementCollectionInventoryItem.FIELD_VALUE},column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{MovementCollectionInventoryItem.FIELD_VALUE_GAP},new String[]{});	
			}
			return cell;
		}
		
	}
	
}