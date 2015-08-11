package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.omnifaces.util.Faces;

@Named @RequestScoped @Getter @Setter
public class ReportPage extends AbstractReportPage<AbstractIdentifiable> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		contentTitle = null;//text("data.report")+" - "+contentTitle;
	}
	
	@Override
	protected String url(){
		Collection<Object> parametersCollection = new ArrayList<Object>();
		HttpServletRequest request = Faces.getRequest();
		for(Entry<String, String[]> entry : request.getParameterMap().entrySet()){
			parametersCollection.add(entry.getKey());
			if(entry.getValue()!=null && entry.getValue().length>0)
				parametersCollection.add(entry.getValue()[0]);
		}
		Object[] parametersArray = parametersCollection.toArray();
		/*
		String url = navigationManager.url(requestParameter(webManager.getRequestParameterOutcome()), new Object[]{
				webManager.getRequestParameterClass(),requestParameter(webManager.getRequestParameterClass())
				,webManager.getRequestParameterIdentifiable(),requestParameter(webManager.getRequestParameterIdentifiable())
				,uiManager.getFileExtensionParameter(),fileExtension
				,webManager.getRequestParameterPrint(),requestParameter(webManager.getRequestParameterPrint())
				,uiManager.getReportIdentifierParameter(),requestParameter(uiManager.getReportIdentifierParameter())
			},Boolean.FALSE,Boolean.FALSE);
		*/
		return navigationManager.url(requestParameter(webManager.getRequestParameterOutcome()), parametersArray,Boolean.FALSE,Boolean.FALSE);
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.FALSE;
	}
	
	
}
