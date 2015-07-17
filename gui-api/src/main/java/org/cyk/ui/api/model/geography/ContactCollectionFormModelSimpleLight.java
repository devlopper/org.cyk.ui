package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

@Getter @Setter
public class ContactCollectionFormModelSimpleLight extends AbstractContactCollectionFormModel implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText private String mobilePhoneNumber;
	@Input @InputText private String landPhoneNumber;
	@Input @InputText @Email private String electronicMail;
	
	@Override
	public void write() {
		updatePhoneNumber(RootBusinessLayer.getInstance().getLandPhoneNumberType(),landPhoneNumber);
		updatePhoneNumber(RootBusinessLayer.getInstance().getMobilePhoneNumberType() ,mobilePhoneNumber);
		updateElectronicMail(electronicMail);
		super.write();
	}
	
	@Override
	public void read() {
		landPhoneNumber = readPhoneNumber(RootBusinessLayer.getInstance().getLandPhoneNumberType());
		mobilePhoneNumber = readPhoneNumber(RootBusinessLayer.getInstance().getMobilePhoneNumberType());
		electronicMail = readElectronicMail();
		super.read();
	}
	
}
