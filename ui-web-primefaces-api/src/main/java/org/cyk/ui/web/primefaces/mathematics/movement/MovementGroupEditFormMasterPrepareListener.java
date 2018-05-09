package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementGroupItem;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableCellAdapter;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableColumnAdapter;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementCollectionInventoryEditFormMasterPrepareListener.PartyControlGetAdapter;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;

public interface MovementGroupEditFormMasterPrepareListener {
	
	void addPartyField(Form.Detail detail);
	Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass();
	Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass();
	Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass();
	void addItemsDataTable(Form.Detail detail);
	
	void addPropertyRowsCollectionInstanceListener(final Form.Detail detail,final Boolean isCreateOrUpdate,final DataTable dataTable);

	public static class Adapter extends AbstractBean implements MovementGroupEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementGroupEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			@Override
			public void addPropertyRowsCollectionInstanceListener(final Detail detail,Boolean isCreateOrUpdate, DataTable dataTable) {
				((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
					private static final long serialVersionUID = 1L;
							
					public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
						MovementGroupItem movementGroupItem = (MovementGroupItem) ((DataTable.Row) element).getPropertiesMap().getValue();
						InstanceHelper.getInstance().computeChanges(movementGroupItem.getMovement());
					}		
					
				});
			}	
		}
	
		@Override
		public void addPropertyRowsCollectionInstanceListener(Detail detail,Boolean isCreateOrUpdate, DataTable dataTable) {}

		@Override
		public void addPartyField(Detail detail) {}

		@Override
		public Class<? extends PartyControlGetAdapter> getPartyControlGetAdapterClass() {
			return null;
		}

		@Override
		public Class<? extends ItemsDataTableColumnAdapter> getItemsDataTableColumnAdapterClass() {
			return null;
		}

		@Override
		public Class<? extends ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
			return null;
		}

		@Override
		public void addItemsDataTable(Detail detail) {}
		
	}
	
	/**/
	
}