package org.cyk.ui.web.api.resources.helper;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.cyk.utility.common.Constant.Action;

public class UniformResourceLocatorHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Listener extends org.cyk.utility.common.helper.UniformResourceLocatorHelper.Listener.Adapter.Default {
		private static final long serialVersionUID = 1L;
				
		private static final String VIEW = "View";
		
		@Override
		public String getPathIdentifier(Action action, Class<?> aClass) {
			if(action==null || aClass==null)
				return null;
			return super.getPathIdentifier(action, aClass)+VIEW;
		}
		
		@Override
		public String getPathIdentifierWhenMappingDoesNotExist(Action action, Class<?> aClass) {
			if(action==null || aClass==null)
				return null;
			return "__identifiable__"+super.getPathIdentifierWhenMappingDoesNotExist(action, aClass)+VIEW;
		}
		
		@Override
		public Boolean getIsPathIdentifierMappingExist(String identifier) {
			return getNavigationCase(identifier)!=null;
		}
		
		@Override
		public String getPathIdentifierMapping(String identifier) {
			NavigationCase navigationCase = getNavigationCase(identifier);			
			if(navigationCase==null){
				//System.out.println("UniformResourceLocatorHelper.Listener.getPathIdentifierMapping() NULL : "+identifier);
			}else
				return navigationCase.getToViewId(FacesContext.getCurrentInstance());
			return super.getPathIdentifierMapping(identifier);
		}
		
		protected NavigationCase getNavigationCase(String outcome) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String,Set<NavigationCase>> navigationCaseMap = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCases();
			for(Entry<String,Set<NavigationCase>> entry : navigationCaseMap.entrySet())
				for(NavigationCase navigationCase : entry.getValue())
					if(navigationCase.getFromOutcome().equals(outcome))
						return navigationCase;

			//NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, outcome);			
			return null;
		}
	}
	
	public static class PathStringifierListener extends org.cyk.utility.common.helper.UniformResourceLocatorHelper.PathStringifier.Listener.Adapter.Default {
		
		private static final long serialVersionUID = 1L;

		
	}
	
}
