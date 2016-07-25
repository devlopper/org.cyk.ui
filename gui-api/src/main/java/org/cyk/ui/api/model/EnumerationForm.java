package org.cyk.ui.api.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class EnumerationForm extends AbstractFormModel<AbstractEnumeration> {
	private static final long serialVersionUID = 3546404581804831710L;
	
	@Input @InputText @NotNull private String code;
	@Input @InputText @NotNull private String name;
	
	public EnumerationForm() {}
	
	public EnumerationForm(AbstractEnumeration enumeration) {
		setIdentifiable(enumeration);
		read();
	}
	
	@Override
	public void read() {
		super.read();
		code = identifiable.getCode();
		name = identifiable.getName();
	}
	
	@Override
	public void write() {
		super.write();
		identifiable.setCode(code);
		identifiable.setName(name);
	}
}