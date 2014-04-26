package org.cyk.ui.web.primefaces.test;

import org.cyk.utility.common.annotation.FormField;
import org.cyk.utility.common.validation.Client;
import org.cyk.utility.common.validation.System;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Details {

	@FormField(groups=Client.class,required=true)
	private String name;
	
	@FormField(groups=System.class,required=true)
	private Boolean yesOrNoWrapper=false;
	
}
