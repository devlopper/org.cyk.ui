package org.cyk.ui.desktop.api;

import java.io.Serializable;

import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;

@Deployment(initialisationType=InitialisationType.EAGER)
public class DesktopNavigationManager extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;
	
	private static DesktopNavigationManager INSTANCE;
	public static DesktopNavigationManager getInstance() {
		return INSTANCE;
	}
		
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
}
