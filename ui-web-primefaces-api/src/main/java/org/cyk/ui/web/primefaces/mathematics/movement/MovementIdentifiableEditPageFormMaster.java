package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.business.api.party.BusinessRoleBusiness;
import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.Movement;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransfer;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferAcknowledgement;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionValuesTransferItemCollectionItem;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.party.Store;
import org.cyk.system.root.persistence.api.party.StoreDao;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;
import org.cyk.utility.common.userinterface.event.Event;

public class MovementIdentifiableEditPageFormMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(PrepareMovementCollectionValuesTransferItemCollectionListener.class, PrepareMovementCollectionValuesTransferItemCollectionListener.Adapter.Default.class,Boolean.FALSE);
	}
	
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
	
	public static void prepareMovementCollectionValuesTransferItemCollection(final Form.Detail detail,final String fieldName,PrepareMovementCollectionValuesTransferItemCollectionListener listener){
		MovementCollectionValuesTransferItemCollection movementsTransferItemCollection = (MovementCollectionValuesTransferItemCollection) (StringHelper.getInstance().isBlank(fieldName) ? detail.getMaster().getObject() 
				: FieldHelper.getInstance().read(detail.getMaster().getObject(), fieldName));
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
		movementsTransferItemCollection.getItems().setSynchonizationEnabled(isCreateOrUpdate);
		movementsTransferItemCollection.getItems().removeAll(); // will be filled up by the data table load call
		
		//DataTable dataTable = detail.getMaster().instanciateDataTable(MovementCollectionValuesTransferItemCollectionItem.class,isCreateOrUpdate ? MovementCollection.class : null,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
		DataTable dataTable = detail.getMaster().instanciateDataTable(MovementCollectionValuesTransferItemCollectionItem.class,MovementCollection.class,new DataTable.Cell.Listener.Adapter.Default(),Boolean.TRUE);
		
		if(isCreateOrUpdate){
			/* events */
			
			dataTable.getPropertiesMap().setCellListener(new DataTable.Cell.Listener.Adapter.Default(){
				private static final long serialVersionUID = 1L;
				public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
					final DataTable.Cell cell = super.instanciateOne(column, row);
					//System.out.println("F : "+column.getPropertiesMap().getFieldName());
					if(ArrayUtils.contains(new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_COLLECTION)}
						,column.getPropertiesMap().getFieldName())){
						Event.instanciateOne(cell, new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_PREVIOUS_CUMUL)
								,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL)},new String[]{});
						//System.out.println("E01");
					}else if(ArrayUtils.contains(new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_VALUE)}
						,column.getPropertiesMap().getFieldName())){
						Event.instanciateOne(cell, new String[]{FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_CUMUL)
								,FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL)},new String[]{});
						//System.out.println("E02");
					}
					
					return cell;
				}
			});
			
		}
		
		listener.addPropertyRowsCollectionInstanceListener(detail, fieldName, isCreateOrUpdate, dataTable);
		
		dataTable.addColumnListener(new CollectionHelper.Instance.Listener.Adapter<Component>(){
			private static final long serialVersionUID = 1L;

			@Override
			public void addOne(CollectionHelper.Instance<Component> instance, Component element, Object source,Object sourceObject) {
				super.addOne(instance, element, source, sourceObject);
				if(element instanceof DataTable.Column){
					DataTable.Column column = (DataTable.Column)element;
					if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_PREVIOUS_CUMUL).equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_CUMUL).equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_PREVIOUS_CUMUL).equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}else if(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_DESTINATION,Movement.FIELD_CUMUL).equals(column.getPropertiesMap().getFieldName())){
						if(isCreateOrUpdate)
							column.setCellValueType(DataTable.Cell.ValueType.TEXT);
					}
				}
			}
		});
		
		dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.FALSE);
		dataTable.getPropertiesMap().setMasterFieldName(MovementCollectionValuesTransferItemCollectionItem.FIELD_COLLECTION);
		dataTable.getPropertiesMap().setMaster(movementsTransferItemCollection);
		dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION));
		
		dataTable.prepare();
		dataTable.build();	
	}
	
	public static void prepareMovementCollectionValuesTransferItemCollection(final Form.Detail detail,final String fieldName){
		prepareMovementCollectionValuesTransferItemCollection(detail, fieldName, ClassHelper.getInstance().instanciateOne(PrepareMovementCollectionValuesTransferItemCollectionListener.class));
	}
	
	public static void prepareMovementCollectionValuesTransfer(Form.Detail detail,Class<?> aClass){
		final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
		detail.add(MovementCollectionValuesTransfer.FIELD_SENDER).addBreak();
		detail.add(MovementCollectionValuesTransfer.FIELD_RECEIVER).addBreak();
		
		if(isCreateOrUpdate){
			Event.instanciateOne(detail, MovementCollectionValuesTransfer.FIELD_SENDER, new String[]{});
			Event.instanciateOne(detail, MovementCollectionValuesTransfer.FIELD_RECEIVER, new String[]{});
		}
		
		prepareMovementCollectionValuesTransferItemCollection(detail, MovementCollectionValuesTransfer.FIELD_ITEMS);
	}
	
	public static void prepareMovementCollectionValuesTransferAcknowledgement(Form.Detail detail,Class<?> aClass){
		detail.add(MovementCollectionValuesTransferAcknowledgement.FIELD_TRANSFER).addBreak();
		prepareMovementCollectionValuesTransferItemCollection(detail, MovementCollectionValuesTransferAcknowledgement.FIELD_ITEMS);
	}
	
	/**/
	
	public static interface PrepareMovementCollectionValuesTransferItemCollectionListener {
		
		void addPropertyRowsCollectionInstanceListener(final Form.Detail detail,final String fieldName,final Boolean isCreateOrUpdate,final DataTable dataTable);
		MovementCollection getDestinationMovementCollection(EndPoint sender,EndPoint receiver,MovementCollection source,AbstractIdentifiable sourceIdentifiableJoined);
		AbstractIdentifiable getSourceIdentifiableJoined(EndPoint sender,EndPoint receiver,MovementCollection source,MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier);
		
		public static class Adapter extends AbstractBean implements PrepareMovementCollectionValuesTransferItemCollectionListener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void addPropertyRowsCollectionInstanceListener(Detail detail, String fieldName,Boolean isCreateOrUpdate, DataTable dataTable) {}
			
			@Override
			public MovementCollection getDestinationMovementCollection(EndPoint sender,EndPoint receiver,MovementCollection source,AbstractIdentifiable sourceIdentifiableJoined) {
				return null;
			}
			
			@Override
			public AbstractIdentifiable getSourceIdentifiableJoined(EndPoint sender, EndPoint receiver,MovementCollection source,MovementCollectionIdentifiableGlobalIdentifier movementCollectionIdentifiableGlobalIdentifier) {
				return null;
			}
			
			public static class Default extends PrepareMovementCollectionValuesTransferItemCollectionListener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				@Override
				public void addPropertyRowsCollectionInstanceListener(final Detail detail, String fieldName,Boolean isCreateOrUpdate, DataTable dataTable) {
					((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
						private static final long serialVersionUID = 1L;
								
						public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
							DataTable.Row row = (DataTable.Row) element;
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
							item.getDestination().setCollection(getDestinationMovementCollection(sender,receiver , item.getSource().getCollection(),sourceIdentifiableJoined));
							if(item.getDestination()!=null)
								InstanceHelper.getInstance().computeChanges(item.getDestination());
							//System.out.println(
							//		"MovementIdentifiableEditPageFormMaster.PrepareMovementCollectionValuesTransferItemCollectionListener.Adapter.Default.addPropertyRowsCollectionInstanceListener(...).new Adapter() {...}.addOne() 001");
						}		
						
					});
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
}