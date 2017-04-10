package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @ViewScoped @Getter @Setter
public class LocalityEditPage extends AbstractCrudOnePage<Locality> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=Locality.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=LocalityType.class)
			})
	public static class Form extends AbstractDataTreeForm<Locality,LocalityType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText private String residentName;
		
		public static final String FIELD_RESIDENT_NAME = "residentName";
	
	}
	
}
