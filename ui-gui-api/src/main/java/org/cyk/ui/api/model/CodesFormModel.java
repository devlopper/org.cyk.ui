package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter
public class CodesFormModel implements Serializable {

	private static final long serialVersionUID = -5940707362332267474L;

	@Input @InputText @NotNull private String code;
	
	//@Input @InputBooleanCheck @NotNull private Boolean inputCodeSet;
	@Input @InputTextarea /*@NotNull*/ private String codes;
	@Input @InputText /*@NotNull*/ private String separator=Constant.CHARACTER_COMA.toString();
	
	//@Input @InputBooleanCheck @NotNull private Boolean inputCodeInterval;
	@Input @InputNumber /*@NotNull*/ private Integer fromCode;
	@Input @InputNumber /*@NotNull*/ private Integer toCode;
	@Input @InputNumber /*@NotNull*/ private Integer codeStep=1;
	
	//TODO this code should be moved to enumeration class
	public Set<String> getCodeSet(){
		Set<String> set = new LinkedHashSet<>();
		String codes = StringUtils.remove(this.codes, Constant.LINE_DELIMITER);
		String[] codesSplitted = StringUtils.split(codes, separator);
		if(codesSplitted!=null)
			set.addAll(Arrays.asList(codesSplitted));
		
		if(fromCode!=null && toCode!=null && codeStep!=null)
			for(int index = fromCode; index <= toCode; index = index + codeStep)
				set.add(String.valueOf(index));
		return set;
	}
	
	/**/
	
	public static final String FIELD_CODE = "code";
	public static final String FIELD_INPUT_CODE_SET = "inputCodeSet";
	public static final String FIELD_CODES = "codes";
	public static final String FIELD_SEPARATOR = "separator";
	public static final String FIELD_INPUT_CODE_INTERVAL = "inputCodeInterval";
	public static final String FIELD_FROM_CODE = "fromCode";
	public static final String FIELD_TO_CODE = "toCode";
	public static final String FIELD_CODE_STEP = "codeStep";
	
}
