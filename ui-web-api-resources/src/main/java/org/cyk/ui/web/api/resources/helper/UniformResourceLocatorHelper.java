package org.cyk.ui.web.api.resources.helper;

import java.io.Serializable;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.common.Constant.Action;

public class UniformResourceLocatorHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.UniformResourceLocatorHelper.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;
				
		@Override
		public String getPathIdentifier(Action action, Class<?> aClass) {
			if(action==null || aClass==null)
				return null;
			return super.getPathIdentifier(action, aClass)+"View";
		}
		
		@Override
		public String getPathIdentifierMapping(String identifier) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler())
					.getNavigationCase(facesContext, null, identifier);			
			if(navigationCase==null){
				
			}else
				return navigationCase.getToViewId(facesContext);
			return super.getPathIdentifierMapping(identifier);
		}
	}
	
	public static class PathStringifierListener extends org.cyk.utility.common.helper.UniformResourceLocatorHelper.PathStringifier.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		
	}
	
}
