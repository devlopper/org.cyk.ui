package org.cyk.ui.web.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.Part;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.ui.api.SelectItemBuildAdapter;
import org.cyk.ui.api.SelectItemBuildListener;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.api.servlet.report.ReportBasedOnDynamicBuilderServletListener;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;
import org.omnifaces.util.Ajax;

@Singleton @Named @Getter @Deployment(initialisationType=InitialisationType.EAGER)
public class WebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private static WebManager INSTANCE;
	public static WebManager getInstance() {
		return INSTANCE;
	}
	
	private static final String COLON = ":";
	private final Collection<ReportBasedOnDynamicBuilderServletListener> reportBasedOnDynamicBuilderServletListeners = new ArrayList<>();
	
	@Inject private LanguageBusiness languageBusiness;
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		languageBusiness.registerResourceBundle("org.cyk.ui.web.api.resources.message", getClass().getClassLoader());
	}
	
	private final Map<Class<? extends AbstractWebPage<?, ?,?, ?>>,Collection<Field>> requestParameterFieldsMap = new HashMap<Class<? extends AbstractWebPage<?,?,?,?>>, Collection<Field>>();
	
	private final String clientValidationGroupClass = Client.class.getName();
	
	private final String formId = "form";
	private final String formContentId = "contentPanel";
	private final String formMenuId = "menuPanel";
	private final String formContentFullId = COLON+formId+COLON+formContentId;
	private final String formMenuFullId = COLON+formId+COLON+formMenuId;
	
	private final String blockUIDialogWidgetId = "blockUIDialogWidget";
	private final String messageDialogWidgetId = "messageDialogWidget";
	private final String connectionMessageDialogWidgetId = "connectionMessageDialogWidget";
	private final String reportDataTableServletUrl = "/private/__tools__/export/_cyk_report_/_dynamicbuilder_/_jasper_/";
	
	@Setter private String decoratedTemplateInclude;
	
	private final String requestParameterFormModel = UIManager.getInstance().getFormModelParameter();
	private final String requestParameterClass = UIManager.getInstance().getClassParameter();
	private final String requestParameterIdentifiable = UIManager.getInstance().getIdentifiableParameter();
	private final String requestParameterUserAccount = "useraccount";
	private final String requestParameterWindowMode = "windowmode";
	private final String requestParameterWindowModeDialog = "windowmodedialog";
	private final String requestParameterWindowModeNormal = "windowmodenormal";
	private final String requestParameterPreviousUrl = "previousurl";
	private final String requestParameterPrint = "print";
	/*
	private final String requestParameterFileExtension = "fileextension";
	private final String requestParameterPdf = "pdf";
	private final String requestParameterXls = "xls";
	*/
	private final String requestParameterUrl = "url";
	private final String requestParameterOutcome = "outcome";
	
	private final String sessionAttributeUserSession = "userSession";
	
	public String facesMessageSeverity(FacesMessage facesMessage){
		switch(facesMessage.getSeverity().getOrdinal()){
		case 0:return "info";
		case 1:return "warn";
		case 2:return "error";
		case 3:return "fatal";
		default: return "info";
		}
	}
	
	public String fileName(Part part) {
		if(part==null || part.getHeader("content-disposition")==null)
			return null;
		for (String content : part.getHeader("content-disposition").split(";"))
			if (content.trim().startsWith("filename"))
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
		return null;
	}
	
	public <TYPE> List<SelectItem> buildSelectItems(Class<TYPE> aClass,SelectItemBuildListener<TYPE> selectItemBuildListener){
		List<SelectItem> list = new ArrayList<>();
		if(Boolean.TRUE.equals(selectItemBuildListener.nullable()))
			list.add(new SelectItem(null, selectItemBuildListener.nullLabel()));
		for(TYPE type : selectItemBuildListener.collection(aClass))
			list.add(new SelectItem(type,selectItemBuildListener.label(type)));
		return list;
	}
	
	public <TYPE> List<SelectItem> buildSelectItems(Class<TYPE> aClass){
		return buildSelectItems(aClass, new SelectItemBuildAdapter<TYPE>());
	}
	
	public String libraryName(AbstractWebManager webManager){
		return webManager.getLibraryName();
	}
	
	/**/
	
	public String getClientId(String[] clientId,Boolean root){
		return (Boolean.TRUE.equals(root)?COLON:"")+StringUtils.join(clientId,COLON);
	}
	
	public String getClientId(String clientId,Boolean root){
		return getClientId(new String[]{clientId}, root);
	}
	
	/**/
	
	public void update(String[] clientId){
		//System.out.println("WebManager.update() : "+getClientId(clientId, Boolean.FALSE));
		Ajax.update(getClientId(clientId, Boolean.FALSE));
	}
	
	public void update(String clientId){
		update(new String[]{getClientId(clientId, Boolean.FALSE)});
	}
	
	public void updateInForm(String clientId){
		update(new String[]{formId,clientId});
	}
	
	public void updateInForm(String[] clientId){
		update(formId+COLON+StringUtils.join(clientId,COLON));
	}
	
	public void updateInFormContent(String clientId){
		updateInForm(new String[]{formId,formContentId,clientId});
	}
	
	public void updateInFormContent(String[] clientId){
		updateInForm(formId+COLON+formContentId+COLON+StringUtils.join(clientId,COLON));
	}
	
	/**/
	
	public void throwValidationException(String messageId,Object[] messageParams,String detailsId,Object[] detailsParams){
		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,UIManager.getInstance().getLanguageBusiness().findText(messageId,messageParams)
				,StringUtils.isBlank(detailsId)?null:UIManager.getInstance().getLanguageBusiness().findText(detailsId,detailsParams)));
	}
	public void throwValidationException(String messageId,Object[] messageParams){
		throwValidationException(messageId,messageParams, null,null);
	}
	public void throwValidationException(){
		throwValidationException("input.value.valid.no",null);
	}
	public void throwValidationExceptionUnknownValue(Object value){
		throwValidationException("exception.value.unknown",new Object[]{value});
	}
	
}
