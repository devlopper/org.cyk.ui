package org.cyk.ui.web.api.resources.helper;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.omnifaces.util.Faces;

public class RequestHelper {

	public static class Listener extends org.cyk.utility.common.userinterface.RequestHelper.Listener.Adapter.Default implements Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public Object get() {
			return Faces.getRequest();
		}
		
		@Override
		public Object getParameter(Object request, String name) {
			if(request instanceof HttpServletRequest)
				return ((HttpServletRequest)request).getParameter(name);
			return super.getParameter(request, name);
		}
		
		@Override
		public String getUniformResourceLocator(Object request) {
			if(request instanceof HttpServletRequest)
				return ((HttpServletRequest)request).getRequestURL().toString();
			return super.getUniformResourceLocator(request);
		}
		
	}
	
}
