package org.cyk.ui.api;

import java.io.Serializable;

import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;

@Singleton @Named
public class ViewHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static ViewHelper INSTANCE;
	
	public static ViewHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ViewHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getParameterNameInputValueIsNotRequired(){
		return org.cyk.ui.api.Constant.INPUT_VALUE_IS_NOT_REQUIRED;
	}
	
	public String getParameterValueInputValueIsNotRequired(){
		return org.cyk.utility.common.userinterface.ViewHelper.getInstance().getParameter(getParameterNameInputValueIsNotRequired());
	}
	
	/**/
	
}
