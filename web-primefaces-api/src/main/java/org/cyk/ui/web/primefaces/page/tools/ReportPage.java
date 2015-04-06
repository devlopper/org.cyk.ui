package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;

@Named @RequestScoped @Getter @Setter
public class ReportPage extends AbstractReportPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = text("data.report")+" - "+contentTitle;
	}
	
	@Override
	protected String url(){
		String url = navigationManager.url(requestParameter(webManager.getRequestParameterOutcome()), new Object[]{
				webManager.getRequestParameterClass(),requestParameter(webManager.getRequestParameterClass())
				,webManager.getRequestParameterIdentifiable(),requestParameter(webManager.getRequestParameterIdentifiable())
				,uiManager.getFileExtensionParameter(),fileExtension
				,webManager.getRequestParameterPrint(),requestParameter(webManager.getRequestParameterPrint())
				,uiManager.getReportIdentifierParameter(),requestParameter(uiManager.getReportIdentifierParameter())
			},Boolean.FALSE,Boolean.FALSE);
		return url;
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.FALSE;
	}
	
	
}
