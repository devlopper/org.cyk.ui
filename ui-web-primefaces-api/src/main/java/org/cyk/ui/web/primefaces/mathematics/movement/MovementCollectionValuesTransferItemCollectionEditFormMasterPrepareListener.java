package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Store;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
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

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener extends MovementCollectionByPartyEditFormMasterPrepareListener<MovementCollectionValuesTransferItemCollection, MovementCollectionValuesTransferItemCollectionItem> {
	
	MovementCollection getDestinationMovementCollection(FormDetail detail,EndPoint sender,EndPoint receiver,MovementCollection source,AbstractIdentifiable sourceIdentifiableJoined);
	AbstractIdentifiable getSourceIdentifiableJoined(EndPoint sender,EndPoint receiver,MovementCollection source,MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier);
	
	Boolean getIsSourceMovementCollectionMustBeBuffer();
	MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsSourceMovementCollectionMustBeBuffer(Boolean isSourceMovementCollectionMustBeBuffer);
	
	Boolean getIsDetinationMovementCollectionMustBeBuffer();
	MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsDetinationMovementCollectionMustBeBuffer(Boolean isDetinationMovementCollectionMustBeBuffer);
	
	String getFieldName();
	MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setFieldName(String fieldName);
	
	@Getter
	public static class Adapter extends MovementCollectionByPartyEditFormMasterPrepareListener.Adapter.Default<MovementCollectionValuesTransferItemCollection, MovementCollectionValuesTransferItemCollectionItem> implements MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Boolean isDetinationMovementCollectionMustBeBuffer,isSourceMovementCollectionMustBeBuffer;
		protected String fieldName;
		
		public static class Default extends MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public MovementCollectionValuesTransferItemCollection getCollection(FormDetail detail) {
				String fieldName = getFieldName();
				if(StringHelper.getInstance().isNotBlank(fieldName))
					return (MovementCollectionValuesTransferItemCollection) FieldHelper.getInstance().read(detail.getMaster().getObject(), fieldName);
				return super.getCollection(detail);
			}
			
			@Override
			public Class<MovementCollectionValuesTransferItemCollection> getCollectionClass() {
				return MovementCollectionValuesTransferItemCollection.class;
			}
			
			@Override
			public Class<MovementCollectionValuesTransferItemCollectionItem> getItemClass() {
				return MovementCollectionValuesTransferItemCollectionItem.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.PartyControlGetAdapter> getPartyControlGetAdapterClass() {
				return MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.PartyControlGetAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
				return MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.ItemsDataTableCellAdapter.class;
			}
			
			@Override
			public Class<? extends MovementCollectionByPartyEditFormMasterPrepareListener.ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
				return MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.ItemsDataTableColumnAdapter.class;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			protected void prepareItemsDataTable(DataTable dataTable) {
				final FormDetail detail = dataTable.getForm();
				MovementCollectionValuesTransferItemCollection movementsTransferItemCollection = (MovementCollectionValuesTransferItemCollection) (StringHelper.getInstance().isBlank(fieldName) ? detail.getMaster().getObject() 
						: FieldHelper.getInstance().read(detail.getMaster().getObject(), fieldName));
				
				((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
					private static final long serialVersionUID = 1L;
							
					public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
						/*Row row = (Row) element;
						MovementCollectionValuesTransferItemCollectionItem item = (MovementCollectionValuesTransferItemCollectionItem) row.getPropertiesMap().getValue();
						InstanceHelper.getInstance().computeChanges(item.getSource());
						EndPoint sender = new EndPoint(),receiver = new EndPoint();
						AbstractIdentifiable sourceIdentifiableJoined=null;
						if(detail.getMaster().getObject() instanceof MovementCollectionValuesTransfer){
							sender.setParty(((MovementCollectionValuesTransfer)detail.getMaster().getObject()).getSender());
							if(sender.getParty()!=null){
								PartyIdentifiableGlobalIdentifier partyIdentifiableGlobalIdentifier = CollectionHelper.getInstance().getFirst(
										inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByPartyByBusinessRole(sender.getParty(), inject(BusinessRoleBusiness.class)
												.find(RootConstant.Code.BusinessRole.COMPANY)));
								if(partyIdentifiableGlobalIdentifier!=null)
									sender.setStore(inject(StoreDao.class).readByGlobalIdentifier(partyIdentifiableGlobalIdentifier.getIdentifiableGlobalIdentifier()));
							}
							MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier = CollectionHelper.getInstance().getFirst(
									inject(MovementCollectionIdentifiableGlobalIdentifierBusiness.class).findByMovementCollection(item.getSource().getCollection()));
							
							sourceIdentifiableJoined = getSourceIdentifiableJoined(sender, receiver, item.getSource().getCollection(),movementCollectionIdentifiableGlobalIdentifier);
							
							receiver.setParty(((MovementCollectionValuesTransfer)detail.getMaster().getObject()).getReceiver());
							if(receiver.getParty()!=null){
								PartyIdentifiableGlobalIdentifier partyIdentifiableGlobalIdentifier = CollectionHelper.getInstance().getFirst(
										inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByPartyByBusinessRole(receiver.getParty(), inject(BusinessRoleBusiness.class)
												.find(RootConstant.Code.BusinessRole.COMPANY)));
								if(partyIdentifiableGlobalIdentifier!=null)
									receiver.setStore(inject(StoreDao.class).readByGlobalIdentifier(partyIdentifiableGlobalIdentifier.getIdentifiableGlobalIdentifier()));
							}
						}
						MovementCollection movementCollection = getDestinationMovementCollection(detail,sender,receiver , item.getSource().getCollection(),sourceIdentifiableJoined);
						
						item.getDestination().setCollection(movementCollection);
						if(item.getDestination()!=null)
							InstanceHelper.getInstance().computeChanges(item.getDestination());
						*/
						//System.out.println(
						//		"MovementIdentifiableEditPageFormMaster.PrepareMovementCollectionValuesTransferItemCollectionListener.Adapter.Default.addPropertyRowsCollectionInstanceListener(...).new Adapter() {...}.addOne() 001");
					}		
					
				});
				
				dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.FALSE);
				dataTable.getPropertiesMap().setMasterFieldName(MovementCollectionValuesTransferItemCollectionItem.FIELD_COLLECTION);
				dataTable.getPropertiesMap().setMaster(movementsTransferItemCollection);
				dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION));
				
				super.prepareItemsDataTable(dataTable);
			}
				
			@Override
			public MovementCollection getDestinationMovementCollection(FormDetail detail,EndPoint sender, EndPoint receiver,MovementCollection source, AbstractIdentifiable sourceIdentifiableJoined) {
				MovementCollection movementCollection = null;
				if(source!=null){
					if(detail.getMaster().getObject() instanceof MovementCollectionValuesTransfer){
						if(Boolean.TRUE.equals(((MovementCollectionValuesTransfer)detail.getMaster().getObject()).getItems().getDestination().getMovementCollectionIsBuffer())){
							movementCollection = source.getBuffer();
						}else{
							
						}		
					}else if(detail.getMaster().getObject() instanceof MovementCollectionValuesTransferAcknowledgement){
						if(Boolean.TRUE.equals(((MovementCollectionValuesTransferAcknowledgement)detail.getMaster().getObject()).getItems().getSource().getMovementCollectionIsBuffer())){
							
						}else{
							
						}		
					}
					
				}
				return movementCollection;
			}
			
			@Override
			public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsSourceMovementCollectionMustBeBuffer(Boolean isSourceMovementCollectionMustBeBuffer) {
				this.isSourceMovementCollectionMustBeBuffer = isSourceMovementCollectionMustBeBuffer;
				return this;
			}
			
			@Override
			public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsDetinationMovementCollectionMustBeBuffer(Boolean isDetinationMovementCollectionMustBeBuffer) {
				this.isDetinationMovementCollectionMustBeBuffer = isDetinationMovementCollectionMustBeBuffer;
				return this;
			}
		
			@Override
			public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setFieldName(String fieldName) {
				this.fieldName = fieldName;
				if(StringHelper.getInstance().isNotBlank(this.fieldName) && !StringUtils.startsWith(getPartyFieldName(), this.fieldName+".")){
					setPartyFieldName(FieldHelper.getInstance().buildPath(this.fieldName,getPartyFieldName()));
				}
				return this;
			}
		}
	
		@Override
		public MovementCollection getDestinationMovementCollection(FormDetail detail,EndPoint sender,EndPoint receiver,MovementCollection source,AbstractIdentifiable sourceIdentifiableJoined) {
			return null;
		}
		
		@Override
		public AbstractIdentifiable getSourceIdentifiableJoined(EndPoint sender, EndPoint receiver,MovementCollection source,MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier) {
			return null;
		}
		
		@Override
		public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsSourceMovementCollectionMustBeBuffer(Boolean value) {
			return null;
		}
		
		@Override
		public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsDetinationMovementCollectionMustBeBuffer(Boolean value) {
			return null;
		}
	
		@Override
		public MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setFieldName(String fieldName) {
			return null;
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class EndPoint implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Party party;
		private Store store;
		
	}
	
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
				Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Action) column._getPropertyAction());
				if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_PREVIOUS_CUMUL).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_CUMUL).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_PREVIOUS_CUMUL).equals(column.getPropertiesMap().getFieldName())){
					if(isCreateOrUpdate)
						column.setCellValueType(Cell.ValueType.TEXT);
				}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL).equals(column.getPropertiesMap().getFieldName())){
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
			if(ArrayUtils.contains(new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_COLLECTION)}
				,column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_PREVIOUS_CUMUL)
						,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL)},new String[]{});
				
			}else if(ArrayUtils.contains(new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_VALUE_ABSOLUTE)}
				,column.getPropertiesMap().getFieldName())){
				Event.instanciateOne(cell, new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_CUMUL)
						,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL)},new String[]{});
			}
			
			return cell;
		}
		
	}
}