package org.cyk.system.test.model.actor;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Details {

	private String name;
	
	private BigDecimal salary;
	
	private Boolean yesOrNoWrapper=false;
	
}
