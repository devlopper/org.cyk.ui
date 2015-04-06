package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8855562067264528963L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	protected Boolean redirect(Boolean condition,String relativeUrl,HttpServletRequest request,HttpServletResponse response) throws IOException{
        if(Boolean.TRUE.equals(condition))
        	response.sendRedirect(request.getContextPath()+"/"+relativeUrl);
        return Boolean.TRUE.equals(condition);
    }
	
	@Override
	public void destroy() {}
	
}
