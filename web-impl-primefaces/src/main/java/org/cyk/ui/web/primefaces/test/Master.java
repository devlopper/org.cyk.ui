package org.cyk.ui.web.primefaces.test;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class Master {

	@UIField(groups=Client.class,description="Master name")
	private String masterName;
	
	@UIField(textArea=true,textRowCount=5,textColumnCount=60,description="Master coments")
	private String textArea;
	
	private Collection<Details> details = new ArrayList<>();
	
}
