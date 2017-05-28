package org.cyk.ui.api.model.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.cyk.system.root.model.value.Value;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanCheck;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar.Format;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class ValueFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputCalendar(format=Format.DATETIME_LONG) private Date dateValue;
	@Input @InputBooleanCheck private Boolean booleanValue;
	@Input @InputNumber private BigDecimal numberValue;
	@Input @InputText private String stringValue;
	
	private Boolean showDate,showBoolean,showNumber,showString;
	
	public void set(Value value){
		if(value==null)
			return;
		booleanValue = value.getBooleanValue().get();
		numberValue = value.getNumberValue().get();
		booleanValue = value.getBooleanValue().get();
		stringValue = value.getStringValue().get();
		
		if(value.getType()!=null)
			switch(value.getType()){
			case BOOLEAN: showBoolean = Boolean.TRUE; break;
			case NUMBER: showNumber = Boolean.TRUE; break;
			case STRING: showString = Boolean.TRUE; break;
			}
	}
	
	public static final String FIELD_DATE_VALUE = "dateValue";
	public static final String FIELD_BOOLEAN_VALUE = "booleanValue";
	public static final String FIELD_NUMBER_VALUE = "numberValue";
	public static final String FIELD_STRING_VALUE = "stringValue";
	
}
