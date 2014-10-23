package org.cyk.ui.test.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Details {

	private String name;
	
	private BigDecimal salary;
	
	private Boolean yesOrNoWrapper=false;
	
}
