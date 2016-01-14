package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.utility.common.CommonUtils;

public class SecurityFilter extends AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8708257664271661158L;

	private static final String PATH_INSTALL = "install";
	private static final String PATH_LICENSE_EXPIRED = "license/expired";
	private static final String PATH_ACCESS_DENIED = "access/denied";
	private static final String PATH_UNREGISTERED = "path/unregistered";
	
	@Inject private UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
	@Inject private RoleUniformResourceLocatorBusiness roleUniformResourceLocatorBusiness;
	@Inject private UserAccountBusiness userAccountBusiness;
	@Inject private LicenseBusiness licenseBusiness;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		AbstractUserSession userSession = userSession(request);
		//Collection<UniformResourceLocator> uniformResourceLocators;
		UserAccount userAccount;
		if(userSession==null){
			userAccount= null;
		}else{
			/*if(Boolean.TRUE.equals(userSession.getIsAdministrator())){
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}*/
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
	
		
	}

}
