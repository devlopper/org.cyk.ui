package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;

@Getter @Setter
public class AdvancedContactCollectionFormModel extends AbstractFormModel<ContactCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;
	
	@OutputSeperator @IncludeInputs private PhoneNumber phoneNumber;
	
	@OutputSeperator @IncludeInputs private Location location;
	 
}
