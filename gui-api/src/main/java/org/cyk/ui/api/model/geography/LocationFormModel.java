package org.cyk.ui.api.model.geography;

import java.io.Serializable;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputChoiceAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneAutoComplete;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LocationFormModel extends AbstractFormModel<Location> implements Serializable {

	private static final long serialVersionUID = -3141389017554508218L;

	@Input @InputChoice @InputOneChoice @InputOneCombo private LocationType type;
	@Input @InputChoice @InputOneChoice @InputOneCombo private LocalityType localityType;
	@Input @InputChoice @InputChoiceAutoComplete @InputOneChoice @InputOneAutoComplete private Locality locality;
	@Input @InputText private String otherDetails;
	
	/**/
	
	@Override
	public void read() {
		super.read();
		if(identifiable==null){
			
		}else{
			type = identifiable.getType();
			locality = identifiable.getLocality();
			otherDetails = identifiable.getOtherDetails();	
		}
		
	}
	
	@Override
	public void write() {
		super.write();
		if(identifiable==null)
			return;
		identifiable.setType(type);
		identifiable.setLocality(locality);
		identifiable.setOtherDetails(otherDetails);
	}
	
	/**/
	
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_LOCALITY_TYPE = "localityType";
	public static final String FIELD_LOCALITY = "locality";
	public static final String FIELD_OTHER_DETAILS = "otherDetails";
}
