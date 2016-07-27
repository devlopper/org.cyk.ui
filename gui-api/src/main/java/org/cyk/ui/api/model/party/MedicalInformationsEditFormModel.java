package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class MedicalInformationsEditFormModel extends AbstractFormModel<MedicalInformations> implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	@Input @InputText private String a1,a11,a111;
	
	public static final String FIELD_MOBILE_PHONE_NUMBER = "mobilePhoneNumber";
	public static final String FIELD_LAND_PHONE_NUMBER = "landPhoneNumber";
	public static final String FIELD_ELECTRONICMAIL = "electronicMail";
	public static final String FIELD_HOME_LOCATION = "homeLocation";
	public static final String FIELD_POSTALBOX = "postalBox";
	
}
