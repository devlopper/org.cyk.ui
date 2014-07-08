package org.cyk.ui.desktop.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named @Getter
public class DesktopManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private static DesktopManager INSTANCE;
	public static DesktopManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		super.initialisation();
		INSTANCE = this;
	}

}
