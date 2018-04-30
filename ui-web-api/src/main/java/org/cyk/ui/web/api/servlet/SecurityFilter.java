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

import org.cyk.system.root.business.api.globalidentification.GlobalIdentifierBusiness;
import org.cyk.system.root.business.api.network.UniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.security.RoleUniformResourceLocatorBusiness;
import org.cyk.system.root.business.api.security.UserAccountBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.utility.common.CommonUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.ListenerUtils;

public class SecurityFilter extends AbstractFilter implements Filter,Serializable {

	private static final long serialVersionUID = 8708257664271661158L;

	static{
		SecurityFilter.Listener.COLLECTION.add(new SecurityFilter.Listener.Adapter.Default());
	}
	
	private static final String PATH_INSTALL = "install";
	private static final String PATH_LICENSE_EXPIRED = "license/expired";
	private static final String PATH_ACCESS_DENIED = "access/denied";
	private static final String PATH_UNREGISTERED = "path/unregistered";
	private static final String ACTION_DENIED = "action/denied";
	
	public static final Map<UniformResourceLocator,UniformResourceLocatorRuntimeConstraint> UNIFORM_RESOURCE_LOCATOR_CONSTRAINTS = new HashMap<>();
	
	@Override
	protected void __filter__(Application application, AbstractUserSession<?,?> userSession, UserAccount userAccount,URL url,HttpServletRequest request, HttpServletResponse response
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
				//System.out.println("SecurityFilter.__filter__() INstalled / Expired ? : "+Boolean.TRUE.equals(application.getLicense().getExpirable()));
				if(Boolean.TRUE.equals(application.getExpirable())){
					if(!goTo(Boolean.TRUE.equals(application.getExpired()) || application.getExistencePeriod().getToDate().before(CommonUtils.getInstance().getUniversalTimeCoordinated())
							, PATH_LICENSE_EXPIRED, request, response))
						doFilterChain = Boolean.TRUE;
					else{
						if(!Boolean.TRUE.equals(application.getExpired())){
							application.setExpired(Boolean.TRUE); 
							inject(ApplicationBusiness.class).update(application);
						}
					}
				}else
					doFilterChain = Boolean.TRUE; 
			}
				
