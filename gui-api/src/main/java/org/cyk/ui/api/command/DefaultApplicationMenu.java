package org.cyk.ui.api.command;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DefaultApplicationMenu extends AbstractMenu implements UIApplicationMenu,Serializable {

	private static final long serialVersionUID = 4331240830505008164L;
	
	private UICommand administrationCommand = new DefaultCommand();
	
	public void addParameterClass(Class<?> aClass){
		
	}

}
