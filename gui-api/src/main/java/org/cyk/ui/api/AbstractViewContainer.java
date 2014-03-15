package org.cyk.ui.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractViewContainer extends AbstractBean implements UIContainer,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	@Getter protected String title;
	@Getter protected Collection<UIView> views = new LinkedList<>();
	@Getter protected Object objectModel;

	@Inject @Named(value="uiManager") @Getter protected UIManager uiManager;
	
}
