package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.ui.web.api.AbstractWebManager;

public abstract class AbstractPrimefacesManager extends AbstractWebManager implements Serializable {

	private static final long serialVersionUID = 5307129721480611811L;

	@Inject protected DefaultDesktopLayoutManager layoutManager;
	
	public String getLibraryName(){
		return "org.cyk.ui.web.primefaces."+identifier;
	}
}
