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

import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.LicenseBusiness;
import org.cyk.system.root.model.security.License;
import org.cyk.utility.common.CommonUtils;

public class SecurityFilter extends AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8708257664271661158L;

	public static Boolean LICENSE_ENABLED = Boolean.FALSE;
	
	@Inject private ApplicationBusiness applicationBusiness;
	@Inject private LicenseBusiness licenseBusiness;
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		if(applicationBusiness.isAccessible(url(request))){
			if(!goTo(applicationBusiness.findCurrentInstance()==null, "install", request, response)){
				if(Boolean.TRUE.equals(LICENSE_ENABLED)){
					License license = applicationBusiness.findCurrentInstance().getLicense();
					if(!goTo(license.getPeriod().getToDate().before(CommonUtils.getInstance().getUniversalTimeCoordinated()), "license/expired", request, response))
						filterChain.doFilter(servletRequest, servletResponse);
					else{
						if(!Boolean.TRUE.equals(license.getExpired()))
							licenseBusiness.expire(license);
					}
				}else
					filterChain.doFilter(servletRequest, servletResponse);
			}	
		}else{
			goTo(Boolean.TRUE, "access/denied", request, response);
		}
	}

}
