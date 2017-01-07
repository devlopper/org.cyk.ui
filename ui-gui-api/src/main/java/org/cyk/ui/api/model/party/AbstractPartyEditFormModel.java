package org.cyk.ui.api.model.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.ContactCollectionBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPartyEditFormModel<PARTY extends AbstractIdentifiable> extends AbstractBusinessIdentifiedEditFormModel<PARTY> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	@OutputSeperator(label=@Text(value="field.contacts"))
	@IncludeInputs(layout=Layout.VERTICAL)
	protected ContactCollectionFormModel contactCollection = new ContactCollectionFormModel(RootConstant.Code.LocationType.HOME);
	
	@Override
	public void setIdentifiable(PARTY identifiable) {
		super.setIdentifiable(identifiable);
		setContactCollection();
	}
	
	protected void setContactCollection(){
		if(contactCollection.getIdentifiable()==null){
			if(getParty().getContactCollection()==null)
				getParty().setContactCollection(new ContactCollection());
			contactCollection.setIdentifiable(getParty().getContactCollection());	
			if(getParty().getContactCollection()!=null)
	    		inject(ContactCollectionBusiness.class).load(getParty().getContactCollection());//TODO think it should not be done here but in page instead
		}
		
	}
	
	protected abstract Party getParty();
	
	@Override
	public void read() {
		super.read();
		setContactCollection();
	}
	
	/**/
	public static final String FIELD_CONTACT_COLLECTION = "contactCollection";

}
