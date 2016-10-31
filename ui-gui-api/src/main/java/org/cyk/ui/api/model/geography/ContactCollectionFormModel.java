package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

@Getter @Setter
public class ContactCollectionFormModel extends AbstractContactCollectionFormModel implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	/* Home */
	@Input @InputText private String mobilePhoneNumber;
	@Input @InputText private String landPhoneNumber;
	@Input @InputText @Email private String electronicMail;
	@Input @InputText private String homeLocation;
	@Input @InputText private String postalBox;
	
	/* Work */
	/*
	@Input @InputText private String workMobilePhoneNumber;
	@Input @InputText private String workLandPhoneNumber;
	@Input @InputText @Email private String workElectronicMail;
	@Input @InputText private String workLocation;
	@Input @InputText private String workPostalBox;
	*/
	@Override
	public void write() {
		updatePhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND),landPhoneNumber);
		updatePhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.MOBILE) ,mobilePhoneNumber);
		updateElectronicMail(electronicMail);
		updateLocation(inject(LocationTypeBusiness.class).find(LocationType.HOME), homeLocation);
		updatePostalBox(postalBox);
		super.write();
	}
	
	@Override
	public void read() {
		landPhoneNumber = readPhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND));
		mobilePhoneNumber = readPhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.MOBILE));
		electronicMail = readElectronicMail();
		homeLocation = readLocation(inject(LocationTypeBusiness.class).find(LocationType.HOME));
		postalBox = readPostalBox();
		super.read();
	}
	
	public static final String FIELD_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
	public static final String FIELD_LAND_PHONE_NUMBER = "landPhoneNumber";
	public static final String FIELD_ELECTRONICMAIL = "electronicMail";
	public static final String FIELD_HOME_LOCATION = "homeLocation";
	public static final String FIELD_POSTALBOX = "postalBox";
	
}
