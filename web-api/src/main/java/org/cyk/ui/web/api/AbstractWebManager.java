package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractWebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 406884223652214395L;

	/**/
	
	/**/
	
	public abstract Collection<UICommandable> businessMenuItems(UserSession userSession);
	
	public abstract Collection<Class<? extends AbstractIdentifiable>> parameterMenuItemClasses(UserSession userSession);
	
}
