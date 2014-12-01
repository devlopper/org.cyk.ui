package org.cyk.ui.web.api;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractWindow;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.UserSession;
import org.cyk.ui.web.api.annotation.RequestParameter;
import org.omnifaces.util.Faces;

public abstract class AbstractWebPage<EDITOR,ROW,OUTPUTLABEL,INPUT> extends AbstractWindow<EDITOR,ROW,OUTPUTLABEL,INPUT,SelectItem> implements 
	WebUIPage<EDITOR,OUTPUTLABEL,INPUT>,Serializable { 

	private static final long serialVersionUID = -7284361545083572063L;
	
	@Inject transient protected WebManager webManager;
	@Inject protected WebSession session;
	@Inject transient protected WebNavigationManager navigationManager;
	@Inject transient protected JavaScriptHelper javaScriptHelper;
	
	@Getter @Setter protected String footer,messageDialogOkButtonOnClick,url,previousUrl,onDocumentReadyJavaScript,onDocumentLoadJavaScript,
		onDocumentBeforeUnLoadJavaScript,onDocumentBeforeUnLoadWarningMessage;
	@Getter @Setter protected Boolean onDocumentBeforeUnLoadWarn;
	private String windowMode;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		locale = session.getLocale();
		footer=  UIManager.getInstance().getWindowFooter();
		windowMode = requestParameter(webManager.getRequestParameterWindowMode());
		if(StringUtils.isEmpty(windowMode))
			windowMode = webManager.getRequestParameterWindowModeNormal();
		url = navigationManager.getRequestUrl();
		previousUrl = requestParameter(webManager.getRequestParameterPreviousUrl());
		onDocumentBeforeUnLoadWarningMessage = UIManager.getInstance().text("window.closing.warning");
		
		Collection<Field> fields = webManager.getRequestParameterFieldsMap().get(getClass());
		if(fields==null){
			//scan to find all properties annotated with @RequestParameter
			webManager.getRequestParameterFieldsMap().put((Class<? extends AbstractWebPage<?,?, ?, ?>>) getClass(), 
					fields = commonUtils.getAllFields(getClass(), RequestParameter.class));
		}
		
		
		//System.out.println("AbstractWebPage.initialisation() : "+fields);	
		for(Field field : fields){
			RequestParameter requestParameter = field.getAnnotation(RequestParameter.class);
			Class<? extends AbstractIdentifiable> clazz = (Class<? extends AbstractIdentifiable>) (AbstractIdentifiable.class.equals(requestParameter.type())?field.getType():requestParameter.type());
			try {
				FieldUtils.writeField(field, this, identifiableFromRequestParameter(clazz), Boolean.TRUE);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public UserSession getUserSession() {
		return session;
	}
	
	@Override
	public Boolean getShowFooter() {
		return getShowMainMenu();
	}
	
	@Override
	public Boolean getShowMainMenu() {
		return webManager.getRequestParameterWindowModeNormal().equals(windowMode);
	}
	
	@Override
	public Boolean getRenderedAsDialog() {
		return webManager.getRequestParameterWindowModeDialog().equals(windowMode);
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
	
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass,String identifierId){
		if(hasRequestParameter(identifierId))
			return (T) getGenericBusiness().load(aClass,requestParameterLong(identifierId));
		return null;
	}
	
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass){
		return identifiableFromRequestParameter(aClass, webManager.getRequestParameterIdentifiable());
	}
	
	protected Crud crudFromRequestParameter(){
		return getUiManager().getCrudValue(requestParameter(getUiManager().getCrudParameter()));
	}
	
	protected Boolean hasRequestParameter(String name){
		return StringUtils.isNotEmpty(Faces.getRequestParameter(name));
	}
	
	protected String requestParameter(String name){
		return Faces.getRequestParameter(name);
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
	
}
