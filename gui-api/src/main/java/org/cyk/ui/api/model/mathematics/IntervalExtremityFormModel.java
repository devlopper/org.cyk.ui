package org.cyk.ui.api.model.mathematics;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.mathematics.IntervalExtremity;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

@Getter @Setter
public class IntervalExtremityFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputNumber private BigDecimal value;
	@Input @InputBooleanButton private Boolean excluded;
	
	public void set(IntervalExtremity intervalExtremity){
		if(intervalExtremity==null)
			return;
		value = intervalExtremity.getValue();
		excluded = intervalExtremity.getExcluded();
	}
	
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_EXCLUDED = "excluded";
	
}
