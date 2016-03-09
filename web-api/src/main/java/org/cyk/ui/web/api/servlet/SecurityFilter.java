package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;

public class SecurityFilter extends AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8708257664271661158L;

	private static final String PATH_INSTALL = "install";
	private static final String PATH_LICENSE_EXPIRED = "license/expired";
	private static final String PATH_ACCESS_DENIED = "access/denied";
	private static final String PATH_UNREGISTERED = "path/unregistered";
	
	public static final Map<String,UrlConstraint> URL_CONSTRAINTS = new HashMap<>();
	
	/*
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		AbstractUserSession userSession = userSession(request);
		UserAccount userAccount;
		if(userSession==null){
			userAccount= null;
		}else{
			userAccount = userSession.getUserAccount();
		}
		
		Application application = getApplication();
		if(userAccount==null || userSession.getIsAdministrator() || (application!=null && Boolean.FALSE.equals(application.getUniformResourceLocatorFilteringEnabled()))){
			filterChain.doFilter(servletRequest, servletResponse);
		}else{
			Boolean doFilterChain = Boolean.FALSE;
			if(!goTo(application==null, PATH_INSTALL, request, response)){
				if(Boolean.TRUE.equals(application.getLicense().getExpirable())){
					if(!goTo(Boolean.TRUE.equals(application.getLicense().getExpired()) || application.getLicense().getPeriod().getToDate().before(CommonUtils.getInstance().getUniversalTimeCoordinated())
							, PATH_LICENSE_EXPIRED, request, response))
						doFilterChain = Boolean.TRUE;
					else{
						if(!Boolean.TRUE.equals(application.getLicense().getExpired())){
							application.getLicense().setExpired(Boolean.TRUE);
							licenseBusiness.update(application.getLicense());
						}
					}
				}else
					doFilterChain = Boolean.TRUE;
			}
				
			if(Boolean.TRUE.equals(doFilterChain)){
				if(userSession==null || userAccount==null || userAccountBusiness.hasRole(userAccount, RootBusinessLayer.getInstance().getRoleAdministrator()) )
					filterChain.doFilter(servletRequest, servletResponse);
				else if( uniformResourceLocatorBusiness.isAccessible(url(request)) ){
					if( roleUniformResourceLocatorBusiness.isAccessibleByUserAccount(url(request),userAccount) )
						filterChain.doFilter(servletRequest, servletResponse);
					else
						goTo(Boolean.TRUE, PATH_ACCESS_DENIED, request, response,RedirectType.FORWARD);
				}else{
					goTo(Boolean.TRUE, PATH_UNREGISTERED, request, response,RedirectType.FORWARD);
					//goTo(Boolean.TRUE, PATH_ACCESS_DENIED, request, response,RedirectType.FORWARD);
				}
			}
		}	
	}*/
	
	@Override
	protected void __filter__(Application application, AbstractUserSession userSession, UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response
			, FilterChain filterChain)throws IOException, ServletException {
		Boolean isUrlFiltered = Boolean.TRUE;
		for(Listener listener : Listener.COLLECTION){
			Boolean v = listener.isUrlFiltered();
			if(v!=null)
				isUrlFiltered = v;
		}
		if(userAccount==null || userSession.getIsAdministrator() || (application!=null && Boolean.FALSE.equals(isUrlFiltered))){
			filterChain.doFilter(request, response);
		}else{
			Boolean doFilterChain = Boolean.FALSE;
			if(!goTo(application==null, PATH_INSTALL, request, response)){
				if(Boolean.TRUE.equals(application.getLicense().getExpirable())){
					if(!goTo(Boolean.TRUE.equals(application.getLicense().getExpired()) || application.getLicense().getPeriod().getToDate().before(CommonUtils.getInstance().getUniversalTimeCoordinated())
							, PATH_LICENSE_EXPIRED, request, response))
						doFilterChain = Boolean.TRUE;
					else{
						if(!Boolean.TRUE.equals(application.getLicense().getExpired())){
							application.getLicense().setExpired(Boolean.TRUE); 
							RootBusinessLayer.getInstance().getLicenseBusiness().update(application.getLicense());
						}
					}
				}else
					doFilterChain = Boolean.TRUE; 
			}
				
			if(Boolean.TRUE.equals(doFilterChain)){
				if(userSession==null || userAccount==null || RootBusinessLayer.getInstance().getUserAccountBusiness()
						.hasRole(userAccount, RootBusinessLayer.getInstance().getRoleAdministrator()) )
					filterChain.doFilter(request, response);
				else {
					if(Boolean.TRUE.equals(isUrlAccessible(url))){
						Boolean isUrlAccessibleByUserAccount = isUrlAccessibleByUserAccount(url,userAccount,request);
						if(Boolean.TRUE.equals(isUrlAccessibleByUserAccount)){
							for(Entry<String, UrlConstraint> entry : URL_CONSTRAINTS.entrySet()){
								if(url.getPath().equalsIgnoreCase(Constant.CHARACTER_SLASH+application.getWebContext()+entry.getKey())){
									Boolean v = entry.getValue().isAccessAllowed(userSession, userAccount, url, request, response);
									if(v!=null)
										isUrlAccessibleByUserAccount = v;
								}
							}
						}
						if(Boolean.TRUE.equals(isUrlAccessibleByUserAccount))
							filterChain.doFilter(request, response);
						else
							goTo(Boolean.TRUE, PATH_ACCESS_DENIED, request, response,RedirectType.FORWARD);
					}else{
						goTo(Boolean.TRUE, PATH_UNREGISTERED, request, response,RedirectType.FORWARD);
						//goTo(Boolean.TRUE, PATH_ACCESS_DENIED, request, response,RedirectType.FORWARD);
					}
				}
			}
		}	
	}
	
	private Boolean isUrlAccessible(URL url){
		Boolean value = Boolean.TRUE;
		for(Listener listener : Listener.COLLECTION){
			Boolean v = listener.isUrlAccessible(url);
			if(v!=null)
				value = v;
		}
		return value;
	}
	
	private Boolean isUrlAccessibleByUserAccount(URL url,UserAccount userAccount,HttpServletRequest request){
		Boolean value = Boolean.TRUE;
		for(Listener listener : Listener.COLLECTION){
			Boolean v = listener.isUrlAccessibleByUserAccount(url,userAccount,request);
			if(v!=null)
				value = v;
		}
		return value;
	}
	
	public static interface Listener extends AbstractListener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Boolean isUrlFiltered();
		Boolean isUrlAccessible(URL url);
		Boolean isUrlAccessibleByUserAccount(URL url,UserAccount userAccount,HttpServletRequest request);
		
		/**/
		
		public static class Adapter extends AbstractListener.Adapter implements Listener,Serializable{

			private static final long serialVersionUID = -2057765010522840493L;
		
			@Override
			public Boolean isUrlFiltered() {
				return null;
			}
			
			@Override
			public Boolean isUrlAccessible(URL url) {
				return null;
			}

			@Override
			public Boolean isUrlAccessibleByUserAccount(URL url, UserAccount userAccount,HttpServletRequest request) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{

				private static final long serialVersionUID = -2057765010522840493L;
				
				@Override
				public Boolean isUrlFiltered() {
					return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplication().getUniformResourceLocatorFilteringEnabled());
				}
				
				@Override
				public Boolean isUrlAccessible(URL url) {
					return RootBusinessLayer.getInstance().getUniformResourceLocatorBusiness().isAccessible(url);
				}
				
				@Override
				public Boolean isUrlAccessibleByUserAccount(URL url, UserAccount userAccount,HttpServletRequest request) {
					return RootBusinessLayer.getInstance().getRoleUniformResourceLocatorBusiness().isAccessibleByUserAccount(url,userAccount);
				}
			
			}

			
		}
	}

	/**/
	
	public static interface UrlConstraint {
		
		Boolean isAccessAllowed(AbstractUserSession userSession,UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response);
		
	}
}
