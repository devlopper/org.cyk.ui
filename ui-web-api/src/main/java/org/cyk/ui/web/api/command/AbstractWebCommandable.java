package org.cyk.ui.web.api.command;

import java.io.Serializable;

import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.web.api.WebNavigationManager;

public abstract class AbstractWebCommandable extends AbstractCommandable implements WebCommandable, Serializable {

	private static final long serialVersionUID = 2790740273836250477L;

	@Override
	public UICommandable addPreviousViewParameter() {
		UICommandable commandable = super.addDefaultParameters();
		commandable.addParameter(UniformResourceLocatorParameter.PREVIOUS_URL, WebNavigationManager.getInstance().getRequestUrl());
		return commandable;
	}
	
	@Override
	public UICommandable addDefaultParameters() {
		addPreviousViewParameter();
		return this;
	}
	
}
