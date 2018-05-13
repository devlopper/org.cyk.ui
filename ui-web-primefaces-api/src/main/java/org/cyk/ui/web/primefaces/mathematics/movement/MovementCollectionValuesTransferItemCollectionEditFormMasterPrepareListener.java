package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.BusinessRoleBusiness;
import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.persistence.api.party.StoreDao;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.container.form.FormDetail;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener {
	
	void addPropertyRowsCollectionInstanceListener(final FormDetail detail,final String fieldName,final Boolean isCreateOrUpdate,final DataTable dataTable);
	MovementCollection getDestinationMovementCollection(FormDetail detail,EndPoint sender,EndPoint receiver,MovementCollection source,AbstractIdentifiable sourceIdentifiableJoined);
	AbstractIdentifiable getSourceIdentifiableJoined(EndPoint sender,EndPoint receiver,MovementCollection source,MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier);
	
	Boolean getIsSourceMovementCollectionMustBeBuffer();
	MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsSourceMovementCollectionMustBeBuffer(Boolean isSourceMovementCollectionMustBeBuffer);
	
	Boolean getIsDetinationMovementCollectionMustBeBuffer();
	MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener setIsDetinationMovementCollectionMustBeBuffer(Boolean isDetinationMovementCollectionMustBeBuffer);
	
	@Getter
	public static class Adapter extends AbstractBean implements MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Boolean isDetinationMovementCollectionMustBeBuffer,isSourceMovementCollectionMustBeBuffer;
		
		@Override
		public void addPropertyRowsCollectionInstanceListener(FormDetail detail, String fieldName,Boolean isCreateOrUpdate, DataTable dataTable) {}
		
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
		
		public static class Default extends MovementCollectionValuesTransferItemCollectionEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			@Override
			public void addPropertyRowsCollectionInstanceListener(final FormDetail detail, String fieldName,Boolean isCreateOrUpdate, DataTable dataTable) {
				((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
					private static final long serialVersionUID = 1L;
							
					public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
						Row row = (Row) element;
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
						/*if(movementCollection!=null){
							if(detail.getMaster().getObject() instanceof MovementCollectionValuesTransfer){
								if(Boolean.TRUE.equals(((MovementCollectionValuesTransfer)detail.getMaster().getObject()).getItems().getDestination().getMovementCollectionIsBuffer())){
									movementCollection = movementCollection.getBuffer();
								}else{
									
								}		
							}else if(detail.getMaster().getObject() instanceof MovementCollectionValuesTransferAcknowledgement){
								if(Boolean.TRUE.equals(((MovementCollectionValuesTransferAcknowledgement)detail.getMaster().getObject()).getItems().getSource().getMovementCollectionIsBuffer())){
									
								}else{
									
								}		
							}
							
						}*/
						item.getDestination().setCollection(movementCollection);
						if(item.getDestination()!=null)
							InstanceHelper.getInstance().computeChanges(item.getDestination());
						//System.out.println(
						//		"MovementIdentifiableEditPageFormMaster.PrepareMovementCollectionValuesTransferItemCollectionListener.Adapter.Default.addPropertyRowsCollectionInstanceListener(...).new Adapter() {...}.addOne() 001");
					}		
					
				});
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
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class EndPoint implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Party party;
		private Store store;
		
	}
}