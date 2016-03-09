package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;

public abstract class AbstractFilter extends AbstractBean implements Filter,Serializable {

	private static final long serialVersionUID = 8855562067264528963L;

	protected static enum RedirectType{REDIRECT,FORWARD}
	
	@Inject protected WebNavigationManager navigationManager;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(Boolean.FALSE);
		AbstractUserSession userSession = (AbstractUserSession) (session == null ? null : session.getAttribute(WebManager.getInstance().getSessionAttributeUserSession()));
		UserAccount userAccount;
		if(userSession==null){
			userAccount= null;
		}else{
			userAccount = userSession.getUserAccount();
		}
		Application application = RootBusinessLayer.getInstance().getApplication();
		URL url = null;
		try {
			url = new URL(request.getRequestURL().toString()+(StringUtils.isBlank(request.getQueryString())?Constant.EMPTY_STRING:"?"+request.getQueryString()) );
		} catch (MalformedURLException e) {
			logThrowable(e);
		}
		__filter__(application, userSession, userAccount,url, request, response, filterChain);
		
	}
	
	protected void __filter__(Application application, AbstractUserSession userSession, UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response
			, FilterChain filterChain) throws IOException, ServletException {	
		Collection<AbstractListener> listeners = getListeners();
		if(listeners!=null)
			for(AbstractListener listener : getListeners())
				listener.filter(application, userSession, userAccount,url, request, response, filterChain);
	}
	
	protected Collection<AbstractListener> getListeners(){
		return null;
	}
	
	protected static Boolean goTo(Boolean condition,String relativeUrl,HttpServletRequest request,HttpServletResponse response,RedirectType redirectType) throws IOException{
		relativeUrl = (relativeUrl.startsWith(Constant.CHARACTER_SLASH.toString())?relativeUrl:Constant.CHARACTER_SLASH+relativeUrl);
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
	
	protected static Boolean goTo(Boolean condition,String relativeUrl,HttpServletRequest request,HttpServletResponse response) throws IOException{
		return goTo(condition, relativeUrl, request, response,RedirectType.REDIRECT);
	}
	
	@Override
	public void destroy() {}
		
	/**/
	
	public static interface AbstractListener {
		
		void filter(Application application,AbstractUserSession userSession,UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;
		
		/**/
		
		public static class Adapter extends BeanAdapter implements AbstractListener,Serializable{

			private static final long serialVersionUID = -2057765010522840493L;

			protected WebManager webManager = WebManager.getInstance();
			
			protected UserAccountBusiness userAccountBusiness = RootBusinessLayer.getInstance().getUserAccountBusiness();
			protected LicenseBusiness licenseBusiness = RootBusinessLayer.getInstance().getLicenseBusiness();
			protected UniformResourceLocatorBusiness uniformResourceLocatorBusiness = RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness();
			protected RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness = RootBusinessLayer.getInstance().getRoleUniformResourceLocatorBusiness();
			
			@Override
			public void filter(Application application, AbstractUserSession userSession, UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response
					, FilterChain filterChain) throws IOException, ServletException {	
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{

				private static final long serialVersionUID = -2057765010522840493L;
				
			}
		}
	}
	
}
