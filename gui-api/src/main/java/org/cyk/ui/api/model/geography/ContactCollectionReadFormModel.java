package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.ContentType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class ContactCollectionReadFormModel extends AbstractContactCollectionFormModel implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	public static String SEPARATOR = ContentType.DEFAULT.getNewLineMarker();
	
	@Input @InputText private String contacts;
	
	@Override
	public void read() {
		append(readPhoneNumber(RootBusinessLayer.getInstance().getLandPhoneNumberType()));
		append(readPhoneNumber(RootBusinessLayer.getInstance().getMobilePhoneNumberType()));
		append(readElectronicMail());
		append(readLocation(rootBusinessLayer.getHomeLocationType()));
		append(readPostalBox());
		super.read();
	}
	
	private void append(String value){
		if(StringUtils.isNotBlank(value))
			if(StringUtils.isBlank(contacts))
				contacts = value;
			else
				contacts = contacts + SEPARATOR + value;
	}
	
	public static final String FIELD_CONTACTS = "contacts";
	
}
