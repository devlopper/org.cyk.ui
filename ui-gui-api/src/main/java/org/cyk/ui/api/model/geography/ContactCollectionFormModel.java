package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import org.cyk.system.root.business.api.geography.LocationTypeBusiness;
import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactCollectionFormModel extends AbstractContactCollectionFormModel implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText private String mobilePhoneNumber;
	@Input @InputText private String landPhoneNumber;
	@Input @InputText @Email private String electronicMail1,electronicMail2;
	@Input @InputText private String location;
	@Input @InputText private String postalBox;
	
	private String locationTypeCode;
	
	public ContactCollectionFormModel(String locationTypeCode) {
		super();
		this.locationTypeCode = locationTypeCode;
	}

	
	@Override
	public void write() {
		updatePhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND),landPhoneNumber);
		updatePhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.MOBILE) ,mobilePhoneNumber);
		updateElectronicMail(electronicMail1,0);
		updateElectronicMail(electronicMail2,1);
		updateLocation(inject(LocationTypeBusiness.class).find(locationTypeCode), location);
		updatePostalBox(postalBox);
		super.write();
	}
	
	@Override
	public void read() {
		landPhoneNumber = readPhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.LAND));
		mobilePhoneNumber = readPhoneNumber(inject(PhoneNumberTypeBusiness.class).find(PhoneNumberType.MOBILE));
		electronicMail1 = readElectronicMail(0);
		electronicMail2 = readElectronicMail(1);
		location = readLocation(inject(LocationTypeBusiness.class).find(locationTypeCode));
		postalBox = readPostalBox();
		super.read();
	}

	public static final String FIELD_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
	public static final String FIELD_LAND_PHONE_NUMBER = "landPhoneNumber";
	public static final String FIELD_ELECTRONICMAIL1 = "electronicMail1";
	public static final String FIELD_ELECTRONICMAIL2 = "electronicMail2";
	public static final String FIELD_LOCATION = "location";
	public static final String FIELD_POSTALBOX = "postalBox";
	
}
