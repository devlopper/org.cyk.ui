package org.cyk.ui.api.model.table;

import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;

public class RowAdapter<DATA> extends org.cyk.utility.common.model.table.RowListener.Adapter<Row<DATA>, DATA, Cell, String> {

	@Override
	public void added(Row<DATA> row) {
		super.added(row);
		AbstractIdentifiable identifiable = null;
		if(row.getData() instanceof AbstractOutputDetails<?>)
			identifiable = ((AbstractOutputDetails<?>)row.getData()).getMaster();
		else if(row.getData() instanceof AbstractFormModel<?>)
			identifiable = ((AbstractFormModel<?>)row.getData()).getIdentifiable();
		
		if(identifiable!=null){
			row.setOpenable(RootBusinessLayer.getInstance().getGlobalIdentifierBusiness().isReadable(identifiable));
			row.setUpdatable(RootBusinessLayer.getInstance().getGlobalIdentifierBusiness().isUpdatable(identifiable));
			row.setDeletable(RootBusinessLayer.getInstance().getGlobalIdentifierBusiness().isDeletable(identifiable));
		}
	}
	
}
