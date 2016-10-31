package org.cyk.ui.web.api;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.UIWindow;

public interface WebUIPage<FORM,OUTPUTLABEL,INPUT> extends UIWindow<FORM,OUTPUTLABEL,INPUT,SelectItem> {
	
	public void setFooter(String footer);
	
	public String getFooter();
	
	Boolean getShowFooter();
	
	String getUrl();
	
	String getOnDocumentReadyJavaScript();
	
	String getOnDocumentLoadJavaScript();
	
	String getOnDocumentBeforeUnLoadJavaScript();
	
	Boolean getOnDocumentBeforeUnLoadWarn();
	
	String getOnDocumentBeforeUnLoadWarningMessage();

}
