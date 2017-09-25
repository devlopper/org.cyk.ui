package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.omnifaces.util.Faces;

@Singleton
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
	
	/**/
	
	public static class Listener extends org.cyk.utility.common.userinterface.ViewHelper.Listener.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		public String getParameter(String name) {
			return Faces.getRequestParameter(name);
		}
		
	}
	
}
