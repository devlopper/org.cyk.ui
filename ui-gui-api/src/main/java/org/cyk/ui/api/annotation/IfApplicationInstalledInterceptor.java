package org.cyk.ui.api.annotation;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@IfApplicationInstalled
public class IfApplicationInstalledInterceptor implements Serializable {

	private static final long serialVersionUID = 8971873650552369574L;

	//@Inject private ApplicationBusiness applicationBusiness;
	
	@AroundInvoke
	public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
		
		return invocationContext.proceed();
	}
}