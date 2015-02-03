package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class SimpleContactCollectionFormModel extends AbstractFormModel<ContactCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText
	private String landPhoneNumber;
	
	@Input @InputText
	private String mobilePhoneNumber;
	
	@Input @InputText
	private String homeLocation;
	 
}
