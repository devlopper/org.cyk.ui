package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

import lombok.Getter;
import lombok.Setter;

public class Window extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 7282005324574303823L;
	
	@Getter @Setter protected String title,contentTitle="Content";
	@Getter @Setter protected String text;
	@Getter @Setter protected String actionIdentifier;
	

}
