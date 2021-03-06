package org.cyk.ui.web.api;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.security.RoleBusiness;
import org.cyk.system.root.business.api.userinterface.style.CascadeStyleSheetBusiness;
import org.cyk.system.root.business.impl.network.UniformResourceLocatorParameterBusinessImpl;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.AbstractWindow;
import org.cyk.ui.api.Icon;
import org.cyk.ui.api.IdentifierProvider;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UIMessageManager.SeverityType;
import org.cyk.ui.api.UIMessageManager.Text;
import org.cyk.ui.api.UserDeviceType;
import org.cyk.ui.api.command.AbstractCommandable;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.command.menu.DefaultMenu;
import org.cyk.ui.api.data.collector.control.Control;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.FormOneData;
import org.cyk.ui.api.model.table.AbstractTable;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.api.data.collector.control.WebOutput;
import org.cyk.ui.web.api.security.RoleManager;
import org.omnifaces.util.Ajax;
import org.omnifaces.util.Faces;

public abstract class AbstractWebPage<EDITOR,ROW,OUTPUTLABEL,INPUT,COMMANDABLE extends AbstractCommandable> extends AbstractWindow<EDITOR,ROW,OUTPUTLABEL,INPUT,SelectItem,COMMANDABLE> implements 
	WebUIPage<EDITOR,OUTPUTLABEL,INPUT>,Serializable { 

	private static final long serialVersionUID = -7284361545083572063L;
	
	public static final Layout DEFAULT_LAYOUT = new Layout(); 
	
	@Inject transient protected WebManager webManager;
	//protected AbstractWebUserSession session;
	@Inject transient protected WebNavigationManager navigationManager;
	@Inject transient protected JavaScriptHelper javaScriptHelper;
	@Inject protected RoleBusiness roleBusiness;
	@Inject transient protected RoleManager roleManager;
	
	@Getter @Setter protected String footer,messageDialogOkButtonOnClick="",url,previousUrl,onDocumentReadyJavaScript="",onDocumentLoadJavaScript="",
		onDocumentBeforeUnLoadJavaScript="",onDocumentBeforeUnLoadWarningMessage,selectedTabId,formConfigurationIdentifier;
	@Getter @Setter protected Boolean onDocumentBeforeUnLoadWarn,renderViewError;
	private String windowMode;
	@Getter private Layout layout = new Layout(DEFAULT_LAYOUT);
	
	@Override
	protected void initialisation() {
		super.initialisation();
		actionIdentifier = requestParameter(UniformResourceLocatorParameter.ACTION_IDENTIFIER);
		//locale = getUserSession().getLocale();
		//layout.setWest("/org.cyk.ui.web.primefaces/include/layout/default/north.xhtml");
		footer=  UIManager.getInstance().getWindowFooter();
		windowMode = requestParameter(UniformResourceLocatorParameter.WINDOW_MODE);
		if(StringUtils.isEmpty(windowMode))
			windowMode = UniformResourceLocatorParameter.WINDOW_MODE_NORMAL;
		url = navigationManager.getRequestUrl();
		previousUrl = requestParameter(UniformResourceLocatorParameter.PREVIOUS_URL);
		selectedTabId = requestParameter(UniformResourceLocatorParameter.TAB_ID);
		formConfigurationIdentifier = requestParameter(UniformResourceLocatorParameter.FORM_IDENTIFIER);
		
		onDocumentBeforeUnLoadWarningMessage = UIManager.getInstance().text("window.closing.warning");
		
		/*
		Collection<Field> fields = webManager.getRequestParameterFieldsMap().get(getClass());
		if(fields==null){
			//scan to find all properties annotated with @RequestParameter
			webManager.getRequestParameterFieldsMap().put((Class<? extends AbstractWebPage<?,?, ?, ?>>) getClass(), 
					fields = commonUtils.getAllFields(getClass(), RequestParameter.class));
		}
		*/
		
		//System.out.println("AbstractWebPage.initialisation() : "+fields);	
		/*
		for(Field field : fields){
			RequestParameter requestParameter = field.getAnnotation(RequestParameter.class);
			Class<? extends AbstractIdentifiable> clazz = (Class<? extends AbstractIdentifiable>) (AbstractIdentifiable.class.equals(requestParameter.type())?field.getType():requestParameter.type());
			try {
				FieldUtils.writeField(field, this, identifiableFromRequestParameter(clazz), Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		*/
		
		//getMessageManager().notifications(session.getNotifications()).showGrowl();
		//System.out.println("AbstractWebPage.initialisation() : "+FacesContext.getCurrentInstance().getMessageList());
		//System.out.println("AbstractWebPage.initialisation()");
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		for(FormOneData<?, ?, ?, ?, ?, ?> form : formOneDataCollection.getCollection())
			for(Control<?, ?, ?, ?, ?> control : form.getSelectedFormData().getControlSets().iterator().next().getControls()){
				if(control instanceof WebInput<?,?,?,?>)
					inject(CascadeStyleSheetBusiness.class).addClasses(((WebInput<?,?,?,?>)control).getCss(),"cyk-ui-form-inputfield");
				else if(control instanceof WebOutput<?, ?, ?, ?>)
					;//((WebOutput<?,?,?,?>)control).getCss().addClass("cyk-ui-form-inputfield");
			}
		
	}
	
	@Override
	protected void setUserDeviceType() {
		userDeviceType = navigationManager.isMobileView()?UserDeviceType.PHONE:UserDeviceType.DESKTOP;
	}
		
	public void onPreRenderView(){
		if(!Boolean.TRUE.equals(FacesContext.getCurrentInstance().isPostback())){
			//System.out.println("AbstractWebPage.onPreRenderView()");
			if(Boolean.TRUE.equals(isRenderViewAllowed()))
				initialiseView();
			else{
				renderViewNotAllowed();
				/*String outcome = renderViewNotAllowedOutcome();
				if(StringUtils.isNotBlank(outcome))
					navigationManager.handleNavigation(outcome);*/
			}
		}
	}
	
	protected Boolean isRenderViewAllowed(){return Boolean.TRUE;}
	protected void initialiseView(){}
	protected void renderViewNotAllowed(){}
	protected String renderViewNotAllowedOutcome(){return "outcomecannotrenderview";}
	
	protected void renderViewErrorMessage(String summary,String details){
		getMessageManager().message(SeverityType.ERROR, new Text(summary, Boolean.FALSE), new Text(details, Boolean.FALSE));
		renderViewError = Boolean.TRUE;
	}
	
	@Override
	public Boolean getShowFooter() {
		return getShowMainMenu();
	}
	
	@Override
	public Boolean getShowMainMenu() {
		return UniformResourceLocatorParameter.WINDOW_MODE_NORMAL.equals(windowMode);
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return getShowMainMenu();
	}
	
	@Override
	public Boolean getRenderedAsDialog() {
		return UniformResourceLocatorParameter.WINDOW_MODE_DIALOG.equals(windowMode);
	}
	
	
	
	/*
	@Override
	public String getTitle() {
		if(Boolean.TRUE.equals(getShowMainMenu()))
			return super.getTitle();
		return contentTitle;
	}*/
	
	public String getOnDocumentBeforeUnLoadWarnAsString(){
		return Boolean.TRUE.equals(getOnDocumentBeforeUnLoadWarn())?Boolean.TRUE.toString():Boolean.FALSE.toString();
	}
	
	protected String hide(String path,Boolean onDocumentLoad){
		String script = javaScriptHelper.hide(path);
		if(Boolean.TRUE.equals(onDocumentLoad))
			onDocumentLoadJavaScript += script;
		return script;
	}
	
	@SuppressWarnings("unchecked")
	public void setFieldValue(FormOneData<?, ?, ?, ?, ?, ?> form,String inputName,Object value){
		Input<Object, ?, ?, ?, ?, ?> input = ((Input<Object, ?, ?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, inputName));
		if(input==null)
			return;
		if(Boolean.TRUE.equals(input.getReadOnly()) && !Boolean.TRUE.equals(((Input<?, ?, ?, ?, ?, ?>)input).getKeepShowingInputOnReadOnly())){
			onCompleteUpdate((WebInput<?, ?, ?, ?>) input, value,Boolean.FALSE);
			//onCompleteUpdate((WebInput<?, ?, ?, ?>) input, value,Boolean.TRUE);
		}else
			((Input<Object, ?, ?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, inputName)).setValue(value);
	}
	
	@SuppressWarnings("unchecked")
	public void onCompleteUpdate(WebInput<?, ?, ?, ?> input,Object value,Boolean onServer){
		if(Boolean.TRUE.equals(onServer)){
			((Input<Object, ?, ?, ?, ?, ?>) input).setValue(value);
		}else{
			Ajax.oncomplete(javaScriptHelper.update(input, value));
		}
	}
	
	public void onCompleteUpdate(WebInput<?, ?, ?, ?> input,Object value){
		onCompleteUpdate(input, value, Boolean.TRUE);
	}
	
	public void onCompleteUpdate(FormOneData<?, ?, ?, ?, ?, ?> form,String inputName,Object value,Boolean onServer){
		onCompleteUpdate((WebInput<?, ?, ?, ?>) form.findInputByClassByFieldName(Input.class, inputName),value,onServer);
	}
	
	protected void onCompleteUpdate(FormOneData<?, ?, ?, ?, ?, ?> form,String inputName,Object value){
		onCompleteUpdate(form, inputName, value, Boolean.TRUE);
	}
	
	//TODO those methods should delegate to web manager
	
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass,String identifierId){
		if(hasRequestParameter(identifierId))
			//return (T) getGenericBusiness().load(aClass,requestParameterLong(identifierId));//TODO to be deleted
			//return (T) inject(BusinessInterfaceLocator.class).injectTyped(aClass).find(requestParameterLong(identifierId));
			return webManager.getIdentifiableFromRequestParameter(aClass, identifierId, Boolean.FALSE);
		return null;
	}
	
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass){
		return identifiableFromRequestParameter(aClass, UniformResourceLocatorParameter.IDENTIFIABLE);
	}
	
	protected Crud crudFromRequestParameter(){
		Crud crud = UniformResourceLocatorParameterBusinessImpl.getCrudAsObject(requestParameter(UniformResourceLocatorParameter.CRUD));
		if(crud==null)
			crud = Crud.valueOf(StringUtils.upperCase(requestParameter("action")));
		return crud;
	}
	
	protected Boolean hasRequestParameter(String name){
		return StringUtils.isNotEmpty(Faces.getRequestParameter(name));
	}
	
	protected String requestParameter(String name){
		return Faces.getRequestParameter(name);
	}
	
	protected Long requestParameterLong(Class<? extends AbstractIdentifiable> identifiableClass){
		return requestParameterLong(UIManager.getInstance().businessEntityInfos(identifiableClass).getIdentifier());
	}
	
	protected Long requestParameterLong(String name){
		try {
			return Long.parseLong(requestParameter(name));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**/
		
	/**/
	
	protected void redirectToDynamicCrudOne(AbstractIdentifiable identifiable,Crud crud){
		WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable, Crud.UPDATE);
	}
	protected void redirectToEditOne(AbstractIdentifiable identifiable){
		redirectToDynamicCrudOne(identifiable,Crud.UPDATE);
	}
	protected void redirectToDeleteOne(AbstractIdentifiable identifiable){
		redirectToDynamicCrudOne(identifiable,Crud.DELETE);
	}
	
	/**/
	
	public AjaxBuilder createAjaxBuilder(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName){
		return new AjaxBuilder().form(form).fieldName(fieldName).event(AjaxListener.EVENT_CHANGE);
	}
	
	protected String inputRowVisibility(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Boolean visible){
		return javaScriptHelper.getFormInputVisibility(form,fieldName, visible);
	}
	
	protected void onComplete(String...scripts){
		Ajax.oncomplete(scripts);
	}
	
	@SuppressWarnings("unchecked")
	protected <TYPE> TYPE fieldValue(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Class<TYPE> typeClass,TYPE nullValue){
		return (TYPE) form.getInputByFieldName(fieldName).getValue();
	}
	
	protected BigDecimal bigDecimalValue(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,BigDecimal nullValue){
		return fieldValue(form,fieldName, BigDecimal.class,nullValue);
	}
	protected BigDecimal bigDecimalValue(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName){
		return bigDecimalValue(form, fieldName, BigDecimal.ZERO);
	}
	protected String stringValue(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,String nullValue){
		return fieldValue(form,fieldName, String.class,nullValue);
	}
	protected Date dateValue(FormOneData<?, ?, ?, ?, ?, ?> form,String fieldName,Date nullValue){
		return fieldValue(form,fieldName, Date.class,nullValue);
	}
	
	protected UICommandable addDetailsMenuCommandable(String identifier,String labelId,Icon icon,String outcome){
		if(detailsMenu==null)
			detailsMenu = new DefaultMenu();
		UICommandable commandable = detailsMenu.addCommandable(labelId, icon, outcome);
		commandable.setIdentifier(identifier);
		commandable.getParameters().addAll(NavigationHelper.getInstance().getParameters(url));
		commandable.setParameter(UniformResourceLocatorParameter.TAB_ID, commandable.getIdentifier());
		
		return commandable;
	}
	public UICommandable removeDetailsMenuCommandable(String identifier){
		if(detailsMenu==null)
			return null;
		return detailsMenu.remove(identifier);
	}
	/*
	protected UICommandable addDetailsMenuCommandable(String labelId,IconType iconType,String outcome){
		return addDetailsMenuCommandable(labelId,labelId, iconType, outcome);
	}*/
	
	protected Boolean isDetailsMenuCommandable(String identifier,Boolean defaultValue){
		if(StringUtils.isBlank(selectedTabId)){
			return defaultValue;
		}
		return identifier.equals(/*detailsMenu==null?*/selectedTabId/*:detailsMenu.getRequestedCommandable().getIdentifier()*/);
	}
	
	protected Boolean isDetailsMenuCommandable(Class<?> aClass,AbstractIdentifiable identifiable){
		return 
				//Boolean.TRUE.equals(getWindowsInstanceManager().isShowDetails(aClass,identifiable,this)) && 
				isDetailsMenuCommandable(IdentifierProvider.Adapter.getTabOf(aClass), Boolean.FALSE);
	}
	
	protected Boolean setRenderedIfDetailsMenuCommandable(String identifier,FormOneData<?, ?, ?, ?, ?, ?> formOneData,Boolean defaultDetails){
		formOneData.setRendered(isDetailsMenuCommandable(identifier,defaultDetails));
		return formOneData.getRendered();
	}
	protected Boolean setRenderedIfDetailsMenuCommandable(String identifier,FormOneData<?, ?, ?, ?, ?, ?> formOneData){
		return setRenderedIfDetailsMenuCommandable(identifier, formOneData, Boolean.FALSE);
	}
	protected Boolean setRenderedIfDetailsMenuCommandable(String identifier,AbstractTable<?, ?, ?> table,Boolean defaultDetails){
		table.setRendered(isDetailsMenuCommandable(identifier,defaultDetails));
		return table.getRendered();
	}
	protected Boolean setRenderedIfDetailsMenuCommandable(String identifier,AbstractTable<?, ?, ?> table){
		return setRenderedIfDetailsMenuCommandable(identifier, table,Boolean.FALSE);
	}
	
	
	/**/
	
	protected void addInputListener(FormOneData<?, EDITOR, ROW, OUTPUTLABEL, INPUT, SelectItem> form,String fieldName,WebInput.Listener listener){
		WebInput<?, ?, ?, ?> webInput = ((WebInput<?, ?, ?, ?>)form.getInputByFieldName(fieldName));
		if(webInput==null)
			;//logError("Cannot add web input listener to field named {} because field not found", fieldName);
		else
			webInput.getWebInputListeners().add(listener);
	}

	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static class Layout{
		private String west;
		public Layout(Layout layout) {
			west = layout.west;
		}
	}
	
	public static class PageInstanceManager extends WindowInstanceManager implements Serializable {

		private static final long serialVersionUID = 2796674164692183550L;

	}
	
}
