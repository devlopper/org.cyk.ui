package org.cyk.ui.web.primefaces.test;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

@Getter @Setter
public class Details {

	@UIField(groups=Client.class)
	private String name;
	
	@UIField(groups=Client.class)
	private BigDecimal salary;
	
	@UIField(groups=System.class)
	private Boolean yesOrNoWrapper=false;
	
}
