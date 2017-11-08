package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton
public class WebJavascriptHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static WebJavascriptHelper INSTANCE;
	
	public static WebJavascriptHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new WebJavascriptHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	
}
