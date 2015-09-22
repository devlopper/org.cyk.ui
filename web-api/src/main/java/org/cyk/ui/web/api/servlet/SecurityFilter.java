package org.cyk.ui.web.api.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.security.License;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.web.api.AbstractServletContextListener;
import org.cyk.utility.common.CommonUtils;

public class SecurityFilter extends AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8708257664271661158L;

	private static final String PATH_INSTALL = "install";
	private static final String PATH_LICENSE_EXPIRED = "license/expired";
	private static final String PATH_ACCESS_DENIED = "access/denied";
	
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private UniformResourceLocatorBusiness uniformResourceLocatorBusiness;
	@Inject private LicenseBusiness licenseBusiness;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		AbstractUserSession userSession = userSession(request);
		Collection<UniformResourceLocator> uniformResourceLocators;
		if(userSession==null){
			uniformResourceLocators = AbstractServletContextListener.getUrls();
		}else{
			uniformResourceLocators = AbstractServletContextListener.getUrls(userSession.getUserAccount());
		}
	
		Boolean doFilterChain = Boolean.FALSE;
		
		if(!goTo(applicationBusiness.findCurrentInstance()==null, PATH_INSTALL, request, response)){
			if(Boolean.TRUE.equals(licenseBusiness.getEnabled())){
				License license = applicationBusiness.findCurrentInstance().getLicense();
				if(!goTo(license.getPeriod().getToDate().before(CommonUtils.getInstance().getUniversalTimeCoordinated()), PATH_LICENSE_EXPIRED, request, response))
					doFilterChain = Boolean.TRUE;
				else{
					if(!Boolean.TRUE.equals(license.getExpired()))
						licenseBusiness.expire(license);
				}
			}else
				doFilterChain = Boolean.TRUE;
		}
			
		if(Boolean.TRUE.equals(doFilterChain)){
			if(uniformResourceLocatorBusiness.isAccessible(uniformResourceLocators,url(request)))
				filterChain.doFilter(servletRequest, servletResponse);
			else
				goTo(Boolean.TRUE, PATH_ACCESS_DENIED, request, response);
		}
	}

}
