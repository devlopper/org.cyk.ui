package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementCollection;
import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementCollectionInventoryEditFormMasterPrepareListener;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
import org.cyk.utility.common.userinterface.output.OutputText;

public class MovementCollectionInventoryEditFormMasterPrepareAdapter extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter.Default implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Class<? extends MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableCellAdapter> getItemsDataTableCellAdapterClass() {
		return ItemsDataTableCellAdapter.class;
	}
	
	@Override
	public Class<? extends MovementCollectionInventoryEditFormMasterPrepareListener.PartyControlGetAdapter> getPartyControlGetAdapterClass() {
		return PartyControlGetAdapter.class;
	}
	
	@Override
	protected void prepareItemsDataTable(DataTable movementCollectionInventoryItemCollection) {
		super.prepareItemsDataTable(movementCollectionInventoryItemCollection);
		((OutputText)movementCollectionInventoryItemCollection.getPropertiesMap().getAddTextComponent()).getPropertiesMap().setValue(StringHelper.getInstance().get("product", new Object[]{}));
		((OutputText)movementCollectionInventoryItemCollection.getColumn(movementCollectionInventoryItemCollection.getChoiceValueClassMasterFieldName()).getPropertiesMap().getHeader()).getPropertiesMap()
			.setValue(StringHelper.getInstance().get("product", new Object[]{}));
		((OutputText)movementCollectionInventoryItemCollection.getColumn(FieldHelper.getInstance().buildPath(MovementCollectionInventoryItem.FIELD_MOVEMENT_COLLECTION
				,MovementCollection.FIELD_VALUE)).getPropertiesMap().getHeader()).getPropertiesMap().setValue(StringHelper.getInstance().get("actual.value", new Object[]{}));
		((InputChoice<?>)movementCollectionInventoryItemCollection.getPropertiesMap().getAddInputComponent())
			.setInputChoiceListener(new InputChoice.Listener.Adapter.Default(){
				private static final long serialVersionUID = 1L;

				public String getChoiceLabel(Object value) {
					return value.toString();
				}
			} );
	}
	
	/**/
	
	public static class PartyControlGetAdapter extends MovementCollectionInventoryEditFormMasterPrepareListener.PartyControlGetAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
	
		@Override
		public String getLabelValueIdentifier() {
			return "store";
		}
		
	}
	
	public static class ItemsDataTableCellAdapter extends MovementCollectionInventoryEditFormMasterPrepareListener.ItemsDataTableCellAdapter implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTable.Cell instanciateOne(DataTable.Column column, DataTable.Row row) {
			final DataTable.Cell cell = super.instanciateOne(column, row);
			if( ((DataTable)column.getPropertiesMap().getDataTable()).getChoiceValueClassMasterFieldName().equals(column.getPropertiesMap().getFieldName())) {
				//((OutputText)cell.getPropertiesMap().getValue()).getPropertiesMap().setValue("PRODUCT CODE / NAME : "+((OutputText)cell.getPropertiesMap().getValue()).getPropertiesMap().getValue());
			}
			return cell;
		}
		
	}
	
}
