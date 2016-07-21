package org.cyk.ui.api.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractQueryOneFormModel<IDENTIFIABLE extends AbstractIdentifiable,IDENTIFIER> extends AbstractBean implements Serializable{
	private static final long serialVersionUID = -4741435164709063863L;
	
	@Input @InputText @NotNull protected IDENTIFIER identifier;
	@Input @InputChoice(load=false) @InputOneCombo @NotNull protected IDENTIFIABLE identifiable;
	
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_IDENTIFIABLE = "identifiable";
	
	/**/
	
	@Getter @Setter @FieldOverrides(value={@FieldOverride(name=FIELD_IDENTIFIER,type=String.class)})
	public static class Default<IDENTIFIABLE extends AbstractIdentifiable> extends AbstractQueryOneFormModel<IDENTIFIABLE,String> implements Serializable {
		private static final long serialVersionUID = -3756660150800681378L;
		
	}
	
}