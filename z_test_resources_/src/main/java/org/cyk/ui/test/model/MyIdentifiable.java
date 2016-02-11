package org.cyk.ui.test.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.test.model.MyEntity.MyEnum;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.OutputText;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.annotation.user.interfaces.Text.ValueType;

@Getter @Setter
public class MyIdentifiable extends AbstractIdentifiable implements Serializable {

	private static final long serialVersionUID = 2551782857718212950L;
	
	@Input @InputText //@NotNull
	private String textOneLine;

	@Input @InputTextarea //@NotNull
	private String textManyLine;
	
	@Input @InputNumber /*@NotNull*/ private BigDecimal number1=BigDecimal.ZERO;
	@Input @InputNumber /*@NotNull*/ private BigDecimal number2=BigDecimal.ZERO;
	
	@Input @InputBooleanButton private Boolean canSum;
	
	@OutputSeperator(label=@Text(value="results")) 
	@OutputText(label=@Text(type=ValueType.VALUE,value="This is the results section")) 
	@Input(disabled=true) @InputText 
	private String sumResult;
	@Input(disabled=true) @InputText private String multiplyResult = "159753.852";
	
	@Input(disabled=true)
	@InputChoice
	@InputOneChoice
	@InputOneCombo
	private MyEnum myEnum = MyEnum.V3;
	
	@Input(disabled=true) @InputTextarea
	private String textManyLine2 = "Text Many Line Two";
	
	//@IncludeInputs
	//@OutputSeperator
	private MyIdentifiableDetails1 details1;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
