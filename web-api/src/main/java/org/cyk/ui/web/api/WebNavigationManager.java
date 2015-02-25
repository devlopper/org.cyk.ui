package org.cyk.ui.web.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.web.api.security.RoleManager;
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
	
	@Getter private String dynamicDirectory = "__dynamic__";
	
	@Getter private String outcomePublicIndex = "publicindex";
	@Getter private String outcomePrivateIndex = "privateindex";
	
	@Getter private String outcomeApplicationSetup = "applicationSetup";
	
	@Getter private String outcomeDynamicCrudOne = "dynamicCrudOne";
	@Getter private String outcomeDynamicCrudMany = "dynamicCrudMany";
	@Getter private String outcomeLogout = "useraccountlogout";
	
	@Getter private String outcomeToolsCalendar = "toolscalendar";
	@Getter private String outcomeToolsExportDataTableToPdf = "toolsexportdatatabletopdf";
	@Getter private String outcomeToolsExportDataTableToXls = "toolsexportdatatabletoxls";
	@Getter private String outcomeToolsPrintDataTable = "toolsprintdatatable";
	
	@Getter private String outcomeLicense = "license";
	
	@Getter private String outcomeExportDataTable = "exportdatatableservlet";
	
	@Inject private NavigationHelper navigationHelper;
	@Inject private WebManager webManager;
	@Inject private UIManager uiManager;
	@Inject protected RoleManager roleManager;
	
	@Getter private Collection<WebNavigationManagerListener> webNavigationManagerListeners = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	
	public String url(String id,Object[] parameters,Boolean actionOutcome,Boolean partial,Boolean pretty){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		StringBuilder url = new StringBuilder();
	
		NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, id);
		//System.out.println(id+" / "+navigationCase);
		if(navigationCase==null){
			log.severe("No Navigation Case found for "+id);
			return url(OUTCOME_NOT_FOUND, new Object[]{"oc",id},Boolean.FALSE,Boolean.FALSE);
		}
		String s = navigationCase.getToViewId(facesContext);
		if(Boolean.TRUE.equals(actionOutcome))
			url.append(s);
		else
			url.append(StringUtils.replace(s, FILE_STATIC_EXTENSION, FILE_PROCESSING_EXTENSION));
		
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
	    	;
	    else{
	    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    	
			//FacesContext.getCurrentInstance().getExternalContext().encodeResourceURL() will trigger rewriting  
	    	
			//int countContextPath = StringUtils.countMatches(url, request.getContextPath());
	    	url = new StringBuilder(StringUtils.removeStartIgnoreCase(//TODO might not work always
	    			FacesContext.getCurrentInstance().getExternalContext().encodeResourceURL(url.toString()), request.getContextPath()));
	    	//if(StringUtils.countMatches(url, request.getContextPath())>countContextPath)
	    		
			url.insert(0,request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
	    }	    
		
	    return url.toString();
	}
	
	
	/*
	public String url(String id,Object[] parameters,Boolean actionOutcome,Boolean partial,Boolean pretty){
		StringBuilder url = new StringBuilder();
		UrlMapping urlMapping = PrettyContext.getCurrentInstance(FacesContext.getCurrentInstance()).getConfig().getMappingById(id);
		System.out.println("WebNavigationManager.url() id = "+id+" , mapping = "+urlMapping);
		Map<String,String[]> map = new LinkedHashMap<>();
		if(parameters!=null && parameters.length>0){
	    	for(int i=0;i<parameters.length-1;i=i+2)
	    		if(parameters[i+1]==null)
	    			;
	    		else{
	    			map.put((String) parameters[i], new String[]{parameters[i+1].toString()});
	    		}
	    }
		url.append(new PrettyURLBuilder().build(urlMapping, false, map));
		
	    return url.toString();
	}
	*/
	
	public String url(String id,Object[] parameters,Boolean actionOutcome,Boolean partial){
		return url(id, parameters, actionOutcome, partial, Boolean.TRUE);
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
		String url,path = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
		if(path==null){
			url = request.getRequestURL().toString();
			if(StringUtils.isNotEmpty(request.getQueryString()))
				url += "?"+request.getQueryString();
		}else{
			url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		}
		
		
		//if(StringUtils.isNotEmpty(PrettyContext.getCurrentInstance().getRequestQueryString().toQueryString()))
		//	url += NavigationHelper.QUERY_START+PrettyContext.getCurrentInstance().getRequestQueryString().toQueryString();
		return url;
	}
			
	public void redirectTo(String outcome,Object[] parameters){
		redirectToUrl(url(outcome,parameters,Boolean.FALSE,Boolean.FALSE));
	}
	public void redirectTo(String outcome){
		redirectTo(outcome, null);
	}
		
	public void redirectToUrl(String url){
		try {
			Faces.redirect(url);
			//Faces.responseComplete();
		} catch (IOException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	/* */
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudOne:businessEntityInfos.getUiEditViewId(), new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(businessEntityInfos)
				,uiManager.getCrudParameter(),uiManager.getCrudCreateParameter()
			});
	}
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos){
		return createOneUrl(businessEntityInfos, Boolean.TRUE);
	}
	
	public String createManyUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic,Boolean actionOutcome,Boolean partial){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudMany:businessEntityInfos.getUiListViewId(), new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(businessEntityInfos)
			},actionOutcome,partial);
	}
	
	public String createManyUrl(BusinessEntityInfos businessEntityInfos,Boolean actionOutcome,Boolean partial){
		return createManyUrl(businessEntityInfos, Boolean.TRUE,actionOutcome,partial);
	}
	
	public String formUrl(Long identifier,String crud){
		return url(outcomeDynamicCrudOne, new Object[]{
				webManager.getRequestParameterIdentifiable(),identifier
				,uiManager.getCrudParameter(),crud
			});
	}
	
	public String exportDataTableFileUrl(Class<?> aClass,String fileExtension,Boolean print){
		return url(outcomeExportDataTable, new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(aClass)
				,uiManager.getFileExtensionParameter(),fileExtension
				,webManager.getRequestParameterPrint(),Boolean.TRUE.equals(print)
			},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String homeUrl(UserSession userSession){
		String url = null;
		if(roleManager.isAdministrator(null))
			url = url("administratorindex",new Object[]{},Boolean.FALSE,Boolean.FALSE);
		else
			for(WebNavigationManagerListener listener : webNavigationManagerListeners){
				String v = listener.homeUrl(userSession);
				if(v!=null)
					url = v;
			}
		if(url==null)
			url = url(outcomePrivateIndex,new Object[]{},Boolean.FALSE,Boolean.FALSE);
			
		return url;
	}
	
	public String applicationSetupUrl(){
		return url(outcomeApplicationSetup, new Object[]{},Boolean.FALSE,Boolean.FALSE);
	}
	
	/**/
	
	public String editOneOutcome(Class<AbstractIdentifiable> aClass){
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		//IdentifiableConfiguration identifiableConfiguration = uiManager.findConfiguration(aClass);
		if(StringUtils.isEmpty(businessEntityInfos.getUiEditViewId()))
			//if(Boolean.TRUE.equals(identifiableConfiguration.getFileSupport()))
				//return outcomeDynamicCrudOneWithFileSupport;
			//else
				return outcomeDynamicCrudOne;
		else
			return businessEntityInfos.getUiEditViewId();
	}
	
	@SuppressWarnings("unchecked")
	public void redirectToDynamicCrudOne(AbstractIdentifiable data,Crud crud){
		redirectTo(editOneOutcome((Class<AbstractIdentifiable>) data.getClass()),new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(data.getClass()),
				webManager.getRequestParameterIdentifiable(), data.getIdentifier(),
				uiManager.getCrudParameter(), uiManager.getCrudParameterValue(crud)
		});
	}
	
	public void redirectToDynamicCrudOne(Class<AbstractIdentifiable> aClass){
		redirectTo(editOneOutcome(aClass),new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(aClass),
				uiManager.getCrudParameter(), uiManager.getCrudCreateParameter()
		});
	}
	
	public void redirectToDynamicCrudMany(Class<AbstractIdentifiable> dataClass,AbstractIdentifiable data){
		redirectTo(outcomeDynamicCrudMany,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass),
				webManager.getRequestParameterIdentifiable(), data==null?null:((AbstractIdentifiable)data).getIdentifier()
		});
	}
	
	public void redirectToExportDataTableToPdf(Class<AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToPdf,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass)
		});
	}
	
	public void redirectToExportDataTableToXls(Class<AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToXls,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass)
		});
	}
	
	public void redirectToPrintData(Class<AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsPrintDataTable,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass)
		});
	}
	
	/**/
	
	public Collection<Parameter> crudOneParameters(Class<? extends AbstractIdentifiable> aClass){
		Collection<Parameter> parameters = new ArrayList<UICommandable.Parameter>();
		parameters.add(new Parameter(webManager.getRequestParameterClass(), uiManager.keyFromClass(aClass)));
		parameters.add(new Parameter(uiManager.getCrudParameter(),uiManager.getCrudCreateParameter()));
		return parameters;
	}
	
	public Collection<Parameter> crudManyParameters(AbstractIdentifiable anIdentifiable){
		Collection<Parameter> parameters = new ArrayList<UICommandable.Parameter>();
		parameters.add(new Parameter(webManager.getRequestParameterClass(), uiManager.keyFromClass(anIdentifiable.getClass())));
		parameters.add(new Parameter(webManager.getRequestParameterIdentifiable(), anIdentifiable==null?null:anIdentifiable.getIdentifier()));
		return parameters;
	}
	
}
