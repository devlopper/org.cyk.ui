package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.cyk.ui.web.api.UserAgentInfo;
import org.cyk.ui.web.api.WebNavigationManager;

public class UserAgentFilter extends AbstractFilter implements Serializable {

	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_USER_AGENT = "user-agent";
	private static final long serialVersionUID = -7275934377277008585L;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String userAgent = request.getHeader(HEADER_USER_AGENT);
	    String accept = request.getHeader(HEADER_ACCEPT);

        if (userAgent != null && accept != null && new UserAgentInfo(userAgent, accept).isMobileDevice()){
        	if(navigationManager.isMobileView(request))
        		filterChain.doFilter(servletRequest, servletResponse);
        	else{
        		StringBuilder relativeUrl = new StringBuilder(StringUtils.defaultString(WebNavigationManager.MOBILE_VIEW_MAP.get(request.getRequestURI())));
        		if(StringUtils.isBlank(relativeUrl))
        			relativeUrl = new StringBuilder(StringUtils.replaceOnce(request.getRequestURI(), request.getContextPath(), WebNavigationManager.MOBILE_AGENT_FOLDER));
        
        		navigationManager.appendQueries(relativeUrl, request);
        			
        		//System.out.println("UserAgentFilter.doFilter() : "+relativeUrl);
        		goTo(Boolean.TRUE, relativeUrl.toString(), request, response);
        	}
        }else
        	filterChain.doFilter(servletRequest, servletResponse);

	}

}
