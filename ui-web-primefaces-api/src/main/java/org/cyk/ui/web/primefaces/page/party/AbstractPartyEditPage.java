package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.JobFunction;
import org.cyk.system.root.model.party.person.PersonTitle;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneRadio;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPartyEditPage<PARTY extends AbstractIdentifiable> extends AbstractCrudOnePage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Party getParty();
	
	@Override
	protected void initialisation() {
		super.initialisation();
		createFormOneData(new ContactCollectionFormModel(LocationType.HOME));
	}
	
	@Override
	protected void processOnIdentifiableFound(PARTY identifiable) {
		super.processOnIdentifiableFound(identifiable);
		if(isDetailsMenuCommandable(ContactCollection.class))
			inject(ContactCollectionBusiness.class).load(getParty().getContactCollection());
	}
	
	/**/
	

	
}
