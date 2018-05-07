package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;
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
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.LoggingHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.event.Event;

public interface MovementCollectionInventoryEditFormMasterPrepareListener {
	
	void addPartyField(Form.Detail detail);
	Collection<Party> findParties(Form.Detail detail);
	void addItemsDataTable(Form.Detail detail);
	
	public static class Adapter extends AbstractBean implements MovementCollectionInventoryEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addPartyField(Detail detail) {
				detail.add(MovementCollectionInventory.FIELD_PARTY).addBreak();
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
					
					movementCollectionInventoryItemCollection.getPropertiesMap().setCellListener(new DataTable.Cell.Listener.Adapter.Default(){
						private static final long serialVersionUID = 1L;
						public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
							final DataTable.Cell cell = super.instanciateOne(column, row);
							if(ArrayUtils.contains(new String[]{MovementCollectionInventoryItem.FIELD_VALUE},column.getPropertiesMap().getFieldName())){
								Event.instanciateOne(cell, new String[]{MovementCollectionInventoryItem.FIELD_VALUE_GAP},new String[]{});	
							}
							return cell;
						}
					});
					
				}
				
				movementCollectionInventoryItemCollection.addColumnListener(new CollectionHelper.Instance.Listener.Adapter<Component>(){
					private static final long serialVersionUID = 1L;

					@Override
					public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
						super.addOne(instance, element, source, sourceObject);
						if(element instanceof DataTable.Column){
							DataTable.Column column = (DataTable.Column)element;
							if(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_VALUE_GAP).equals(column.getPropertiesMap().getFieldName())){
								if(isCreateOrUpdate)
									column.setCellValueType(DataTable.Cell.ValueType.TEXT);
							}
						}
					}
				});
				
				movementCollectionInventoryItemCollection.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.TRUE);
				movementCollectionInventoryItemCollection.getPropertiesMap().setMasterFieldName(MovementCollectionInventoryItem.FIELD_COLLECTION);
				movementCollectionInventoryItemCollection.getPropertiesMap().setMaster(movementCollectionInventory);
				movementCollectionInventoryItemCollection.getPropertiesMap().setIsAutomaticallyAddChoiceValues(Boolean.FALSE);
				//dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION));
				
				movementCollectionInventoryItemCollection.prepare();
				movementCollectionInventoryItemCollection.build();	
			}
		}
		
		@Override
		public void addPartyField(Detail detail) {}
		
		@Override
		public Collection<Party> findParties(Detail detail) {
			return null;
		}
		
		@Override
		public void addItemsDataTable(Detail detail) {}
	}
	
	/**/
	
}