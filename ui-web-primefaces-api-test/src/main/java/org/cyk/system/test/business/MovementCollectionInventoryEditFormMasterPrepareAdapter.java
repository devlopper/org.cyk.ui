package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.system.root.model.mathematics.movement.MovementCollectionInventoryItem;
import org.cyk.ui.web.primefaces.mathematics.movement.MovementCollectionInventoryEditFormMasterPrepareListener;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Constant.Action;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.collection.Cell;
import org.cyk.utility.common.userinterface.collection.Column;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.collection.Row;
import org.cyk.utility.common.userinterface.input.choice.InputChoice;
import org.cyk.utility.common.userinterface.output.OutputText;

@Deprecated
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
		if(Constant.Action.isCreateOrUpdate((Action) movementCollectionInventoryItemCollection._getPropertyAction())){
			((OutputText)movementCollectionInventoryItemCollection.getPropertiesMap().getAddTextComponent()).getPropertiesMap().setValue(StringHelper.getInstance().get("product", new Object[]{}));
		
			((InputChoice<?>)movementCollectionInventoryItemCollection.getPropertiesMap().getAddInputComponent())
			.setInputChoiceListener(new InputChoice.Listener.Adapter.Default(){
				private static final long serialVersionUID = 1L;

				public String getChoiceLabel(Object value) {
					return value.toString();
				}
			} );	
		}
		
		((OutputText)movementCollectionInventoryItemCollection.getColumn(movementCollectionInventoryItemCollection.getChoiceValueClassMasterFieldName()).getPropertiesMap().getHeader()).getPropertiesMap()
			.setValue(StringHelper.getInstance().get("product", new Object[]{}));
		((OutputText)movementCollectionInventoryItemCollection.getColumn(MovementCollectionInventoryItem.FIELD_PREVIOUS_VALUE
			).getPropertiesMap().getHeader()).getPropertiesMap().setValue(StringHelper.getInstance().get("actual.value", new Object[]{}));
			
		
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
		
		public Cell instanciateOne(Column column, Row row) {
			final Cell cell = super.instanciateOne(column, row);
			if( ((DataTable)column.getPropertiesMap().getDataTable()).getChoiceValueClassMasterFieldName().equals(column.getPropertiesMap().getFieldName())) {
				//((OutputText)cell.getPropertiesMap().getValue()).getPropertiesMap().setValue("PRODUCT CODE / NAME : "+((OutputText)cell.getPropertiesMap().getValue()).getPropertiesMap().getValue());
			}
			return cell;
		}
		
	}
	
}
