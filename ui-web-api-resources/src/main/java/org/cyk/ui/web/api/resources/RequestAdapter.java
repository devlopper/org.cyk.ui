package org.cyk.ui.web.api.resources;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.cyk.utility.common.userinterface.RequestHelper;

public class RequestAdapter extends RequestHelper.Listener.Adapter.Default implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Object getParameter(Object request, String name) {
		if(request instanceof HttpServletRequest)
			return ((HttpServletRequest)request).getParameter(name);
		return super.getParameter(request, name);
	}
	
}
