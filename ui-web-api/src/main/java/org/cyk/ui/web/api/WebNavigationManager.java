package org.cyk.ui.web.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.CommonBusinessAction;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.file.FileBusiness;
import org.cyk.system.root.business.api.file.FileRepresentationTypeBusiness;
import org.cyk.system.root.business.api.file.report.ReportTemplateBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.persistence.api.file.FileIdentifiableGlobalIdentifierDao;
import org.cyk.system.root.persistence.api.file.FileRepresentationTypeDao;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.UICommandable.Parameter;
import org.cyk.ui.web.api.security.RoleManager;
import org.cyk.ui.web.api.servlet.FileServlet;
import org.cyk.ui.web.api.servlet.ImageServlet;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtension;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.builder.UrlStringBuilder;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.cdi.BeanAdapter;
import org.omnifaces.util.Faces;

@Singleton @Named @Log @Deployment(initialisationType=InitialisationType.EAGER)
public class WebNavigationManager extends AbstractBean implements Serializable {
	
	private static final long serialVersionUID = 4432678991321751425L;
	
	private static WebNavigationManager INSTANCE;
	public static WebNavigationManager getInstance() {
		return INSTANCE;
	}
	static{
		IdentifierProvider.COLLECTION.add(new IdentifierProvider.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getViewDynamic(CommonBusinessAction commonBusinessAction, Boolean one) {
				if(ArrayUtils.contains(new CommonBusinessAction[]{CommonBusinessAction.CREATE,CommonBusinessAction.READ
						,CommonBusinessAction.UPDATE,CommonBusinessAction.DELETE}, commonBusinessAction))
					if(Boolean.TRUE.equals(one))
						return INSTANCE.outcomeDynamicCrudOne;
					else
						return INSTANCE.outcomeDynamicCrudMany;
				else if(ArrayUtils.contains(new CommonBusinessAction[]{CommonBusinessAction.SELECT}, commonBusinessAction))
					if(Boolean.TRUE.equals(one))
						return INSTANCE.outcomeDynamicSelectOne;
					else
						return INSTANCE.outcomeDynamicSelectMany;
				return null;
			}
			
			@Override
			public String getViewDynamicReport() {
				return INSTANCE.outcomeToolsReport;
			}
			@Override
			public String getViewGlobalIdentifierEdit() {
				return INSTANCE.outcomeGlobalIdentifierEdit;
			}
		});
	
		UrlStringBuilder.PATH_NOT_FOUND_IDENTIFIER = "oc";
		UrlStringBuilder.SCHEME = "http";
		UrlStringBuilder.Listener.COLLECTION.add(new UrlStringBuilder.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getPathFromIdentifier(String identifier) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler())
						.getNavigationCase(facesContext, null, identifier);
			
