package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.AbstractCollectionItem;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.movement.AbstractMovementCollections;
import org.cyk.system.root.model.mathematics.movement.MovementCollection;
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
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.event.Event;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;

import lombok.Getter;

public interface MovementCollectionByPartyEditFormMasterPrepareListener<COLLECTION extends AbstractMovementCollections<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> {
	
	Class<COLLECTION> getCollectionClass();
	Class<ITEM> getItemClass();
	String getPartyFieldName();
	MovementCollectionByPartyEditFormMasterPrepareListener<COLLECTION,ITEM> setPartyFieldName(String partyFieldName);
	void addFields(FormDetail detail);
	void addPartyField(FormDetail detail);
	Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass();
	Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass();
	Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass();
	void addItemsDataTable(FormDetail detail);
	
	COLLECTION getCollection(FormDetail detail);
	
	@Getter
	public static class Adapter<COLLECTION extends AbstractMovementCollections<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends AbstractBean implements MovementCollectionByPartyEditFormMasterPrepareListener<COLLECTION,ITEM>,Serializable {
		private static final long serialVersionUID = 1L;
		
		protected String partyFieldName;
		
		public static class Default<COLLECTION extends AbstractMovementCollections<ITEM>,ITEM extends AbstractCollectionItem<COLLECTION>> extends MovementCollectionByPartyEditFormMasterPrepareListener.Adapter<COLLECTION,ITEM> implements Serializable {
			private static final long serialVersionUID = 1L;
			
			{
				partyFieldName = Party.VARIABLE_NAME;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public COLLECTION getCollection(FormDetail detail) {
				return (COLLECTION) detail.getMaster().getObject();
			}
			
			@Override
			public void addFields(FormDetail detail) {
				final COLLECTION collection = getCollection(detail);
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
				collection.getItems().setSynchonizationEnabled(isCreateOrUpdate);
				collection.getItems().removeAll(); // will be filled up by the data table load call				
				addPartyField(detail);
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Class<COLLECTION> getCollectionClass() {
				return (Class<COLLECTION>) ClassHelper.getInstance().getParameterAt(getClass(), 0, AbstractMovementCollections.class);
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Class<ITEM> getItemClass() {
				return (Class<ITEM>) ClassHelper.getInstance().getParameterAt(getClass(), 1, AbstractCollectionItem.class);
			}
			
			@Override
			public void addPartyField(FormDetail detail) {
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
				String partyFieldName = getPartyFieldName();
				String before = FieldHelper.getInstance().getBeforeLast(partyFieldName);
				String last = FieldHelper.getInstance().getLast(partyFieldName);
				if(!partyFieldName.equals(before)){
					detail.setFieldsObjectFromMaster(before);
				}
				detail.addByControlGetListener(ClassHelper.getInstance().instanciateOne(getPartyControlGetAdapterClass()),last).addBreak();
				detail.setFieldsObjectFromMaster();
				if(isCreateOrUpdate){
					
				}
			}
			
			@Override
			public void addItemsDataTable(FormDetail detail) {
				final COLLECTION collection = getCollection(detail);
				final Boolean isCreateOrUpdate = Constant.Action.isCreateOrUpdate((Constant.Action)detail._getPropertyAction());
				final DataTable dataTable = detail.getMaster().instanciateDataTable(getItemClass(),MovementCollection.class,new Cell.Listener.Adapter.Default(),Boolean.TRUE);
				
				if(isCreateOrUpdate){
					/* events */
					Event event = Event.instanciateOne(detail, getPartyFieldName()/*"party"*/, new String[]{});
					
					event.getListener().addActionListener(new Event.ActionAdapter(event, detail, null, new LoggingHelper.Message.Builder.Adapter.Default()){
						private static final long serialVersionUID = 1L;

						@Override
						public void __execute__(Action<?, ?> action) {
							super.__execute__(action);
							@SuppressWarnings("unchecked")
							CollectionHelper.Instance<Row> rows = (CollectionHelper.Instance<Row>)dataTable.getPropertiesMap().getRowsCollectionInstance();
							rows.removeAll();
							dataTable.addManyRow(collection.getItems());
						}
					});
					
					dataTable.getPropertiesMap().setCellListener(ClassHelper.getInstance().instanciateOne(getItemsDataTableCellAdapterClass()));
					
				}
				
				dataTable.addColumnListener(ClassHelper.getInstance().instanciateOne(getItemsDataTableColumnAdapterClass()));
				
				dataTable.getPropertiesMap().setChoicesIsSourceDisjoint(Boolean.TRUE);
				dataTable.getPropertiesMap().setMasterFieldName(AbstractCollectionItem.FIELD_COLLECTION);
				dataTable.getPropertiesMap().setMaster(collection);
				dataTable.getPropertiesMap().setIsAutomaticallyAddChoiceValues(Boolean.FALSE);
				//dataTable.getPropertiesMap().setChoiceValueClassMasterFieldName(FieldHelper.getInstance().buildPath(MovementCollectionValuesTransferItemCollectionItem.FIELD_SOURCE,Movement.FIELD_COLLECTION));
				
				prepareItemsDataTable(dataTable);
				dataTable.build();	
								
			}
		
			protected void prepareItemsDataTable(DataTable dataTable){
				dataTable.prepare();
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
			
			@Override
			public MovementCollectionByPartyEditFormMasterPrepareListener<COLLECTION, ITEM> setPartyFieldName(String partyFieldName) {
				this.partyFieldName = partyFieldName;
				return this;
			}
		}
		
		@Override
		public MovementCollectionByPartyEditFormMasterPrepareListener<COLLECTION, ITEM> setPartyFieldName(String partyFieldName) {
			return null;
		}
		
		@Override
		public COLLECTION getCollection(FormDetail detail) {
			return null;
		}
		
		@Override
		public void addFields(FormDetail detail) {}
		
		@Override
		public Class<COLLECTION> getCollectionClass() {
			return null;
		}
		
		@Override
		public Class<ITEM> getItemClass() {
			return null;
		}
		
		@Override
		public void addPartyField(FormDetail detail) {}
		
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
		public void addItemsDataTable(FormDetail detail) {}

		
	}
	
	/**/
	
	public static class PartyControlGetAdapter extends Control.Listener.Get.Adapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		protected Collection<Party> findInstances(FormDetail detail){
			return inject(PartyBusiness.class).findByIdentifiablesByBusinessRoleCode(InstanceHelper.getInstance().get(Store.class), RootConstant.Code.BusinessRole.COMPANY);
		}
		
		@Override
		public void processBeforeInitialise(Control control, FormDetail detail, Object object, Field field,Constraints constraints) {
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
			if(element instanceof Column){
				//Column column = (Column)element;
				
			}
		}
	}
	
	public static class ItemsDataTableCellAdapter extends Cell.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public Cell instanciateOne(Column column, Row row) {
			final Cell cell = super.instanciateOne(column, row);
			
			return cell;
		}
		
	}
	
}