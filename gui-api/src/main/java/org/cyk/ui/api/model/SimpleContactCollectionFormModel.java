package org.cyk.ui.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.geography.PhoneNumberTypeBusiness;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.geography.PhoneNumberType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class SimpleContactCollectionFormModel extends AbstractFormModel<ContactCollection> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	private PhoneNumberTypeBusiness phoneNumberTypeBusiness;
	
	@Input @InputText
	private String landPhoneNumber;
	
	@Input @InputText
	private String mobilePhoneNumber;
	
	@Input @InputText
	private String postalBox;
	
	@Input @InputText
	private String homeLocation;
	
	@Override
	public void write() {
		//updatePhoneNumber(phoneNumberTypeBusiness.find("FIXE") ,landPhoneNumber);
		//updatePhoneNumber(phoneNumberTypeBusiness.find("MOBILE") ,mobilePhoneNumber);
		super.write();
	}
	
	private void updatePhoneNumber(PhoneNumberType type,String number){
		PhoneNumber phoneNumber = null;
		for(PhoneNumber p : identifiable.getPhoneNumbers())
			if(p.getType().equals(type)){
				phoneNumber = p;
				break;
			}
		if(phoneNumber ==null){
			phoneNumber = new PhoneNumber();
			phoneNumber.setType(type);
			identifiable.getPhoneNumbers().add(phoneNumber);
		}
		
		phoneNumber.setNumber(number);
		
	}
}
