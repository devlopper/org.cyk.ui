package org.cyk.ui.web.api.servlet;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.business.api.GenericBusiness;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;

public abstract class AbstractServlet extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 2265523854362373567L;
	
	@Inject protected GenericBusiness genericBusiness;
	
	@Inject protected UIManager uiManager;
	@Inject protected WebManager webManager;
	@Inject protected WebNavigationManager navigationManager;
	
	protected String requestParameter(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
	
	protected Class<?> classParameter(HttpServletRequest request){
		return uiManager.classFromKey(request.getParameter(webManager.getRequestParameterClass())).getClazz();
	}
	
	protected AbstractIdentifiable identifiableParameter(HttpServletRequest request,Class<AbstractIdentifiable> aClass){
		return genericBusiness.use(aClass).find(Long.parseLong(requestParameter(request,webManager.getRequestParameterIdentifiable())));
	}
	
}