				if(navigationCase==null){
					
				}else
					return navigationCase.getToViewId(facesContext);
				return super.getPathFromIdentifier(identifier);
			}
		});
	}
	
	public static final Map<String, String> MOBILE_VIEW_MAP = new HashMap<String, String>();
	public static final String MOBILE_AGENT_FOLDER = "/mobile";
	public static final String MOBILE_PAGE_OUTCOME_FORMAT = "pm:%s?transition=%s&reverse=%s";
	public static final String MOBILE_PAGE_TRANSITION="flip";
	public static final Boolean MOBILE_PAGE_REVERSE=Boolean.TRUE;
	
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
	@Getter private String outcomeAnyActorTypeList = "anyActorTypeListView";
	@Getter private String outcomeAnyActorTypeConsult = "anyActorTypeConsultView";
	@Getter private String outcomeAnyActorTypeEdit = "anyActorTypeEditView";
	@Getter private String outcomeEditActorRelationship = "editActorRelationship";
	@Getter private String outcomeEditActorMedicalInformations = "editMedicalInformations";
	@Getter private String outcomeEditFileContent = "fileEditContentView";
	
	@Getter private String outcomeToolsCalendar = "toolscalendar";
	@Getter private String outcomeToolsReport = "toolsreport";
	@Getter private String outcomeToolsExportDataTableToPdf = "toolsexportdatatabletopdf";
	@Getter private String outcomeToolsExportDataTableToXls = "toolsexportdatatabletoxls";
	@Getter private String outcomeToolsPrintDataTable = "toolsprintdatatable";
	
	@Getter private String outcomeReferenceEntity = "referenceentity";
	@Getter private String outcomeSecurity = "security";
	@Getter private String outcomeUserAccountChangePassword = "useraccountchangepassword";
	@Getter private String outcomeEventCrudOne = "eventCrudOne";
	@Getter private String outcomeEventList = "eventlist";
	@Getter private String outcomeNotifications = "notifications";
	
	@Getter private String outcomeLicenseRead = "licenseRead";
	@Getter private String outcomeGlobalIdentifierEdit = "globalIdentifierEditView";
	
	@Getter private String outcomeExportDataTable = "exportdatatableservlet";
	@Getter private String outcomeReportTable = "exportdatatableservlet";
	
	@Getter private String outcomeFileConsultMany = "fileConsultManyView";
	@Getter private String outcomeFileComputeContent = "fileComputeContentView";
	@Getter private String outcomeProcessMany = "dynamicProcessMany";
	
	@Getter private String outcomeReportFileGenerate = "reportFileGenerateView";
	
	@Getter private String outcomeFileServlet = "fileservlet";
	@Getter private String pathFileServlet = FileServlet.PATH;
	@Getter private String pathImageServlet = ImageServlet.PATH;
	
	@Inject private NavigationHelper navigationHelper;
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
	
	public String getPath(String outcome,Boolean actionOutcome,Boolean partial){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		StringBuilder path = new StringBuilder();
		NavigationCase navigationCase = ((ConfigurableNavigationHandler)facesContext.getApplication().getNavigationHandler()).getNavigationCase(facesContext, null, outcome);
		if(navigationCase==null){
			log.severe("No Navigation Case found for "+outcome);
			return url(OUTCOME_NOT_FOUND, new Object[]{"oc",outcome},Boolean.FALSE,Boolean.FALSE);
		}
		String s = navigationCase.getToViewId(facesContext);
		if(Boolean.TRUE.equals(actionOutcome))
			path.append(s);
		else
			path.append(StringUtils.replace(s, FILE_STATIC_EXTENSION, FILE_PROCESSING_EXTENSION));
		
		
	    if(Boolean.TRUE.equals(partial))
	    	;
	    else{
	    	HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			path = new StringBuilder(StringUtils.removeStartIgnoreCase(//TODO might not work always
	    			FacesContext.getCurrentInstance().getExternalContext().encodeResourceURL(path.toString()), request.getContextPath()));	    		
			path.insert(0,request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
	    }	    
		
	    return path.toString();
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
		redirectTo((String) commandable.getViewId(),parametersToArray(commandable.getParameters()));
	}
		
	public void redirectToUrl(String url){
		logTrace("Redirect to {} , Committed = {}", url,Faces.isResponseCommitted());
		try {
			//throw new RuntimeException();
			Faces.redirect(url);
			//Faces.responseComplete();
		} catch (Exception e) {
			log.log(Level.SEVERE,e.toString(),e);
		}
	}
	
	/* */
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudOne:businessEntityInfos.getUserInterface().getEditViewId(), new Object[]{
			 UniformResourceLocatorParameter.CLASS,uiManager.keyFromClass(businessEntityInfos)
				,UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameter.CRUD_CREATE
			});
	}
	
	public String createOneUrl(BusinessEntityInfos businessEntityInfos){
		return createOneUrl(businessEntityInfos, Boolean.TRUE);
	}
	
	public String createManyUrl(BusinessEntityInfos businessEntityInfos,Boolean dynamic,Boolean actionOutcome,Boolean partial){
		return url(Boolean.TRUE.equals(dynamic)?outcomeDynamicCrudMany:businessEntityInfos.getUserInterface().getListViewId(), new Object[]{
				UniformResourceLocatorParameter.CLASS,uiManager.keyFromClass(businessEntityInfos)
			},actionOutcome,partial);
	}
	
	public String createManyUrl(BusinessEntityInfos businessEntityInfos,Boolean actionOutcome,Boolean partial){
		return createManyUrl(businessEntityInfos, Boolean.TRUE,actionOutcome,partial);
	}
	
	public String formUrl(Long identifier,String crud){
		return url(outcomeDynamicCrudOne, new Object[]{
				UniformResourceLocatorParameter.IDENTIFIABLE,identifier
				,UniformResourceLocatorParameter.CRUD,crud
			});
	}
	
	public String getConsultUrl(AbstractIdentifiable data){
		return url(consultOneOutcome(data.getClass()),new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(data.getClass()),
				UniformResourceLocatorParameter.IDENTIFIABLE, data.getIdentifier(),
				UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameterBusinessImpl.getCrudAsString(Crud.READ)
				//,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()//TODO must be parameterized
		},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String exportDataTableFileUrl(Class<?> aClass,String fileExtension,Boolean print){
		return url(outcomeExportDataTable, new Object[]{
				UniformResourceLocatorParameter.CLASS,uiManager.keyFromClass(aClass)
				,UniformResourceLocatorParameter.FILE_EXTENSION,fileExtension
				,UniformResourceLocatorParameter.PRINT,Boolean.TRUE.equals(print),
				UniformResourceLocatorParameter.URL, UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER
			},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String reportFileUrl(Class<?> aClass,String servletUrl,String fileExtension,Boolean print){
		return url(UniformResourceLocatorParameter.VIEW_IDENTIFIER_REPORT, new Object[]{
				UniformResourceLocatorParameter.CLASS,uiManager.keyFromClass(aClass)
				,UniformResourceLocatorParameter.FILE_EXTENSION,fileExtension
				,UniformResourceLocatorParameter.PRINT,Boolean.TRUE.equals(print),
				UniformResourceLocatorParameter.URL, UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER
			},Boolean.FALSE,Boolean.FALSE);
	}
	
	public String homeUrl(AbstractWebUserSession<?,?> userSession){
		String url = null;
		if(roleManager.isAdministrator(null))
			url = url("administratorindex",new Object[]{},Boolean.FALSE,Boolean.FALSE);
		else
			for(Listener<AbstractWebUserSession<?,?>> listener : Listener.COLLECTION){
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
		Collection<Parameter> parameters = new ArrayList<>();
		Parameter.addReport(parameters, identifiable, reportIdentifier, fileExtension, print, null);
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
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(data.getClass()),
				UniformResourceLocatorParameter.IDENTIFIABLE, data.getIdentifier(),
				UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameterBusinessImpl.getCrudAsString(Crud.READ)
				,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()//TODO must be parameterized
		};
		redirectTo(consultOneOutcome(data.getClass()),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	public void redirectToDynamicConsultOne(AbstractIdentifiable data){
		redirectToDynamicConsultOne(data,null);
	}
	
	public void redirectToDynamicCrudOne(AbstractIdentifiable data,Crud crud,Collection<Parameter> parameters){
		redirectTo(editOneOutcome(data.getClass()),ArrayUtils.addAll(new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(data.getClass()),
				UniformResourceLocatorParameter.IDENTIFIABLE, data.getIdentifier(),
				UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameterBusinessImpl.getCrudAsString(crud)
				,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()//TODO must be parameterized
		},parametersToArray(parameters)));
	}
	public void redirectToDynamicCrudOne(AbstractIdentifiable data,Crud crud){
		redirectToDynamicCrudOne(data,crud,null);
	}
	
	public void redirectToDynamicCreate(AbstractIdentifiable parent,Class<? extends AbstractIdentifiable> childClass,Collection<Parameter> parameters){
		Object[] parametersArray = new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(childClass),
				uiManager.businessEntityInfos(parent.getClass()).getIdentifier(), parent.getIdentifier(),
				UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameter.CRUD_CREATE
				,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()//TODO must be parameterized
		};
		redirectTo(editOneOutcome(childClass),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	
	public void redirectToDynamicCrudOne(Class<? extends AbstractIdentifiable> aClass,Collection<Parameter> parameters){
		Object[] parametersArray = new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(aClass),
				UniformResourceLocatorParameter.CRUD, UniformResourceLocatorParameter.CRUD_CREATE
				,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()}; //TODO must be parameterized
		//ArrayUtils.addAll(parametersArray, parametersToArray(parameters));
		redirectTo(editOneOutcome(aClass),ArrayUtils.addAll(parametersArray, parametersToArray(parameters)));
	}
	public void redirectToDynamicCrudOne(Class<? extends AbstractIdentifiable> aClass){
		redirectToDynamicCrudOne(aClass,null);
	}
	
	public void redirectToCrudMany(Class<? extends AbstractIdentifiable> dataClass,AbstractIdentifiable data,Object[] parameters){
		redirectTo(IdentifierProvider.Adapter.getViewOf(dataClass, CommonBusinessAction.LIST, Boolean.FALSE),ArrayUtils.addAll(new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass),
				UniformResourceLocatorParameter.IDENTIFIABLE, data==null?null:((AbstractIdentifiable)data).getIdentifier()
		}, parameters));
	}
	
	public void redirectToCrudMany(Class<? extends AbstractIdentifiable> dataClass,AbstractIdentifiable data){
		redirectToCrudMany(dataClass, data, null);
	}
	
	public void redirectToCrudOne(AbstractIdentifiable data,Crud crud,CommonBusinessAction commonBusinessAction){
		redirectTo(IdentifierProvider.Adapter.getViewOf(data.getClass(), commonBusinessAction, Boolean.TRUE),new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(data.getClass())
				,UniformResourceLocatorParameter.CRUD, crud
				,UniformResourceLocatorParameter.PREVIOUS_URL, getRequestUrl()
				,UniformResourceLocatorParameter.IDENTIFIABLE, data
		});
	}
	
	public void redirectToDynamicCrudMany(Class<? extends AbstractIdentifiable> dataClass,AbstractIdentifiable data){
		redirectTo(outcomeDynamicCrudMany,new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass),
				UniformResourceLocatorParameter.IDENTIFIABLE, data==null?null:((AbstractIdentifiable)data).getIdentifier()
		});
	}
	
	public void redirectToExportDataTableToPdf(Class<? extends AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToPdf,new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass),
				UniformResourceLocatorParameter.URL, UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER
		});
	}
	
	public void redirectToExportDataTableToXls(Class<? extends AbstractIdentifiable> dataClass){
		redirectTo(outcomeToolsExportDataTableToXls,new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass),
				UniformResourceLocatorParameter.URL, UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER
		});
	}
	
	public Object[] getFileConsultManyPageObjects(Collection<? extends AbstractIdentifiable> identifiables,FileExtension fileExtension){
		return new Object[]{
				UniformResourceLocatorParameter.IDENTIFIABLE, identifiables==null || identifiables.isEmpty() ? null 
						: WebManager.getInstance().encodeIdentifiablesAsRequestParameterValue(identifiables)
				,UniformResourceLocatorParameter.FILE_EXTENSION, fileExtension.getValue()
				,UniformResourceLocatorParameter.ENCODED, UniformResourceLocatorParameter.IDENTIFIABLE
		};
	}
	
	public void redirectToFileConsultManyPage(Collection<? extends AbstractIdentifiable> identifiables,FileExtension fileExtension){
		redirectTo(outcomeFileConsultMany,getFileConsultManyPageObjects(identifiables, fileExtension));
	}
	
	public String getUrlToFileConsultManyPage(String fileRepresentationTypeCode,AbstractIdentifiable identifiable,FileExtension fileExtension){
		Collection<File> files = inject(FileBusiness.class).findByRepresentationTypesByIdentifiables(Arrays.asList(inject(FileRepresentationTypeBusiness.class)
				.find(fileRepresentationTypeCode)),Arrays.asList(identifiable));
		return url(outcomeFileConsultMany,getFileConsultManyPageObjects(files, fileExtension));
	}
	
	public void redirectToFileConsultManyPage(Collection<FileRepresentationType> fileRepresentationTypes,Collection<? extends AbstractIdentifiable> identifiables,FileExtension fileExtension){
		redirectToFileConsultManyPage(inject(FileBusiness.class).findByRepresentationTypesByIdentifiables(fileRepresentationTypes,identifiables), fileExtension);
	}
	
	public void redirectToFileConsultManyPage(FileRepresentationType fileRepresentationType,Collection<? extends AbstractIdentifiable> identifiables,FileExtension fileExtension){
		redirectToFileConsultManyPage(Arrays.asList(fileRepresentationType), identifiables, fileExtension);
	}
	
	public void redirectToReportFileGeneratePage(AbstractIdentifiable identifiable,String reportTemplateCode,FileExtension fileExtension){
		redirectTo(outcomeReportFileGenerate,new Object[]{
				UniformResourceLocatorParameter.IDENTIFIABLE, identifiable
				,UniformResourceLocatorParameter.GLOBAL_IDENTIFIER, identifiable.getGlobalIdentifier().getIdentifier()
				,UniformResourceLocatorParameter.GLOBAL_IDENTIFIER_OWNER_CLASS, inject(ApplicationBusiness.class).findBusinessEntityInfos(identifiable.getClass()).getIdentifier()
				,UniformResourceLocatorParameter.REPORT_IDENTIFIER, inject(ReportTemplateBusiness.class).find(reportTemplateCode).getIdentifier()
				,UniformResourceLocatorParameter.FILE_EXTENSION, fileExtension.getValue()
		});
	}
	
	public void redirectToReportFileConsultPageOrReportFileGeneratePageIfNotExist(AbstractIdentifiable identifiable,String reportTemplateCode){
		FileIdentifiableGlobalIdentifier.SearchCriteria searchCriteria = new FileIdentifiableGlobalIdentifier.SearchCriteria();
    	searchCriteria.addIdentifiableGlobalIdentifier(identifiable);
    	searchCriteria.addRepresentationType(inject(FileRepresentationTypeDao.class).read(reportTemplateCode));
    	Collection<FileIdentifiableGlobalIdentifier> fileIdentifiableGlobalIdentifiers = inject(FileIdentifiableGlobalIdentifierDao.class).readByCriteria(searchCriteria);
		if(fileIdentifiableGlobalIdentifiers.isEmpty())
			WebNavigationManager.getInstance().redirectToReportFileGeneratePage(identifiable,reportTemplateCode, FileExtension.PDF);
		else
			WebNavigationManager.getInstance().redirectToFileConsultManyPage(Utils.getFiles(fileIdentifiableGlobalIdentifiers), FileExtension.PDF);
	}
	
	/*public <IDENTIFIABLE extends AbstractIdentifiable> UrlStringBuilder getRedirectToDynamicProcessManyPage(String outcome,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables
			,String actionIdentifier,Object[] parameters){
		
	}*/
	
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToDynamicProcessManyPage(String outcome,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables
			,String actionIdentifier,Object[] parameters){
		Object[] __parameters__ = ArrayUtils.addAll(new Object[]{
				UniformResourceLocatorParameter.IDENTIFIABLE, identifiables==null || identifiables.isEmpty() ? null 
						: WebManager.getInstance().encodeIdentifiablesAsRequestParameterValue(identifiables)
				,UniformResourceLocatorParameter.CLASS,uiManager.businessEntityInfos(identifiableClass).getIdentifier()
				,UniformResourceLocatorParameter.ACTION_IDENTIFIER, actionIdentifier
				,UniformResourceLocatorParameter.ENCODED, UniformResourceLocatorParameter.IDENTIFIABLE
		}, parameters);
		redirectTo(outcome,__parameters__);
	}
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToDynamicProcessManyPage(String outcome,Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables
			,String actionIdentifier){
		redirectToDynamicProcessManyPage(outcome, identifiableClass, identifiables, actionIdentifier, null);
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToDynamicProcessManyPage(Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables
			,String actionIdentifier,Object[] parameters){
		redirectToDynamicProcessManyPage(outcomeProcessMany, identifiableClass, identifiables, actionIdentifier,parameters);
	}
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToDynamicProcessManyPage(Class<IDENTIFIABLE> identifiableClass,Collection<IDENTIFIABLE> identifiables
			,String actionIdentifier){
		redirectToDynamicProcessManyPage(identifiableClass, identifiables, actionIdentifier, null);
	}
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToEditManyPage(String outcome,Class<IDENTIFIABLE> identifiableClass
			,Collection<IDENTIFIABLE> identifiables,Object[] parameters){
		redirectToDynamicProcessManyPage(outcome, identifiableClass, identifiables, null,parameters);
	}
	public <IDENTIFIABLE extends AbstractIdentifiable> void redirectToEditManyPage(String outcome,Class<IDENTIFIABLE> identifiableClass
			,Collection<IDENTIFIABLE> identifiables){
		redirectToEditManyPage(outcome, identifiableClass, identifiables, null);
	}
	
	public void redirectToPrintData(Collection<Parameter> parameters/*,String reportId*/){
		/*redirectTo(outcomeToolsPrintDataTable,new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass)
		});*/
		//System.out.println("WebNavigationManager.redirectToPrintData() : "+dataClass+" : "+uiManager.keyFromClass(dataClass));
		/*redirectTo(outcomeToolsReport,new Object[]{
				UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(dataClass),
				UIManager.getInstance().getFileExtensionParameter(),uiManager.getPdfParameter(),
				UIManager.getInstance().getReportIdentifierParameter(),reportId,
				UniformResourceLocatorParameter.PRINT,Boolean.TRUE,
				webManager.getRequestParameterOutcome(), outcomeReportTable
		});*/
		
		redirectTo(outcomeToolsReport,parametersToArray(parameters));
	}
	
	/**/
	
/*	public UICommandable createUpdateCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType,String viewId){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(StringUtils.isBlank(viewId)?editOneOutcome(identifiable.getClass()):viewId);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.addCrudParameters(UIManager.getInstance().getCrudUpdateParameter(), identifiable);
		return commandable;
	}
	public UICommandable createUpdateCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType){
		return createUpdateCommandable(identifiable, labelid, iconType,null);
	}
	
	public UICommandable createDeleteCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType,String viewId){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(StringUtils.isBlank(viewId)?editOneOutcome(identifiable.getClass()):viewId);
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.addCrudParameters(UIManager.getInstance().getCrudDeleteParameter(), identifiable);
		return commandable;
	}
	public UICommandable createDeleteCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType){
		return createDeleteCommandable(identifiable, labelid, iconType,null);
	}
	
	public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,String labelid,Icon iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(consultOneOutcome(identifiable.getClass()));
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.addCrudParameters(UIManager.getInstance().getCrudReadParameter(), identifiable);
		return commandable;
	}
	public UICommandable createConsultCommandable(AbstractIdentifiable identifiable,Icon iconType){
		UICommandable commandable = createConsultCommandable(identifiable, "button", iconType);
		commandable.setLabel(RootBusinessLayer.getInstance().getFormatterBusiness().format(identifiable));
		return commandable;
	}
	
	public UICommandable createCreateCommandable(AbstractIdentifiable master,Class<? extends AbstractIdentifiable> identifiableClass,String labelid,Icon iconType){
		UICommandable commandable = UIProvider.getInstance().createCommandable(labelid, iconType);
		commandable.setViewId(editOneOutcome(identifiableClass));
		commandable.setCommandRequestType(CommandRequestType.UI_VIEW);
		commandable.getParameters().addAll(crudOneParameters(identifiableClass));
		if(master!=null)
			commandable.getParameters().add(new Parameter(uiManager.businessEntityInfos(master.getClass()).getIdentifier(), master.getIdentifier()));	
		return commandable;
	}
	
	public UICommandable createReportCommandable(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,Icon iconType,Boolean popup){
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
	
	public UICommandable createReportCommandable(AbstractIdentifiable identifiable,String reportIdentifier,String labelid,Icon iconType){
		return createReportCommandable(identifiable, reportIdentifier, labelid, iconType, Boolean.TRUE);
	}
	
	
	
	public Collection<Parameter> crudOneParameters(Class<? extends AbstractIdentifiable> aClass){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(aClass)));
		parameters.add(new Parameter(UniformResourceLocatorParameter.CRUD,UniformResourceLocatorParameter.CRUD_CREATE));
		return parameters;
	}
	
	public Collection<Parameter> crudManyParameters(AbstractIdentifiable anIdentifiable){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(UniformResourceLocatorParameter.CLASS, uiManager.keyFromClass(anIdentifiable.getClass())));
		parameters.add(new Parameter(UniformResourceLocatorParameter.IDENTIFIABLE, anIdentifiable==null?null:anIdentifiable.getIdentifier()));
		return parameters;
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print,String windowMode){
		Collection<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter(UniformResourceLocatorParameter.CLASS,uiManager.keyFromClass(anIdentifiable.getClass())));
		parameters.add(new Parameter(UniformResourceLocatorParameter.IDENTIFIABLE,anIdentifiable.getIdentifier()));
		parameters.add(new Parameter(UniformResourceLocatorParameter.FILE_EXTENSION,fileExtension));
		if(StringUtils.isNotBlank(windowMode))
			parameters.add(new Parameter(webManager.getRequestParameterWindowMode(),windowMode));
		parameters.add(new Parameter(uiManager.getReportIdentifierParameter(),reportIdentifier));
		parameters.add(new Parameter(webManager.getRequestParameterOutcome(),outcomeReport));
		parameters.add(new Parameter(UniformResourceLocatorParameter.PRINT,Boolean.TRUE.equals(print)));
		return parameters;
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,String fileExtension,Boolean print){
		return reportParameters(anIdentifiable, reportIdentifier, fileExtension, print, webManager.getRequestParameterWindowModeDialog());
	}
	
	public Collection<Parameter> reportParameters(AbstractIdentifiable anIdentifiable,String reportIdentifier,Boolean print){
		return reportParameters(anIdentifiable, reportIdentifier, uiManager.getPdfParameter(),print);
	}*/

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
	
	public String getIdentifierParameterName(){
		return UniformResourceLocatorParameter.IDENTIFIABLE;
	}
	
	public String getDynamicExportFileJasper(){
		return UniformResourceLocator.DYNAMIC_EXPORT_FILE_JASPER;
	}
	public String getExportFileJasper(){
		return UniformResourceLocator.EXPORT_FILE_JASPER;
	}
	
	/**/
	
	public interface Listener<USER_SESSION extends AbstractWebUserSession<?,?>> {

		Collection<Listener<AbstractWebUserSession<?,?>>> COLLECTION = new ArrayList<>();
		
		String homeUrl(USER_SESSION userSession);
		
		/**/
		
		public static class Adapter<USER_SESSION extends AbstractWebUserSession<?,?>> extends BeanAdapter implements Listener<USER_SESSION>,Serializable{

			private static final long serialVersionUID = -6865620540167646004L;

			@Override
			public String homeUrl(USER_SESSION userSession) {
				return null;
			}
			
			/**/
			
			public static class Default<USER_SESSION extends AbstractWebUserSession<?,?>> extends Adapter<USER_SESSION> implements Serializable{

				private static final long serialVersionUID = 3989646511932404057L;
				
			}
			
		}
		
	}
	
}
