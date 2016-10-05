package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Getter @Setter
public abstract class AbstractPartyEditPage<PARTY extends AbstractIdentifiable> extends AbstractCrudOnePage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Party getParty();
		
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		T t = super.identifiableFromRequestParameter(aClass, identifierId);
		if(isDetailsMenuCommandable(ContactCollection.class)){
			if(t instanceof Party){
				inject(ContactCollectionBusiness.class).load(((Party)t).getContactCollection());
			}
		}
		debug( t );
		return t;
	}
	
}
