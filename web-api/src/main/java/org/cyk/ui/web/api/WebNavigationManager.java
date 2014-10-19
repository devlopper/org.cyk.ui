package org.cyk.ui.web.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.omnifaces.util.Faces;

@Singleton @Named @Log @Deployment(initialisationType=InitialisationType.EAGER)
public class WebNavigationManager extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;
	
	private static WebNavigationManager INSTANCE;
	public static WebNavigationManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 */
	private static final String OUTCOME_NOT_FOUND = "outcomenotfound";
	/**
	 * We stay on the same view after action
	 */
	public static final String OUTCOME_CURRENT_VIEW = null;
	/**
	 * We move to the success view after action
	 */
	public static final String OUTCOME_SUCCESS_VIEW = "succes";
	
	private static final String QUERY_PARAMETER_FACES_REDIRECT_NAME = "faces-redirect";
	private static final String FILE_STATIC_EXTENSION = ".xhtml";
	private static final String FILE_PROCESSING_EXTENSION = ".jsf";
	
	@Getter private String outcomePublicIndex = "publicindex";
	@Getter private String outcomePrivateIndex = "privateindex";
	
	@Getter private String outcomeDynamicCrudOne = "dynamicCrudOne";
	@Getter private String outcomeDynamicCrudMany = "dynamicCrudMany";
	@Getter private String outcomeLogout = "useraccountlogout";
	
	@Getter private String outcomeToolsCalendar = "toolscalendar";
	
	@Getter private String outcomeDeploymentManagement = "deploymentmanagement";
	
	@Inject private NavigationHelper navigationHelper;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String editorCreateUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudOne:businessEntityInfos.getUiEditViewId(), new Object[]{
				WebManager.getInstance().getRequestParameterClass(),UIManager.getInstance().keyFromClass(businessEntityInfos)
				,UIManager.getInstance().getCrudParameter(),UIManager.getInstance().getCrudCreateParameter()
			});
	}
	
	public String editorCreateUrl(BusinessEntityInfos businessEntityInfos){
		return editorCreateUrl(businessEntityInfos, Boolean.TRUE);
	}
	
	public String editorUrl(Long identifier,String crud){
		return url(outcomeDynamicCrudOne, new Object[]{
				WebManager.getInstance().getRequestParameterIdentifiable(),identifier
				,UIManager.getInstance().getCrudParameter(),crud
			});
	}
	
	public String url(String id,Object[] parameters,Boolean actionOutcome,Boolean partial){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, id);
		//System.out.println(id+" / "+navigationCase);
		if(navigationCase==null){
			log.severe("No Navigation Case found for "+id);
			return url(OUTCOME_NOT_FOUND, new Object[]{"oc",id},Boolean.FALSE,Boolean.FALSE);
		}
		String s = navigationCase.getToViewId(facesContext);
		StringBuilder url;
		if(Boolean.TRUE.equals(actionOutcome))
			url = new StringBuilder(s);
		else
			url = new StringBuilder(StringUtils.replace(s, FILE_STATIC_EXTENSION, FILE_PROCESSING_EXTENSION));
	    
		if(Boolean.TRUE.equals(actionOutcome))
	    	navigationHelper.addParameter(url, QUERY_PARAMETER_FACES_REDIRECT_NAME, navigationCase.isRedirect());
	    if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
	    		if(parameters[i+1]==null)
	    			;
	    		else
	    			navigationHelper.addParameter(url, (String) parameters[i], parameters[i+1]);
	    }
	    if(Boolean.TRUE.equals(partial))
	    	return url.toString();
	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getServletContext().getContextPath()+url;
	}
	
	public String url(String id,Object[] parameters,Boolean actionOutcome){
		return url(id, parameters, actionOutcome, Boolean.TRUE);
	}
	
	public String url(String id,Object[] parameters){
		return url(id, parameters, Boolean.TRUE);
	}
	
	public String url(String id,Boolean actionOutcome){
		return url(id, null,actionOutcome);
	}
	
	public String url(String id){
		return url(id, Boolean.TRUE);
	}
		
	public String getRequestUrl(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//System.out.println(request.getQueryString());
		String url = request.getRequestURL().toString();
		if(StringUtils.isNotEmpty(request.getQueryString()))
			url += NavigationHelper.QUERY_START+request.getQueryString();
		return url;
	}
	
	public void redirectTo(String outcome,Object[] parameters){
		redirectToUrl(url(outcome,parameters,Boolean.FALSE,Boolean.FALSE));
	}
	public void redirectTo(String outcome){
		redirectTo(outcome, null);
	}
	
	public void redirectToDynamicCrudMany(Class<AbstractIdentifiable> dataClass,AbstractIdentifiable data){
		WebNavigationManager.getInstance().redirectTo(outcomeDynamicCrudMany,new Object[]{
				WebManager.getInstance().getRequestParameterClass(), UIManager.getInstance().keyFromClass(dataClass),
				WebManager.getInstance().getRequestParameterIdentifiable(), data==null?null:((AbstractIdentifiable)data).getIdentifier()
		});
	}
	
	public void redirectToUrl(String url){
		try {
			Faces.redirect(url);
		} catch (IOException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
}
