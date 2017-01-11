package org.cyk.ui.api.model.table;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.CommonUtils;

public class RowAdapter<DATA> extends org.cyk.utility.common.model.table.RowListener.Adapter<Row<DATA>, DATA, Cell, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public void added(Row<DATA> row) {
		super.added(row);
		AbstractIdentifiable identifiable = null;
		if(row.getData() instanceof AbstractOutputDetails<?>)
			identifiable = ((AbstractOutputDetails<?>)row.getData()).getMaster();
		else if(row.getData() instanceof AbstractFormModel<?>)
			identifiable = ((AbstractFormModel<?>)row.getData()).getIdentifiable();
		
		if(identifiable!=null){
			row.setOpenable(CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isReadable(identifiable));
			row.setUpdatable(CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isUpdatable(identifiable));
			row.setDeletable(CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isDeletable(identifiable));
			
			if(identifiable.getIdentifier()!=null && StringUtils.isBlank(row.getCascadeStyleSheet().getUniqueClass()))
				inject(CascadeStyleSheetBusiness.class).setUniqueClass(row.getCascadeStyleSheet(),inject(CascadeStyleSheetBusiness.class).generateUniqueClass(identifiable));
		}
	}
	
}
