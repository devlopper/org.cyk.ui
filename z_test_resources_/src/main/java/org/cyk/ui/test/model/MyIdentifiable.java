package org.cyk.ui.test.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.test.model.MyEntity.MyEnum;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;

@Getter @Setter
public class MyIdentifiable extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2551782857718212950L;
	
	@Input @InputText
	private String textOneLine;

	@Input @InputTextarea
	private String textManyLine;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	private MyEnum myEnum;
	
	@Input @InputTextarea
	private String textManyLine2;
	
	@IncludeInputs
	@OutputSeperator
	private MyIdentifiableDetails1 details1;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
