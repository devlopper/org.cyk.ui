package org.cyk.ui.web.primefaces.page.file;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityPrimefacesPage;
import org.omnifaces.util.Faces;

import lombok.Getter;
import lombok.Setter;

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
		for(ReportPageListener<?> listener : ReportPageListener.Adapter.getReportPageListeners(businessEntityInfos)){
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
	
	/**/
	
	public static interface ReportPageListener<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener<ENTITY> {

		Collection<ReportPageListener<?>> COLLECTION = new ArrayList<>();
		
		String getUrl(String outcome,Object[] parameters);

		/**/
		
		public class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements ReportPageListener<ENTITY_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;

			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}

			@Override
			public String getUrl(String outcome, Object[] parameters) {
				return null;
			}
			
			public static Collection<ReportPageListener<?>> getReportPageListeners(Class<? extends Identifiable<?>> aClass){
				Collection<ReportPageListener<?>> results = new ArrayList<>();
				if(aClass!=null)
					for(ReportPageListener<?> listener : ReportPageListener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add(listener);
				return results;
			}
			public static Collection<ReportPageListener<?>> getReportPageListeners(BusinessEntityInfos businessEntityInfos){
				return getReportPageListeners(businessEntityInfos==null ? null : businessEntityInfos.getClazz());
			}


		}
		
	}
}
