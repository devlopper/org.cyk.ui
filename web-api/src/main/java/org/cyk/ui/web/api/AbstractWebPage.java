package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Getter;

import org.cyk.ui.api.AbstractWindow;

public abstract class AbstractWebPage extends AbstractWindow implements IWebPage,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	@Inject @Getter protected WebUIMessageManager messageManager;


	
}
