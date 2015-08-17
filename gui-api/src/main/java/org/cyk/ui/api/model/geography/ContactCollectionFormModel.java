package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

@Getter @Setter
public class ContactCollectionFormModel extends AbstractContactCollectionFormModel implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText private String mobilePhoneNumber;
	@Input @InputText private String landPhoneNumber;
	@Input @InputText @Email private String electronicMail;
	@Input @InputText private String homeLocation;
	@Input @InputText private String postalBox;
	
	@Override
	public void write() {
		updatePhoneNumber(rootBusinessLayer.getLandPhoneNumberType(),landPhoneNumber);
		updatePhoneNumber(rootBusinessLayer.getMobilePhoneNumberType() ,mobilePhoneNumber);
		updateElectronicMail(electronicMail);
		updateLocation(rootBusinessLayer.getHomeLocationType(), homeLocation);
		updatePostalBox(postalBox);
		super.write();
	}
	
	@Override
	public void read() {
		landPhoneNumber = readPhoneNumber(RootBusinessLayer.getInstance().getLandPhoneNumberType());
		mobilePhoneNumber = readPhoneNumber(RootBusinessLayer.getInstance().getMobilePhoneNumberType());
		electronicMail = readElectronicMail();
		homeLocation = readLocation(rootBusinessLayer.getHomeLocationType());
		postalBox = readPostalBox();
		super.read();
	}
	
	public static final String FIELD_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
	public static final String FIELD_LAND_PHONE_NUMBER = "landPhoneNumber";
	public static final String FIELD_ELECTRONICMAIL = "electronicMail";
	public static final String FIELD_HOME_LOCATION = "homeLocation";
	public static final String FIELD_POSTALBOX = "postalBox";
	
}
