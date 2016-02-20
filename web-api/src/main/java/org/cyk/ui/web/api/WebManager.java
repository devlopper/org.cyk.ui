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

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.language.LanguageEntry;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.api.servlet.report.ReportBasedOnDynamicBuilderServletListener;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;
import org.omnifaces.util.Ajax;

import lombok.Getter;
import lombok.Setter;

@Singleton @Named @Getter @Deployment(initialisationType=InitialisationType.EAGER)
public class WebManager extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1690449792881915040L;
	
	private static final String REQUEST_PARAMETER_TOKEN_SEPERATOR = Constant.CHARACTER_UNDESCORE.toString();
	
	private static WebManager INSTANCE;
	public static WebManager getInstance() {
		return INSTANCE;
	}
	
	private static final String ID_SEPARATOR = Constant.CHARACTER_COLON.toString();
	private final Collection<ReportBasedOnDynamicBuilderServletListener> reportBasedOnDynamicBuilderServletListeners = new ArrayList<>();
	
	@Inject private LanguageBusiness languageBusiness;
	
	private List<SelectItem> booleanSelectItems = new ArrayList<>();
	private List<SelectItem> booleanSelectItemsNoNull = new ArrayList<>();
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		languageBusiness.registerResourceBundle("org.cyk.ui.web.api.resources.message", getClass().getClassLoader());
		booleanSelectItems.add(new SelectItem(null, languageBusiness.findText(SelectItemBuilderListener.NULL_LABEL_ID)));
		booleanSelectItems.add(new SelectItem(Boolean.TRUE, languageBusiness.findText(LanguageEntry.YES)));
		booleanSelectItems.add(new SelectItem(Boolean.FALSE, languageBusiness.findText(LanguageEntry.NO)));
		
		booleanSelectItemsNoNull.add(new SelectItem(Boolean.TRUE, languageBusiness.findText(LanguageEntry.YES)));
		booleanSelectItemsNoNull.add(new SelectItem(Boolean.FALSE, languageBusiness.findText(LanguageEntry.NO)));
		
	}
	
	private final Map<Class<? extends AbstractWebPage<?, ?,?, ?>>,Collection<Field>> requestParameterFieldsMap = new HashMap<Class<? extends AbstractWebPage<?,?,?,?>>, Collection<Field>>();
	
	private final String clientValidationGroupClass = Client.class.getName();
	
	private final String formId = "form";
	private final String formContentId = "contentPanel";
	private final String formMenuId = "menuPanel";
	private final String formContentFullId = ID_SEPARATOR+formId+ID_SEPARATOR+formContentId;
	private final String formMenuFullId = ID_SEPARATOR+formId+ID_SEPARATOR+formMenuId;
	
	private final String blockUIDialogWidgetId = "blockUIDialogWidget";
	private final String messageDialogWidgetId = "messageDialogWidget";
	private final String progressBarWidgetId = "progressBarWidget";
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
	private final String requestParameterTabId = "tabid";
	private final String requestParameterTitle = "title";
	private final String requestParameterAttachment = "attachment";
	
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
		
	public SelectItem getSelectItem(Object object,SelectItemBuilderListener selectItemBuildListener) {
		SelectItem selectItem = new SelectItem(object,selectItemBuildListener.getLabel(object));
		return selectItem;
	}
	public SelectItem getNullSelectItem(Class<?> aClass,SelectItemBuilderListener selectItemBuildListener) {
		SelectItem selectItem = new SelectItem(null,selectItemBuildListener.getNullLabel());
		return selectItem;
	}
	
	public SelectItem getSelectItem(Object object) {
		return getSelectItem(object, UIManager.getInstance().findSelectItemBuildListener(object.getClass()));
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection,SelectItemBuilderListener selectItemBuildListener){
		List<SelectItem> list = new ArrayList<>();
		if(Boolean.TRUE.equals(selectItemBuildListener.getNullable()))
			list.add(getNullSelectItem(aClass, selectItemBuildListener));
		for(Object object : collection)
			list.add(getSelectItem(object, selectItemBuildListener));
		return list;
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection){
		return getSelectItems(aClass,collection, UIManager.getInstance().findSelectItemBuildListener(aClass));
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass,SelectItemBuilderListener selectItemBuildListener){
		return getSelectItems(aClass,selectItemBuildListener.getCollection(aClass), selectItemBuildListener);
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass){
		return getSelectItems(aClass, UIManager.getInstance().findSelectItemBuildListener(aClass));
	}
	
	@SuppressWarnings("unchecked")
	public void setChoices(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Collection<?> collection,Object selected){
		InputChoice<Object, ?, ?, ?, ?, ?> inputChoice = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class, fieldName);
		if(inputChoice==null)
			return;
		List<SelectItem> list = (List<SelectItem>) inputChoice.getList();
		list.clear();
		if(collection!=null)
			list.addAll(getSelectItems(inputChoice.getField().getType(), collection));
		
		inputChoice.setValue(selected);
	}
	public void setChoices(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Collection<?> collection){
		setChoices(form, fieldName, collection,null);
	}
	
	public Object getChoice(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Integer index){
		InputChoice<?, ?, ?, ?, ?, ?> inputChoice = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class, fieldName);
		@SuppressWarnings("unchecked")
		List<SelectItem> list = (List<SelectItem>) inputChoice.getList();
		return list.isEmpty() ? null : list.get(index.intValue());
	}
	
	public String libraryName(AbstractWebManager webManager){
		return webManager.getLibraryName();
	}
	
	/**/
	
	public String getClientId(String[] clientId,Boolean root){
		return (Boolean.TRUE.equals(root)?ID_SEPARATOR:"")+StringUtils.join(clientId,ID_SEPARATOR);
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
		update(formId+ID_SEPARATOR+StringUtils.join(clientId,ID_SEPARATOR));
	}
	
	public void updateInFormContent(String clientId){
		updateInForm(new String[]{formId,formContentId,clientId});
	}
	
	public void updateInFormContent(String[] clientId){
		updateInForm(formId+ID_SEPARATOR+formContentId+ID_SEPARATOR+StringUtils.join(clientId,ID_SEPARATOR));
	}
	
	//TODO should not be here
	public String getClassSelector(WebInput<?, ?, ?, ?> input){
		return "@(."+input.getUniqueCssClass()+")";
	}
	
	public String encodeIdentifiersAsRequestParameterValue(Collection<Long> identifiers){
		Long highest = RootBusinessLayer.getInstance().getNumberBusiness().findHighest(identifiers);
		String number = RootBusinessLayer.getInstance().getNumberBusiness().concatenate(identifiers, highest.toString().length());
		number = RootBusinessLayer.getInstance().getNumberBusiness().encodeToBase62(number);
		return highest+REQUEST_PARAMETER_TOKEN_SEPERATOR+number;
	}
	
	public Collection<Long> decodeIdentifiersRequestParameterValue(String value){
		String[] tokens = StringUtils.split(value, REQUEST_PARAMETER_TOKEN_SEPERATOR);
		String number = RootBusinessLayer.getInstance().getNumberBusiness().decodeBase62(tokens[1]);
		return RootBusinessLayer.getInstance().getNumberBusiness().deconcatenate(Long.class, number, Integer.valueOf(tokens[0]));
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
