package org.cyk.ui.web.primefaces.mathematics.movement;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.Store;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.userinterface.collection.DataTable;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Detail;

public interface MovementCollectionInventoryEditFormMasterPrepareListener {
	
	void addPropertyRowsCollectionInstanceListener(final Form.Detail detail,final Boolean isCreateOrUpdate,final DataTable dataTable);
	Collection<Party> findParties(Form.Detail detail);
	
	public static class Adapter extends AbstractBean implements MovementCollectionInventoryEditFormMasterPrepareListener,Serializable {
		private static final long serialVersionUID = 1L;
		
		public static class Default extends MovementCollectionInventoryEditFormMasterPrepareListener.Adapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			@SuppressWarnings("unchecked")
			@Override
			public void addPropertyRowsCollectionInstanceListener(final Detail detail,Boolean isCreateOrUpdate, DataTable dataTable) {
				((CollectionHelper.Instance<Object>)dataTable.getPropertyRowsCollectionInstance()).addListener(new CollectionHelper.Instance.Listener.Adapter<Object>(){
					private static final long serialVersionUID = 1L;
							
					public void addOne(CollectionHelper.Instance<Object> instance, Object element, Object source, Object sourceObject) {
						//DataTable.Row row = (DataTable.Row) element;
						//MovementCollectionInventoryItem item = (MovementCollectionInventoryItem) row.getPropertiesMap().getValue();
						
					}		
					
				});
			}	
		
			@Override
			public Collection<Party> findParties(Detail detail) {
				return inject(PartyBusiness.class).findByIdentifiablesByBusinessRoleCode(InstanceHelper.getInstance().get(Store.class), RootConstant.Code.BusinessRole.COMPANY);
			}
		}
		
		@Override public void addPropertyRowsCollectionInstanceListener(Detail detail,Boolean isCreateOrUpdate, DataTable dataTable) {}
		
		@Override
		public Collection<Party> findParties(Detail detail) {
			return null;
		}
	}
	
	/**/
	
}