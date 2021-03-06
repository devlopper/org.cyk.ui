package org.cyk.ui.web.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.TypedBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.mathematics.IntervalBusiness;
import org.cyk.system.root.business.api.mathematics.NumberBusiness;
import org.cyk.system.root.business.impl.BusinessInterfaceLocator;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.RootConstant;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.system.root.model.pattern.tree.AbstractDataTreeNode;
import org.cyk.system.root.persistence.impl.Utils;
import org.cyk.ui.api.SelectItemBuilderListener;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.control.InputOneChoice;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.api.servlet.report.ReportBasedOnDynamicBuilderServletListener;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.Deployment;
import org.cyk.utility.common.annotation.Deployment.InitialisationType;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.validation.Client;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;

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
	
	private List<SelectItem> booleanSelectItems;
	private List<SelectItem> booleanSelectItemsNoNull;
	
	private final Map<Class<? extends AbstractWebPage<?, ?,?, ?,?>>,Collection<Field>> requestParameterFieldsMap = new HashMap<Class<? extends AbstractWebPage<?,?,?,?,?>>, Collection<Field>>();
	
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
	
	@Setter private String decoratedTemplateInclude;
		
	private final String sessionAttributeUserSession = "userSession";
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
		languageBusiness.registerResourceBundle("org.cyk.ui.web.api.resources.message", getClass().getClassLoader());
		UIManager.Listener.COLLECTION.add(new UIManager.Listener.Adapter(){
			private static final long serialVersionUID = -3858521830715147966L;
			@Override
			public String getCurrentViewUrl() {
				return WebNavigationManager.getInstance().getRequestUrl();
			}
			@Override
			public String getViewPath(String identifier) {
				return inject(WebNavigationManager.class).getPath(identifier,Boolean.FALSE,Boolean.FALSE);
			}
		});
	}
	
	public List<SelectItem> getBooleanSelectItems(){
		if(booleanSelectItems==null){
			booleanSelectItems = new ArrayList<>();
			booleanSelectItems.add(new SelectItem(null, languageBusiness.findText(SelectItemBuilderListener.NULL_LABEL_ID)));
			addBooleanSelectItems(booleanSelectItems);
		}
		return booleanSelectItems;
	}
	
	public List<SelectItem> getBooleanSelectItemsNoNull(){
		if(booleanSelectItemsNoNull==null){
			booleanSelectItemsNoNull = new ArrayList<>();
			addBooleanSelectItems(booleanSelectItemsNoNull);
		}
		return booleanSelectItemsNoNull;
	}
	
	private void addBooleanSelectItems(List<SelectItem> list){
		list.add(new SelectItem(Boolean.TRUE, languageBusiness.findText(RootConstant.Code.LanguageEntry.YES)));
		list.add(new SelectItem(Boolean.FALSE, languageBusiness.findText(RootConstant.Code.LanguageEntry.NO)));
	}
	
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
	
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection,Boolean addNullIf,SelectItemBuilderListener selectItemBuildListener){
		List<SelectItem> list = new ArrayList<>();
		if(Boolean.TRUE.equals(addNullIf) && Boolean.TRUE.equals(selectItemBuildListener.getNullable()))
			list.add(getNullSelectItem(aClass, selectItemBuildListener));
		for(Object object : collection)
			list.add(getSelectItem(object, selectItemBuildListener));
		return list;
	}
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection,SelectItemBuilderListener selectItemBuildListener){
		return getSelectItems(aClass, collection, Boolean.TRUE, selectItemBuildListener);
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection,Boolean addNullIf){
		return getSelectItems(aClass,collection,addNullIf, UIManager.getInstance().findSelectItemBuildListener(aClass));
	}
	public List<SelectItem> getSelectItems(Class<?> aClass,Collection<?> collection){
		return getSelectItems(aClass, collection, Boolean.TRUE);
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass,SelectItemBuilderListener selectItemBuildListener){
		return getSelectItems(aClass,selectItemBuildListener.getCollection(aClass),Boolean.TRUE, selectItemBuildListener);
	}
	
	public List<SelectItem> getSelectItems(Class<?> aClass){
		return getSelectItems(aClass, UIManager.getInstance().findSelectItemBuildListener(aClass));
	}
	
	public List<SelectItem> getSelectItems(Interval interval){
		List<SelectItem> selectItems = new ArrayList<>();
		for(Long value : inject(IntervalBusiness.class).findIntegers(interval))
			selectItems.add(new SelectItem(value, String.valueOf(value)));
		return selectItems;
	}
	
	public List<SelectItem> getSelectItemsFromNodes(List<? extends AbstractDataTreeNode> nodes){
		List<SelectItem> selectItems = new ArrayList<>();
		for(AbstractDataTreeNode node : nodes){
			selectItems.add(getSelectItemFromNode(node));
		}
		return selectItems;
	}
	
	public SelectItem getSelectItemFromNode(AbstractDataTreeNode node){
		SelectItem item;
		if(node.getChildren()==null || node.getChildren().isEmpty())
			item = new SelectItem(node, node.getName());
		else{
			item = new SelectItemGroup(node.getName());
			List<SelectItem> nodeItems = new ArrayList<>();
			for(AbstractIdentifiable child : node.getChildren()){
				nodeItems.add(getSelectItemFromNode((AbstractDataTreeNode) child));
			}
			((SelectItemGroup)item).setSelectItems(nodeItems.toArray(new SelectItem[]{}));
		}
		return item;
	}
	
	@SuppressWarnings("unchecked")
	public void setChoices(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Collection<?> collection,Object selected){
		InputChoice<Object, ?, ?, ?, ?, ?> inputChoice = form.findInputByClassByFieldName(org.cyk.ui.api.data.collector.control.InputChoice.class, fieldName);
		if(inputChoice==null)
			return;
		List<SelectItem> list = (List<SelectItem>) inputChoice.getList();
		list.clear();
		if(collection!=null)
			list.addAll(getSelectItems(inputChoice.getField().getType(), collection,InputOneChoice.class.isAssignableFrom(inputChoice.getClass())));
		
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
	
	@SuppressWarnings("rawtypes")
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
		return "@(."+input.getCss().getUniqueClass()+")";
	}
	
	public String encodeIdentifiersAsRequestParameterValue(Collection<Long> identifiers){
		Long highest = inject(NumberBusiness.class).findHighest(identifiers);
		String number = inject(NumberBusiness.class).concatenate(identifiers, highest.toString().length());
		Integer numberOfZero = 0;
		for(int i = 0;i<number.length();i++)
			if( number.charAt(i) == Constant.CHARACTER_ZERO )
				numberOfZero++;
			else
				break;
		number = inject(NumberBusiness.class).encodeToBase62(number);
		
		String value = highest.toString().length()+REQUEST_PARAMETER_TOKEN_SEPERATOR+number+REQUEST_PARAMETER_TOKEN_SEPERATOR+numberOfZero;
		logTrace("identifiers {} converted to request parameter value is {}",identifiers, value);
		return value;
	}
	public String encodeIdentifiablesAsRequestParameterValue(Collection<? extends AbstractIdentifiable> identifiables){
		return encodeIdentifiersAsRequestParameterValue(Utils.ids(identifiables));
	}
	
	public Collection<Long> decodeIdentifiersRequestParameterValue(String value){
		Collection<Long> identifiers;
		if(StringUtils.isBlank(value))
			identifiers = null;
		else{
			String[] tokens = StringUtils.split(value, REQUEST_PARAMETER_TOKEN_SEPERATOR);
			String number = StringUtils.repeat(Constant.CHARACTER_ZERO,Integer.valueOf(tokens[2]))
					+inject(NumberBusiness.class).decodeBase62(tokens[1]);
			identifiers = inject(NumberBusiness.class).deconcatenate(Long.class, number, Integer.valueOf(tokens[0]));
		}
		logTrace("request parameter value {} converted to identifiers is {}", value,identifiers);
		return identifiers;
	}
	
	public Collection<Long> decodeIdentifiersRequestParameter(String name,HttpServletRequest request){
		Collection<Long> identifiers = new ArrayList<>();
		String identifiable = getRequestParameter(request, name);
		if(StringUtils.isBlank(identifiable))
			return null;
		//TODO many parameters can be encoded
		String[] encodedParameterNames = request.getParameterValues(RootConstant.Code.UniformResourceLocatorParameter.ENCODED);
		if(ArrayUtils.contains(encodedParameterNames, name)){
			Collection<Long> r = decodeIdentifiersRequestParameterValue(identifiable);
			if(r!=null)
				identifiers.addAll(r);
		}else{
			String[] identifiersAsString = StringUtils.split(identifiable,Constant.CHARACTER_COMA.charValue());
			for(String identifier : identifiersAsString)
				try {
					identifiers.add(Long.parseLong(identifier));
				} catch (NumberFormatException e) {
					return null;
				}
		}
		return identifiers;
	}
	public Collection<Long> decodeIdentifiersRequestParameter(String name){
		return decodeIdentifiersRequestParameter(name, Faces.getRequest());
	}
	public Collection<Long> decodeIdentifiersRequestParameter(HttpServletRequest request){
		return decodeIdentifiersRequestParameter(UniformResourceLocatorParameter.IDENTIFIABLE, request);
	}
	public Collection<Long> decodeIdentifiersRequestParameter(){
		return decodeIdentifiersRequestParameter(UniformResourceLocatorParameter.IDENTIFIABLE, Faces.getRequest());
	}
	
	/**/
	
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> decodeIdentifiablesRequestParameter(Class<IDENTIFIABLE> identifiableClass,String name,HttpServletRequest request){
		Collection<Long> identifiers = decodeIdentifiersRequestParameter(name, request);
		@SuppressWarnings("unchecked")
		TypedBusiness<IDENTIFIABLE> business = (TypedBusiness<IDENTIFIABLE>) BusinessInterfaceLocator.getInstance().injectLocated(identifiableClass);
		Collection<IDENTIFIABLE> collection = business.findByIdentifiers(identifiers);
		logTrace("Decode identifiables from request parameters. class={} , name={} , count={} , collection={}",identifiableClass,name,collection.size(), collection);
		return collection;
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> decodeIdentifiablesRequestParameter(Class<IDENTIFIABLE> identifiableClass,HttpServletRequest request){
		return decodeIdentifiablesRequestParameter(identifiableClass,getEncodedParameterName(identifiableClass, request),request);
		/*
		String encodedParameter = getRequestParameter(request, UniformResourceLocatorParameter.ENCODED);
		return decodeIdentifiablesRequestParameter(identifiableClass,UniformResourceLocatorParameter.IDENTIFIABLE.equals(encodedParameter) 
				? UniformResourceLocatorParameter.IDENTIFIABLE : UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier(),request);
				*/
	}
	
	private <IDENTIFIABLE extends AbstractIdentifiable> String getEncodedParameterName(Class<IDENTIFIABLE> identifiableClass,HttpServletRequest request){
		String[] encodedParameterNames = request.getParameterValues(RootConstant.Code.UniformResourceLocatorParameter.ENCODED);
		return ArrayUtils.contains(encodedParameterNames, UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier())
				? UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier() : UniformResourceLocatorParameter.IDENTIFIABLE;			
	}
	
	public <IDENTIFIABLE extends AbstractIdentifiable> Collection<IDENTIFIABLE> decodeIdentifiablesRequestParameter(Class<IDENTIFIABLE> identifiableClass){
		return decodeIdentifiablesRequestParameter(identifiableClass, Faces.getRequest());
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
	
	/* Request parameter handling */
	
	public String getRequestParameter(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
	public String getRequestParameter(String name){
		return getRequestParameter(Faces.getRequest(),name);
	}
	
	public Long getRequestParameterLong(HttpServletRequest request,Class<? extends AbstractIdentifiable> identifiableClass){
		return getRequestParameterAsLong(request,UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
	}
	public Long getRequestParameterLong(Class<? extends AbstractIdentifiable> identifiableClass){
		return getRequestParameterLong(Faces.getRequest(), identifiableClass);
	}
	
	public Long getRequestParameterAsLong(HttpServletRequest request,String name){
		try {
			return Long.parseLong(getRequestParameter(request,name));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	public Long getRequestParameterAsLong(String name){
		return getRequestParameterAsLong(Faces.getRequest(), name);
	}
	
	public Boolean hasRequestParameter(HttpServletRequest request,String name){
		return StringUtils.isNotEmpty(getRequestParameter(request,name));
	}
	public Boolean hasRequestParameter(String name){
		return hasRequestParameter(Faces.getRequest(),name);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(HttpServletRequest request,Class<T> aClass,String identifierId,Boolean useLoad){
		if(hasRequestParameter(request,identifierId))
			//if(Boolean.TRUE.equals(useLoad))
			//	return (T) RootBusinessLayer.getInstance().getGenericBusiness().load(aClass,getRequestParameterAsLong(request,identifierId));
			//else
				return (T) inject(BusinessInterfaceLocator.class).injectTyped(aClass).find(getRequestParameterAsLong(request,identifierId));
		return null;
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(HttpServletRequest request,Class<T> aClass,String identifierId){
		return getIdentifiableFromRequestParameter(request, aClass, identifierId,Boolean.FALSE);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(Class<T> aClass,String identifierId,Boolean useLoad){
		return getIdentifiableFromRequestParameter(Faces.getRequest(),aClass,identifierId,useLoad);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(Class<T> aClass,String identifierId){
		return getIdentifiableFromRequestParameter(Faces.getRequest(),aClass,identifierId,Boolean.FALSE);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(HttpServletRequest request,Class<T> aClass,Boolean useBusinessEntityInfosIdentifier,Boolean useLoad){
		return getIdentifiableFromRequestParameter(request,aClass,Boolean.TRUE.equals(useBusinessEntityInfosIdentifier) 
				? UIManager.getInstance().businessEntityInfos(aClass).getIdentifier() 
				: UniformResourceLocatorParameter.IDENTIFIABLE,useLoad);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(HttpServletRequest request,Class<T> aClass,Boolean useBusinessEntityInfosIdentifier){
		return getIdentifiableFromRequestParameter(request, aClass, useBusinessEntityInfosIdentifier,Boolean.FALSE);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(Class<T> aClass,Boolean useBusinessEntityInfosIdentifier,Boolean useLoad){
		return getIdentifiableFromRequestParameter(Faces.getRequest(),aClass,useBusinessEntityInfosIdentifier,useLoad);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(Class<T> aClass,Boolean useBusinessEntityInfosIdentifier){
		return getIdentifiableFromRequestParameter(Faces.getRequest(),aClass,useBusinessEntityInfosIdentifier,Boolean.FALSE);
	}
	
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(HttpServletRequest request,Class<T> aClass){
		return getIdentifiableFromRequestParameter(request,aClass,Boolean.FALSE);
	}
	public <T extends AbstractIdentifiable> T getIdentifiableFromRequestParameter(Class<T> aClass){
		return getIdentifiableFromRequestParameter(Faces.getRequest(),aClass);
	}
	
	public <T extends AbstractIdentifiable> Collection<T> getIdentifiablesFromRequestParameter(Class<T> aClass){
		return decodeIdentifiablesRequestParameter(aClass);
	}
	
	public Object[] getRequestParameterMapAsArray(){
		Collection<Object> parametersCollection = new ArrayList<Object>();
		HttpServletRequest request = Faces.getRequest();
		for(Entry<String, String[]> entry : request.getParameterMap().entrySet()){
			parametersCollection.add(entry.getKey());
			if(entry.getValue()!=null && entry.getValue().length>0)
				parametersCollection.add(entry.getValue()[0]);
		}
		return parametersCollection.toArray();
	}
	
	public Crud getCrudFromRequestParameter(HttpServletRequest request){
		return UniformResourceLocatorParameterBusinessImpl.getCrudAsObject(getRequestParameter(request,UniformResourceLocatorParameter.CRUD));
	}
	public Crud getCrudFromRequestParameter(){
		return getCrudFromRequestParameter(Faces.getRequest());
	}
	
	/**/
	
	public String getIdentifierParameterName(){
		return UniformResourceLocatorParameter.IDENTIFIABLE;
	}
	
}
