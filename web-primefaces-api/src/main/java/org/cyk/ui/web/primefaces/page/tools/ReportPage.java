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
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.primefaces.page.ReportPageListener;
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
	protected String buildContentTitle() {
		return requestParameter(UniformResourceLocatorParameter.TITLE);
	}
	
	@Override
	protected String url(){
		logTrace("Building report url");
		Collection<Object> parametersCollection = new ArrayList<Object>();
		HttpServletRequest request = Faces.getRequest();
		for(Entry<String, String[]> entry : request.getParameterMap().entrySet()){
			parametersCollection.add(entry.getKey());
			if(entry.getValue()!=null && entry.getValue().length>0)
				parametersCollection.add(entry.getValue()[0]);
		}
		String outcome = requestParameter(UniformResourceLocatorParameter.VIEW_IDENTIFIER);
		Object[] parametersArray = parametersCollection.toArray();
		
		String url = null;
		for(ReportPageListener<?> listener : primefacesManager.getReportPageListeners())
			if(listener.getEntityTypeClass().equals(businessEntityInfos.getClazz())){
				String u = listener.getUrl(outcome, parametersArray);
				if(u!=null)
					url = u;
			}
		
		if(url==null){
			url = navigationManager.url(outcome, parametersArray,Boolean.FALSE,Boolean.FALSE);
		}else{
			
		}
		logTrace("Report url {}", url);
		return url;
	}
	
	@Override
	public Boolean getShowContextualMenu() {
		return Boolean.FALSE;
	}
	
	
}
