package org.cyk.ui.web.api;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.AbstractWindow;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.model.table.Table;
import org.omnifaces.util.Faces;

public abstract class AbstractWebPage<EDITOR,OUTPUTLABEL,INPUT,TABLE extends Table<?>> extends AbstractWindow<EDITOR,OUTPUTLABEL,INPUT,SelectItem,TABLE> implements WebUIPage<EDITOR,OUTPUTLABEL,INPUT,TABLE>,Serializable {

	private static final long serialVersionUID = -7284361545083572063L;
	
	@Inject protected WebManager webManager;
	@Inject protected WebSession session;
	@Inject protected WebNavigationManager navigationManager;
	@Getter @Setter protected String footer,messageDialogOkButtonOnClick,url,onDocumentReadyJavaScript,onDocumentLoadJavaScript,
		onDocumentBeforeUnLoadJavaScript,onDocumentBeforeUnLoadWarningMessage;
	@Getter @Setter protected Boolean onDocumentBeforeUnLoadWarn;
	private String windowMode;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		locale = session.getLocale();
		footer="CYK Systems - All rights Reserved.";
		windowMode = requestParameter(webManager.getRequestParameterWindowMode());
		if(StringUtils.isEmpty(windowMode))
			windowMode = webManager.getRequestParameterWindowModeNormal();
		url = navigationManager.getRequestUrl();
		onDocumentBeforeUnLoadWarningMessage = UIManager.getInstance().text("window.closing.warning");
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
	
}
