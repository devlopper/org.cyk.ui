package org.cyk.ui.api.model.table;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.CommonUtils;

@Getter @Setter
public class RowAdapter<DATA> extends org.cyk.utility.common.model.table.Row.Listener.Adapter.Default<Row<DATA>, DATA, Cell, String> {

	private static final long serialVersionUID = 1L;
	
	protected AbstractUserSession<?, ?> userSession;
	
	public RowAdapter(AbstractUserSession<?, ?> userSession) {
		super();
		this.userSession = userSession;
	}
	
	@Override
	public void added(Row<DATA> row) {
		super.added(row);
		AbstractIdentifiable identifiable = null;
		if(row.getData() instanceof AbstractOutputDetails<?>)
			identifiable = ((AbstractOutputDetails<?>)row.getData()).getMaster();
		else if(row.getData() instanceof AbstractFormModel<?>)
			identifiable = ((AbstractFormModel<?>)row.getData()).getIdentifiable();
		
		if(identifiable!=null){
			Boolean isAdministrator = getUserSession()!=null && getUserSession().getUserAccount()!=null 
					&& inject(UserAccountBusiness.class).hasRole(getUserSession().getUserAccount(), RootConstant.Code.Role.ADMINISTRATOR);
			
			row.setOpenable(Boolean.TRUE.equals(org.cyk.utility.common.model.table.Row.Listener.Adapter.READABLE) || isAdministrator ||
					CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isReadable(identifiable));
			row.setUpdatable(Boolean.TRUE.equals(org.cyk.utility.common.model.table.Row.Listener.Adapter.UPDATABLE) || isAdministrator ||
					CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isUpdatable(identifiable));
			row.setDeletable(Boolean.TRUE.equals(org.cyk.utility.common.model.table.Row.Listener.Adapter.DELETABLE) || isAdministrator ||
					CommonUtils.getInstance().inject(GlobalIdentifierBusiness.class).isDeletable(identifiable));
			
			if(identifiable.getIdentifier()!=null && StringUtils.isBlank(row.getCascadeStyleSheet().getUniqueClass()))
				inject(CascadeStyleSheetBusiness.class).setUniqueClass(row.getCascadeStyleSheet(),inject(CascadeStyleSheetBusiness.class).generateUniqueClass(identifiable));
		}
	}

	
}
