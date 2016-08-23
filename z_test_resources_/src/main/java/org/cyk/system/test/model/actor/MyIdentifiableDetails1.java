package org.cyk.system.test.model.actor;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.test.model.actor.MyEntity.MyEnum;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputEditor;
import org.cyk.utility.common.annotation.user.interfaces.InputOneButton;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class MyIdentifiableDetails1 extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2551782857718212950L;
	
	@Input @InputText @NotNull
	private String detailsTextOneLine;

	@Input @InputEditor
	private String detailsTextManyLine;
	
	@Input
	@InputChoice
	@InputOneChoice
	@InputOneButton
	private MyEnum detailsMyEnum;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
