package org.cyk.ui.web.primefaces.store;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.PartyBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.Store;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.userinterface.container.form.FormDetail;

public class StoreIdentifiableEditPageFormMaster extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void prepareStore(FormDetail detail){
		if(Constant.Action.CREATE.equals(detail._getPropertyAction())){
			
		}else{
			Store store = (Store)detail.getMaster().getObject();
			store.setPartyCompany(inject(PartyBusiness.class).findFirstByIdentifiableByBusinessRoleCode(store, RootConstant.Code.BusinessRole.COMPANY));	
		}
		detail.add(Store.FIELD_HAS_PARTY_AS_COMPANY).addBreak();
		detail.add(Store.FIELD_PARTY_COMPANY).addBreak();
	}
}