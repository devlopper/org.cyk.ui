package org.cyk.ui.web.primefaces.store;

import java.io.Serializable;

import org.cyk.system.root.business.api.party.PartyIdentifiableGlobalIdentifierBusiness;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.party.PartyIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.store.Store;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.CollectionHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class StoreIdentifiableEditPageFormMaster extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static void prepareStore(Form.Detail detail){
		if(Constant.Action.CREATE.equals(detail._getPropertyAction())){
			
		}else{
			PartyIdentifiableGlobalIdentifier partyIdentifiableGlobalIdentifier = CollectionHelper.getInstance()
					.getFirst(inject(PartyIdentifiableGlobalIdentifierBusiness.class).findByIdentifiableGlobalIdentifierByBusinessRoleCode((Store)detail.getMaster().getObject()
						, RootConstant.Code.BusinessRole.COMPANY));
				if(partyIdentifiableGlobalIdentifier!=null)
					((Store)detail.getMaster().getObject()).setPartyCompany(partyIdentifiableGlobalIdentifier.getParty());	
		}
		detail.add(Store.FIELD_PARTY_COMPANY).addBreak();
	}
}