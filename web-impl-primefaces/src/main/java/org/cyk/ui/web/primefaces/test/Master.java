package org.cyk.ui.web.primefaces.test;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.common.annotation.FormField;
import org.cyk.utility.common.validation.Client;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Master {

	@FormField(groups=Client.class,required=true,description="Master name")
	private String masterName;
	
	@FormField(textArea=true,required=true,textRowCount=5,textColumnCount=60,description="Master coments")
	private String textArea;
	
	private Collection<Details> details = new ArrayList<>();
	
}
