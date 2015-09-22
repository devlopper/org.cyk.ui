package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.web.api.AbstractWebUserSession;
import org.cyk.ui.web.api.NavigationHelper;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractFilter extends AbstractBean implements Filter,Serializable {

	private static final long serialVersionUID = 8855562067264528963L;

	protected static enum RedirectType{REDIRECT,FORWARD}
	
	@Inject protected WebNavigationManager navigationManager;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	protected Boolean goTo(Boolean condition,String relativeUrl,HttpServletRequest request,HttpServletResponse response,RedirectType redirectType) throws IOException{
		relativeUrl = (relativeUrl.startsWith(NavigationHelper.SLASH)?relativeUrl:NavigationHelper.SLASH+relativeUrl);
		if(Boolean.TRUE.equals(condition))
        	if(redirectType==null || RedirectType.REDIRECT.equals(redirectType))
        		response.sendRedirect(request.getContextPath()+relativeUrl);
        	else if(RedirectType.FORWARD.equals(redirectType)){
				try {
					request.getRequestDispatcher(relativeUrl).forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				}
        	}
        return Boolean.TRUE.equals(condition);
    }
	
	protected Boolean goTo(Boolean condition,String relativeUrl,HttpServletRequest request,HttpServletResponse response) throws IOException{
		return goTo(condition, relativeUrl, request, response,RedirectType.REDIRECT);
	}
	
	@Override
	public void destroy() {}
	
	protected URL url(HttpServletRequest request){
		try {
			return new URL(request.getRequestURL().toString()+(StringUtils.isBlank(request.getQueryString())?Constant.EMPTY_STRING:"?"+request.getQueryString()) );
		} catch (MalformedURLException e) {
			logThrowable(e);
			return null;
		}
	}
	
	protected AbstractUserSession userSession(HttpServletRequest request){
		HttpSession session = request.getSession(Boolean.FALSE);
		return (AbstractWebUserSession) (session == null ? null : session.getAttribute(WebManager.getInstance().getSessionAttributeUserSession()));
	}
	
}
