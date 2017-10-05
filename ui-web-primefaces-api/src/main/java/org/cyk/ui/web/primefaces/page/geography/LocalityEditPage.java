package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.ui.api.model.geography.GlobalPositionFormModel;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LocalityEditPage extends AbstractDataTreeNodeEditPage<Locality> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;	
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=Locality.class)
			,@FieldOverride(name=AbstractDataTreeForm.FIELD_TYPE,type=LocalityType.class)
			})
	public static class Form extends AbstractDataTreeForm<Locality,LocalityType> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputText private String residentName;
		@IncludeInputs private GlobalPositionFormModel globalPosition = new GlobalPositionFormModel();
		
		@Override
		public void read() {
			super.read();
			globalPosition.set(identifiable.getGlobalPosition());
		}
		
		@Override
		public void write() {
			super.write();
			globalPosition.write(identifiable.getGlobalPosition());
		}
		
		public static final String FIELD_RESIDENT_NAME = "residentName";
		public static final String FIELD_GLOBAL_POSITION = "globalPosition";
	}
	
}
