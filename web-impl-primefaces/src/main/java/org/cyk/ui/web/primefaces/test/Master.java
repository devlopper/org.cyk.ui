package org.cyk.ui.web.primefaces.test;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.UIField;
import org.cyk.utility.common.validation.Client;

@Getter @Setter
public class Master {

	@UIField(groups=Client.class,description="Master name")
	@NotNull
	private String masterName;
	
	@UIField(textArea=true,textRowCount=5,description="Master coments")
	private String textArea;
	
	@UIField
	private Float aFloat;
	
	@UIField(manyRelationshipClass=Details.class)
	private Collection<Details> details = new ArrayList<>();
	
}
