package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.ui.web.api.AbstractServletContextListener;

public abstract class AbstractContextListener extends AbstractServletContextListener implements Serializable {

	private static final long serialVersionUID = 592943227142026384L;
	
	@Inject protected DefaultDesktopLayoutManager defaultDesktopLayoutManager;

}
