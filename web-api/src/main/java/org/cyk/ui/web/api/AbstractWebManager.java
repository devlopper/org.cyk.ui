package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.ui.api.AbstractApplicationUIManager;
import org.cyk.ui.web.api.security.RoleManager;

public abstract class AbstractWebManager extends AbstractApplicationUIManager implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	@Inject protected WebNavigationManager webNavigationManager;
	@Inject protected WebManager webManager;
	@Inject protected RoleManager roleManager;
	
}
