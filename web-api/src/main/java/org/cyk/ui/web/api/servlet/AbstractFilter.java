package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.ui.web.api.NavigationHelper;

public abstract class AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8855562067264528963L;

	protected static enum RedirectType{REDIRECT,FORWARD}
	
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
	
}
