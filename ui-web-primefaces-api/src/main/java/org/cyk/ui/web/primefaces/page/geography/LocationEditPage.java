package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.Location;
import org.cyk.system.root.model.geography.LocationType;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.hibernate.validator.constraints.Email;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LocationEditPage extends AbstractContactEditPage<Location> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
		
	public static class Form extends AbstractForm<Location> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private LocationType type;
		@NotNull @Input @InputChoice @InputOneChoice @InputOneCombo private Locality locality;
		@Input @InputText @NotNull @Email private String otherDetails;
		
		public static final String FIELD_TYPE = "type";
		public static final String FIELD_LOCALITY = "locality";
		public static final String FIELD_OTHER_DETAILS = "otherDetails";
	}
	
}
