package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWindow extends AbstractBean implements UIWindow,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Getter protected String title;
	@Inject @Getter protected UIManager uiManager;

	
}
