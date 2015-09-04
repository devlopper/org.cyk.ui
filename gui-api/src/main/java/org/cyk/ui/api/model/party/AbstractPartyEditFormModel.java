package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractPartyEditFormModel<PARTY extends Party> extends AbstractPartyFormModel<PARTY> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	@OutputSeperator(label=@Text(value="field.contacts")) 
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected ContactCollectionEditFormModel contactCollectionFormModel = new ContactCollectionEditFormModel();
	
	@Override
	public void setIdentifiable(PARTY identifiable) {
		super.setIdentifiable(identifiable);
		if(identifiable.getContactCollection()==null)
			identifiable.setContactCollection(new ContactCollection());
		contactCollectionFormModel.setIdentifiable(identifiable.getContactCollection());
	}

}
