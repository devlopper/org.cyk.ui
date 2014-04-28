package org.cyk.ui.web.primefaces.test;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

@Getter @Setter
public class Details {

	@UIField(groups=Client.class,required=true)
	private String name;
	
	@UIField(groups=System.class,required=true)
	private Boolean yesOrNoWrapper=false;
	
}
