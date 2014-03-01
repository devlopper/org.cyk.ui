package org.cyk.ui.api;

import java.io.Serializable;

import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.cdi.provider.CommonMethodProvider;

public abstract class AbstractViewContainer extends AbstractView implements IViewContainer,Serializable {

	private static final long serialVersionUID = 7282005324574303823L;

	/*
	public AbstractViewContainer(CommonMethodProvider commonMethodProvider, CommonUtils commonUtils) {
		super(commonMethodProvider, commonUtils);
	}*/
}
