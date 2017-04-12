package org.cyk.ui.api.model;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractEnumeration;

@Getter @Setter
public abstract class AbstractEnumerationForm<ENUMERATION extends AbstractEnumeration> extends AbstractBusinessIdentifiedEditFormModel<ENUMERATION> {
	private static final long serialVersionUID = 3546404581804831710L;
	/*
	@Input @InputText @NotNull private String code;
	@Input @InputText @NotNull private String name;
	*/
	public AbstractEnumerationForm() {}
	
	public AbstractEnumerationForm(ENUMERATION enumeration) {
		super(enumeration);
		
	}
	/*
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
	*/
	/*
	public static final String FIELD_CODE = "code";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_ORDER_NUMBER = "orderNumber";
	*/
}