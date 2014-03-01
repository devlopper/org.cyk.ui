package org.cyk.ui.web.api;

import java.io.Serializable;

import org.cyk.ui.api.AbstractViewContainer;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.cdi.provider.CommonMethodProvider;

public abstract class AbstractWebPage extends AbstractViewContainer implements IWebPage,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	/*
	public AbstractWebPage(CommonMethodProvider commonMethodProvider, CommonUtils commonUtils) {
		super(commonMethodProvider, commonUtils);
	}*/

}