			if(Boolean.TRUE.equals(doFilterChain)){
				if(userSession==null || userAccount==null || inject(UserAccountBusiness.class)
						.hasRole(userAccount, inject(RoleBusiness.class).find(RootConstant.Code.Role.ADMINISTRATOR)) )
					filterChain.doFilter(request, response);
				else {
					if(Boolean.TRUE.equals(isUrlAccessible(url))){
						Boolean isUrlAccessibleByUserAccount = isUrlAccessibleByUserAccount(url,userAccount,request);
						if(Boolean.TRUE.equals(isUrlAccessibleByUserAccount)){
							for(Entry<UniformResourceLocator, UniformResourceLocatorRuntimeConstraint> entry : UNIFORM_RESOURCE_LOCATOR_CONSTRAINTS.entrySet()){
								if(url.getPath().equalsIgnoreCase(Constant.CHARACTER_SLASH+application.getWebContext()+entry.getKey())){
									Boolean v = entry.getValue().isAccessibleByUserAccount(userSession, userAccount,entry.getKey(), request, response);
									if(v!=null)
										isUrlAccessibleByUserAccount = v;
								}
							}
						}
						if(Boolean.TRUE.equals(isUrlAccessibleByUserAccount)){
							Boolean isActionAllowedOnIdentifiableByUserAccount = isActionAllowedOnIdentifiableByUserAccount(url,userAccount,request);
							//System.out.println("isActionAllowedOnIdentifiableByUserAccount : "+isActionAllowedOnIdentifiableByUserAccount);
							if(Boolean.TRUE.equals(isActionAllowedOnIdentifiableByUserAccount))
								filterChain.doFilter(request, response);
							else
								goTo(Boolean.TRUE, ACTION_DENIED, request, response,RedirectType.FORWARD);
						}else
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
	
	private Boolean isUrlAccessibleByUserAccount(final URL url,final UserAccount userAccount,final HttpServletRequest request){
		return listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
			@Override
			public Boolean execute(Listener listener) {
				return listener.isUrlAccessibleByUserAccount(url,userAccount,request);
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
	}
	
	/**which operation he is willing to do ? Create , Read , Update , Delete , ... And what is the object ? class , instance , ...
	 * 
	 * @param url
	 * @param userAccount
	 * @param request
	 * @return
	 */
	private Boolean isActionAllowedOnIdentifiableByUserAccount(final URL url,final UserAccount userAccount,final HttpServletRequest request){
		return listenerUtils.getBoolean(Listener.COLLECTION, new ListenerUtils.BooleanMethod<Listener>() {
			@Override
			public Boolean execute(Listener listener) {
				CommonBusinessAction commonBusinessAction = listener.getCommonBusinessAction(url, request);
				if(commonBusinessAction==null)
					return Boolean.TRUE;
				Class<? extends AbstractIdentifiable> clazz = listener.getIdentifiableClass(url, request);
				if(clazz==null)
					return Boolean.TRUE;
				AbstractIdentifiable identifiable = listener.getIdentifiableInstance(url, request);
				if(identifiable==null && CommonBusinessAction.CREATE.equals(commonBusinessAction))
					return inject(GlobalIdentifierBusiness.class).isCreatable(clazz);
				//System.out.println(commonBusinessAction+" , "+clazz.getSimpleName()+" , "+identifiable);
				switch(commonBusinessAction){
				case READ: return inject(GlobalIdentifierBusiness.class).isReadable(identifiable);
				case UPDATE: return inject(GlobalIdentifierBusiness.class).isUpdatable(identifiable);
				case DELETE: return inject(GlobalIdentifierBusiness.class).isDeletable(identifiable);
				default: return Boolean.TRUE;
				}
			}
			@Override
			public Boolean getNullValue() {
				return Boolean.TRUE;
			}
		});
	}
			
	public static interface Listener extends AbstractListener {
		
		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Boolean isUrlFiltered();
		Boolean isUrlAccessible(URL url);
		Boolean isUrlAccessibleByUserAccount(URL url,UserAccount userAccount,HttpServletRequest request);
		
		CommonBusinessAction getCommonBusinessAction(URL url,HttpServletRequest request);
		Class<? extends AbstractIdentifiable> getIdentifiableClass(URL url,HttpServletRequest request);
		AbstractIdentifiable getIdentifiableInstance(URL url,HttpServletRequest request);
		
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
			
			@Override
			public CommonBusinessAction getCommonBusinessAction(URL url,HttpServletRequest request) {
				return null;
			}

			@Override
			public Class<? extends AbstractIdentifiable> getIdentifiableClass(URL url,HttpServletRequest request) {
				return null;
			}

			@Override
			public AbstractIdentifiable getIdentifiableInstance(URL url,HttpServletRequest request) {
				return null;
			}
			
			/**/
			
			public static class Default extends Listener.Adapter implements Serializable{

				private static final long serialVersionUID = -2057765010522840493L;
				
				@Override
				public Boolean isUrlFiltered() {
					return Boolean.TRUE.equals(RootBusinessLayer.getInstance().getApplication().getUniformResourceLocatorFiltered());
				}
				
				@Override
				public Boolean isUrlAccessible(URL url) {
					return inject(UniformResourceLocatorBusiness.class).isAccessible(url);
				}
				
				@Override
				public Boolean isUrlAccessibleByUserAccount(URL url, UserAccount userAccount,HttpServletRequest request) {
					return inject(RoleUniformResourceLocatorBusiness.class).isAccessibleByUserAccount(url,userAccount);
				}
			
				@Override
				public CommonBusinessAction getCommonBusinessAction(URL url,HttpServletRequest request) {
					return UniformResourceLocatorParameterBusinessImpl.getCommonBusinessAction(url, request.getParameterMap());
				}
				
				@Override
				public Class<? extends AbstractIdentifiable> getIdentifiableClass(URL url,HttpServletRequest request) {
					return UniformResourceLocatorParameterBusinessImpl.getIdentifiableClass(url, request.getParameterMap());
				}
				
				@Override
				public AbstractIdentifiable getIdentifiableInstance(URL url,HttpServletRequest request) {
					return UniformResourceLocatorParameterBusinessImpl.getIdentifiableInstance(url, request.getParameterMap());
				}
			}
			
		}
	}

	/**/
	
	public static void addUniformResourceLocatorRuntimeConstraint(UniformResourceLocator uniformResourceLocator,UniformResourceLocatorRuntimeConstraint uniformResourceLocatorRuntimeConstraint){
		UNIFORM_RESOURCE_LOCATOR_CONSTRAINTS.put(uniformResourceLocator, uniformResourceLocatorRuntimeConstraint);
	}
	
	public static interface UniformResourceLocatorRuntimeConstraint {
	
		Boolean isAccessibleByUserAccount(AbstractUserSession<?,?> userSession,UserAccount userAccount,UniformResourceLocator uniformResourceLocator,HttpServletRequest request, HttpServletResponse response);
		
	}
}
