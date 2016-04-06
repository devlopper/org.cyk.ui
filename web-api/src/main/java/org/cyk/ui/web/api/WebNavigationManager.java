package org.cyk.ui.web.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractUserSession;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.CommandRequestType;
import org.cyk.ui.api.command.UICommandable.IconType;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.api.command.UICommandable.ViewType;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.servlet.FileServlet;
import org.cyk.ui.web.api.servlet.ImageServlet;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Singleton @Named @Log @Deployment(initialisationType=InitialisationType.EAGER)
public class WebNavigationManager extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;
	
	private static WebNavigationManager INSTANCE;
	public static WebNavigationManager getInstance() {
		return INSTANCE;
	}
	
	public static final Map<String, String> MOBILE_VIEW_MAP = new HashMap<String, String>();
	public static final String MOBILE_AGENT_FOLDER = "/mobile";
	public static final String MOBILE_PAGE_OUTCOME_FORMAT = "pm:%s?transition=%s&reverse=%s";
	public static final String MOBILE_PAGE_TRANSITION="flip";
	public static final Boolean MOBILE_PAGE_REVERSE=Boolean.TRUE;
	public static final String PAGE_CRUD_MANY = "/private/__tools__/crud/crudmany.jsf";
	public static final String PAGE_CRUD_ONE = "/private/__tools__/crud/crudone.jsf";
	public static final String PAGE_SELECT = "/private/__tools__/select.jsf";
	
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
	@Getter @Setter private String contextPath;
	@Getter @Setter private String mobileContextPath;
	@Getter private String outcomePublicIndex = "publicindex";
	@Getter private String outcomePrivateIndex = "privateindex";
	
	@Getter private String outcomeApplicationSetup = "applicationSetup";
	
	@Getter private String outcomeDynamicCrudOne = "dynamicCrudOne";
	@Getter private String outcomeDynamicCrudMany = "dynamicCrudMany";
	@Getter private String outcomeDynamicSelectOne = "dynamicSelectOne";
	@Getter private String outcomeDynamicSelectMany = "dynamicSelectMany";
	@Getter private String outcomeLogout = "useraccountlogout";
	@Getter private String outcomeEditActorRelationship = "editActorRelationship";
	@Getter private String outcomeEditActorMedicalInformations = "editMedicalInformations";
	
	@Getter private String outcomeToolsCalendar = "toolscalendar";
	@Getter private String outcomeToolsReport = "toolsreport";
	@Getter private String outcomeToolsExportDataTableToPdf = "toolsexportdatatabletopdf";
	@Getter private String outcomeToolsExportDataTableToXls = "toolsexportdatatabletoxls";
	@Getter private String outcomeToolsPrintDataTable = "toolsprintdatatable";
	
	@Getter private String outcomeReferenceEntity = "referenceentity";
	@Getter private String outcomeSecurity = "security";
	@Getter private String outcomeUserAccounts = "useraccounts";
	@Getter private String outcomeUserAccountCrudOne = "useraccountcrudone";
	@Getter private String outcomeUserAccountConsult = "useraccountconsult";
	@Getter private String outcomeUserAccountChangePassword = "useraccountchangepassword";
	@Getter private String outcomeEventCrudOne = "eventCrudOne";
	@Getter private String outcomeEventList = "eventlist";
	@Getter private String outcomeNotifications = "notifications";
	
	@Getter private String outcomeLicenseRead = "licenseRead";
	
	@Getter private String outcomeExportDataTable = "exportdatatableservlet";
	@Getter private String outcomeReportTable = "exportdatatableservlet";
	@Getter private String outcomeReport = "reportservlet";
	@Getter private String outcomeFileConsultMany = "fileConsultManyView";
	@Getter private String outcomeProcessMany = "dynamicProcessMany";
	
	@Getter private String pathFileServlet = FileServlet.PATH;
	@Getter private String pathImageServlet = ImageServlet.PATH;
	
	@Inject private NavigationHelper navigationHelper;
	@Inject private WebManager webManager;
	@Inject private UIManager uiManager;
	@Inject private RoleManager roleManager;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void handleNavigation(String outcome) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
        NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
        navigationHandler.handleNavigation(facesContext, null, outcome);
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
	    			navigationHelper.addParameter(url,/*(String)*/ parameters[i], parameters[i+1]);
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
	
	public String mobilePageOutcome(String pageId,String transition,Boolean reverse){
		return String.format(MOBILE_PAGE_OUTCOME_FORMAT, pageId,transition,Boolean.TRUE.equals(reverse));
	}
	public String mobilePageOutcome(String pageId){
		return mobilePageOutcome(pageId,MOBILE_PAGE_TRANSITION,MOBILE_PAGE_REVERSE);
	}
		
	public String getRequestUrl(HttpServletRequest request){
		String url,path = (String) request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
		if(path==null){
			url = request.getRequestURL().toString();
			if(StringUtils.isNotEmpty(request.getQueryString()))
				url += Constant.CHARACTER_QUESTION_MARK+request.getQueryString();
		}else{
			url = request.getScheme()+Constant.CHARACTER_COLON+Constant.CHARACTER_SLASH+Constant.CHARACTER_SLASH+request.getServerName()+
					Constant.CHARACTER_COLON+request.getServerPort()+path;
		}
		
		
		//if(StringUtils.isNotEmpty(PrettyContext.getCurrentInstance().getRequestQueryString().toQueryString()))
		//	url += NavigationHelper.QUERY_START+PrettyContext.getCurrentInstance().getRequestQueryString().toQueryString();
		return url;
	}
	
	public String getRequestUrl(){
		return getRequestUrl((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
	}
			
	public void redirectTo(String outcome,Object[] parameters){
		redirectToUrl(url(outcome,parameters,Boolean.FALSE,Boolean.FALSE));
	}
	public void redirectTo(String outcome){
		redirectTo(outcome, null);
	}
	
	public void redirectTo(UICommandable commandable){
		redirectTo((String) commandable.getViewId());
	}
		
	public void redirectToUrl(String url){
		logTrace("Redirect to {} , Committed = {}", url,Faces.isResponseCommitted());
		try {
			Faces.redirect(url);
			//Faces.responseComplete();
		} catch (IOException e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	/* */
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudOne:businessEntityInfos.getUserInterface().getEditViewId(), new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(businessEntityInfos)
				,uiManager.getCrudParameter(),uiManager.getCrudCreateParameter()
			});
	}
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos){
		return createOneUrl(businessEntityInfos, Boolean.TRUE);
	}
	
	public String createManyUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic,Boolean actionOutcome,Boolean partial){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudMany:businessEntityInfos.getUserInterface().getListViewId(), new Object[]{
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
	
	public String getConsultUrl(AbstractIdentifiable data){
		return url(consultOneOutcome(data.getClass()),new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(data.getClass()),
				webManager.getRequestParameterIdentifiable(), data.getIdentifier(),
				uiManager.getCrudParameter(), uiManager.getCrudParameterValue(Crud.READ)
				//,webManager.getRequestParameterPreviousUrl(), getRequestUrl()//TODO must be parameterized
		},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String exportDataTableFileUrl(Class<?> aClass,String fileExtension,Boolean print){
		return url(outcomeExportDataTable, new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(aClass)
				,uiManager.getFileExtensionParameter(),fileExtension
				,webManager.getRequestParameterPrint(),Boolean.TRUE.equals(print),
				webManager.getRequestParameterUrl(), webManager.getReportDataTableServletUrl()
			},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String reportFileUrl(Class<?> aClass,String servletUrl,String fileExtension,Boolean print){
		return url(outcomeReport, new Object[]{
				webManager.getRequestParameterClass(),uiManager.keyFromClass(aClass)
				,uiManager.getFileExtensionParameter(),fileExtension
				,webManager.getRequestParameterPrint(),Boolean.TRUE.equals(print),
				webManager.getRequestParameterUrl(), webManager.getReportDataTableServletUrl()
			},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String homeUrl(AbstractUserSession userSession){
		String url = null;
		if(roleManager.isAdministrator(null))
			url = url("administratorindex",new Object[]{},Boolean.FALSE,Boolean.FALSE);
		else
			for(Listener listener : Listener.COLLECTION){
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
	
	public String editOneOutcome(Class<? extends AbstractIdentifiable> aClass){
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		//IdentifiableConfiguration identifiableConfiguration = uiManager.findConfiguration(aClass);
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getEditViewId()))
			//if(Boolean.TRUE.equals(identifiableConfiguration.getFileSupport()))
				//return outcomeDynamicCrudOneWithFileSupport;
			//else
				return outcomeDynamicCrudOne;
		else
			return businessEntityInfos.getUserInterface().getEditViewId();
	}
	
	public String consultOneOutcome(Class<? extends AbstractIdentifiable> aClass){
		BusinessEntityInfos businessEntityInfos = uiManager.businessEntityInfos(aClass);
		if(StringUtils.isEmpty(businessEntityInfos.getUserInterface().getConsultViewId()))
			return outcomeDynamicCrudOne;
		else
			return businessEntityInfos.getUserInterface().getConsultViewId();
	}
	
	public String reportUrl(AbstractIdentifiable identifiable,String reportIdentifier,String fileExtension,Boolean print){
		Collection<Parameter> parameters = reportParameters(identifiable, reportIdentifier, fileExtension,print);
		return url(outcomeToolsReport, parametersToArray(parameters), Boolean.FALSE, Boolean.FALSE);
	}
	
	public Object[] parametersToArray(Collection<Parameter> parameters){
		if(parameters==null || parameters.isEmpty())
			return new Object[]{};
		Object[] objects = new Object[parameters.size()*2];
		int i=0;
		for(Parameter parameter : parameters){
			objects[i] = parameter.getName();
			objects[i+1] = parameter.getValue();
			i += 2;
		}
		return objects;
	}
	
	public void redirectToDynamicConsultOne(AbstractIdentifiable data,Collection<Parameter> parameters){
		Object[] parametersArray = new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(data.getClass()),
				webManager.getRequestParameterIdentifiable(), data.getIdentifier(),
				uiManager.getCrudParameter(), uiManager.getCrudParameterValue(Crud.READ)
				,webManager.getRequestParameterPreviousUrl(), getRequestUrl()//TODO must be parameterized
		};
		redirectTo(consultOneOutcome(data.getClass()),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	public void redirectToDynamicConsultOne(AbstractIdentifiable data){
		redirectToDynamicConsultOne(data,null);
	}
	
	public void redirectToDynamicCrudOne(AbstractIdentifiable data,Crud crud){
		redirectTo(editOneOutcome(data.getClass()),new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(data.getClass()),
				webManager.getRequestParameterIdentifiable(), data.getIdentifier(),
				uiManager.getCrudParameter(), uiManager.getCrudParameterValue(crud)
				,webManager.getRequestParameterPreviousUrl(), getRequestUrl()//TODO must be parameterized
		});
	}
	
	public void redirectToDynamicCreate(AbstractIdentifiable parent,Class<? extends AbstractIdentifiable> childClass,Collection<Parameter> parameters){
		Object[] parametersArray = new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(childClass),
				uiManager.businessEntityInfos(parent.getClass()).getIdentifier(), parent.getIdentifier(),
				uiManager.getCrudParameter(), uiManager.getCrudCreateParameter()
				,webManager.getRequestParameterPreviousUrl(), getRequestUrl()//TODO must be parameterized
		};
		redirectTo(editOneOutcome(childClass),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	
	public void redirectToDynamicCrudOne(Class<? extends AbstractIdentifiable> aClass,Collection<Parameter> parameters){
		Object[] parametersArray = new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(aClass),
				uiManager.getCrudParameter(), uiManager.getCrudCreateParameter()
				,webManager.getRequestParameterPreviousUrl(), getRequestUrl()}; //TODO must be parameterized
		//ArrayUtils.addAll(parametersArray, parametersToArray(parameters));
		redirectTo(editOneOutcome(aClass),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	public void redirectToDynamicCrudOne(Class<? extends AbstractIdentifiable> aClass){
		redirectToDynamicCrudOne(aClass,null);
	}
	
	public void redirectToDynamicCrudMany(Class<? extends AbstractIdentifiable> dataClass,AbstractIdentifiable data){
		redirectTo(outcomeDynamicCrudMany,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass),
				webManager.getRequestParameterIdentifiable(), data==null?null:((AbstractIdentifiable)data).getIdentifier()
		});
	}
	
	public void redirectToExportDataTableToPdf(Class<? extends AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToPdf,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass),
				webManager.getRequestParameterUrl(), webManager.getReportDataTableServletUrl()
		});
	}
	
	public void redirectToExportDataTableToXls(Class<? extends AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToXls,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass),
				webManager.getRequestParameterUrl(), webManager.getReportDataTableServletUrl()
		});
	}
	
	public void redirectToFileConsultManyPage(Collection<? extends AbstractIdentifiable> identifiables,FileExtension fileExtension){
		redirectTo(outcomeFileConsultMany,new Object[]{
				uiManager.getIdentifiableParameter(), identifiables==null || identifiables.isEmpty() ? null 
						: WebManager.getInstance().encodeIdentifiablesAsRequestParameterValue(identifiables)
				,uiManager.getFileExtensionParameter(), fileExtension.getValue()
				,uiManager.getEncodedParameter(), uiManager.getIdentifiableParameter()
		});
	}
	
	public void redirectToDynamicProcessManyPage(Collection<? extends AbstractIdentifiable> identifiables,String actionIdentifier){
		redirectTo(outcomeProcessMany,new Object[]{
				uiManager.getIdentifiableParameter(), identifiables==null || identifiables.isEmpty() ? null 
						: WebManager.getInstance().encodeIdentifiablesAsRequestParameterValue(identifiables)
				,uiManager.getActionIdentifierParameter(), actionIdentifier
				,uiManager.getEncodedParameter(), uiManager.getIdentifiableParameter()
		});
	}
	
	public void redirectToPrintData(Collection<Parameter> parameters/*,String reportId*/){
		/*redirectTo(outcomeToolsPrintDataTable,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass)
		});*/
		//System.out.println("WebNavigationManager.redirectToPrintData() : "+dataClass+" : "+uiManager.keyFromClass(dataClass));
		/*redirectTo(outcomeToolsReport,new Object[]{
				webManager.getRequestParameterClass(), uiManager.keyFromClass(dataClass),
				UIManager.getInstance().getFileExtensionParameter(),uiManager.getPdfParameter(),
				UIManager.getInstance().getReportIdentifierParameter(),reportId,
				webManager.getRequestParameterPrint(),Boolean.TRUE,
				webManager.getRequestParameterOutcome(), outcomeReportTable
		});*/
		
		redirectTo(outcomeToolsReport,parametersToArray(parameters));
	}
	
	/**/
	
	public UICommandable createUpdateCommandable(AbstractIdentifiable identifiable,String labelid,IconType iconType,String viewId){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(StringUtils.isBlank(viewId)?editOneOutcome(identifiable.getClass()):viewId);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.addCrudParameters(UIManager.getInstance().getCrudUpdateParameter(), identifiable);
		return commandable;
	}
	public UICommandable createUpdateCommandable(AbstractIdentifiable identifiable,String labelid,IconType iconType){
		return createUpdateCommandable(identifiable, labelid, iconType,null);
	}
	
	public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,String labelid,IconType iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(consultOneOutcome(identifiable.getClass()));
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.addCrudParameters(UIManager.getInstance().getCrudReadParameter(), identifiable);
		return commandable;
	}
	public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,IconType iconType){
		UICommandable commandable = createConsultCommandable(identifiable, "button", iconType);
		commandable.setLabel(RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable));
		return commandable;
	}
	
	public UICommandable createCreateCommandable(AbstractIdentifiable master,Class<? extends AbstractIdentifiable> identifiableClass,String labelid,IconType iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(editOneOutcome(identifiableClass));
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.getParameters().addAll(crudOneParameters(identifiableClass));
		if(master!=null)
			commandable.getParameters().add(new Parameter(uiManager.businessEntityInfos(master.getClass()).getIdentifier(), master.getIdentifier()));	
		return commandable;
	}
	
	public UICommandable createReportCommandable(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,IconType iconType,Boolean popup){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		if(Boolean.TRUE.equals(popup)){
			commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
			commandable.setOnClick(JavaScriptHelper.getInstance().openWindow(identifiable.getIdentifier().toString(), 
				reportUrl(identifiable, reportIdentifier, "pdf", Boolean.FALSE), 300, 300));
		}else{
			commandable.setViewType(ViewType.TOOLS_REPORT);
			commandable.getParameters().addAll(reportParameters(identifiable, reportIdentifier,Boolean.FALSE));
		}
		return commandable;
	}
	
	public UICommandable createReportCommandable(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,IconType iconType){
		return createReportCommandable(identifiable, reportIdentifier, labelid, iconType, Boolean.TRUE);
	}
	
	/**/
	
	public Collection<Parameter> crudOneParameters(Class<? extends AbstractIdentifiable> aClass){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(webManager.getRequestParameterClass(), uiManager.keyFromClass(aClass)));
		parameters.add(new Parameter(uiManager.getCrudParameter(),uiManager.getCrudCreateParameter()));
		return parameters;
	}
	
	public Collection<Parameter> crudManyParameters(AbstractIdentifiable anIdentifiable){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(webManager.getRequestParameterClass(), uiManager.keyFromClass(anIdentifiable.getClass())));
		parameters.add(new Parameter(webManager.getRequestParameterIdentifiable(), anIdentifiable==null?null:anIdentifiable.getIdentifier()));
		return parameters;
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print,String windowMode){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(webManager.getRequestParameterClass(),uiManager.keyFromClass(anIdentifiable.getClass())));
		parameters.add(new Parameter(webManager.getRequestParameterIdentifiable(),anIdentifiable.getIdentifier()));
		parameters.add(new Parameter(uiManager.getFileExtensionParameter(),fileExtension));
		if(StringUtils.isNotBlank(windowMode))
			parameters.add(new Parameter(webManager.getRequestParameterWindowMode(),windowMode));
		parameters.add(new Parameter(uiManager.getReportIdentifierParameter(),reportIdentifier));
		parameters.add(new Parameter(webManager.getRequestParameterOutcome(),outcomeReport));
		parameters.add(new Parameter(webManager.getRequestParameterPrint(),Boolean.TRUE.equals(print)));
		return parameters;
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print){
		return reportParameters(anIdentifiable, reportIdentifier, fileExtension, print, webManager.getRequestParameterWindowModeDialog());
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,Boolean print){
		return reportParameters(anIdentifiable, reportIdentifier, uiManager.getPdfParameter(),print);
	}

	public Boolean isMobileView(HttpServletRequest request){
		return request.getRequestURI().startsWith(mobileContextPath);
	}
	
	public Boolean isMobileView(){
		return isMobileView((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
	}

	public static void init(String contextPath){
		getInstance().setMobileContextPath(contextPath+MOBILE_AGENT_FOLDER);
	}
	
	public void mapMobileView(String viewName,String mobileViewName){
		MOBILE_VIEW_MAP.put(contextPath+viewName+FILE_PROCESSING_EXTENSION, MOBILE_AGENT_FOLDER+mobileViewName+FILE_PROCESSING_EXTENSION);
	}

	public void appendQueries(StringBuilder stringBuilder,HttpServletRequest request){
		if(StringUtils.isNotBlank(request.getQueryString()))
			stringBuilder.append(NavigationHelper.QUERY_START).append(request.getQueryString());
		/*
		for(Entry<String, String[]> entry : request.getParameterMap().entrySet())
			navigationHelper.addParameter(stringBuilder, entry.getKey(), StringUtils.join(entry.getValue(),","));
		*/
	}
	
	public void useDynamicSelectView(Class<?> clazz){
		BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(clazz);
		businessEntityInfos.getUserInterface().setSelectOneViewId(outcomeDynamicSelectOne);
		businessEntityInfos.getUserInterface().setSelectManyViewId(outcomeDynamicSelectMany);
	}
	
	/**/
	
	public interface Listener {

		Collection<Listener> COLLECTION = new ArrayList<>();
		
		String homeUrl(AbstractUserSession userSession);
		
		/**/
		
		public static class Adapter extends BeanAdapter implements Listener,Serializable{

			private static final long serialVersionUID = -6865620540167646004L;

			@Override
			public String homeUrl(AbstractUserSession userSession) {
				return null;
			}
			
			/**/
			
			public static class Default extends Adapter implements Serializable{

				private static final long serialVersionUID = 3989646511932404057L;
				
			}
			
		}
		
	}
	
}
