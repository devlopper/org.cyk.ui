package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.system.root.model.network.UniformResourceLocatorParameter;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.api.WebNavigationManager;

public interface BusinessEntityFormPageListener<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener<ENTITY> {

	Collection<BusinessEntityFormPageListener<?>> COLLECTION = new ArrayList<>();
	
	FormConfiguration getFormConfiguration(Object data,Crud crud);
	
	Boolean canRedirect(Crud crud,Object data);
	
	void redirect(Crud crud,Object data);
	
	Boolean canRedirectToConsultView(Object data);
	void redirectToConsultView(Object data);
	
	ENTITY getIdentifiable(Object data);
	
	Class<?> getFormModelClass();
	
	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage.BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormPageListener<ENTITY_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		@Getter protected Map<Crud,Map<String,FormConfiguration>> formConfigurationMap = new HashMap<>();
		
		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}

		@Override
		public FormConfiguration getFormConfiguration(Object data, Crud crud) {
			return null;
		}
		
		@Override
		public Boolean canRedirect(Crud crud, Object data) {
			return null;
		}

		@Override
		public void redirect(Crud crud, Object data) {
			ENTITY_TYPE identifiable = getIdentifiable(data);
			if(identifiable!=null)
				WebNavigationManager.getInstance().redirectToDynamicCrudOne(identifiable,crud);
		}
		
		@Override
		public Boolean canRedirectToConsultView(Object data) {
			return null;
		}
		
		@Override
		public void redirectToConsultView(Object data) {
			ENTITY_TYPE identifiable = getIdentifiable(data);
			if(identifiable!=null){
				BusinessEntityInfos businessEntityInfos = UIManager.getInstance().businessEntityInfos(identifiable.getClass());
				logTrace("Redirecting to consult view from listener : {} , {} , {}"
						,businessEntityInfos.getUserInterface().getConsultViewId(),businessEntityInfos.getClazz().getSimpleName(),identifiable.getIdentifier());
				WebNavigationManager.getInstance().redirectTo(businessEntityInfos.getUserInterface().getConsultViewId(), 
						new Object[]{UniformResourceLocatorParameter.CLASS,UIManager.getInstance().keyFromClass(businessEntityInfos)
					,UniformResourceLocatorParameter.IDENTIFIABLE,identifiable.getIdentifier(),
					UniformResourceLocatorParameter.CRUD,businessEntityInfos.getUserInterface().getEditViewId().equals(businessEntityInfos.getUserInterface().getConsultViewId())
					?UniformResourceLocatorParameter.CRUD_READ:null});
			}
		}

		@Override
		public ENTITY_TYPE getIdentifiable(Object data) {
			return null;
		}
		
		/**/
		
		@Override
		public Class<?> getFormModelClass() {
			return null;
		}

		public static Collection<BusinessEntityFormPageListener<?>> getBusinessEntityFormPageListeners(Class<? extends Identifiable<?>> aClass){
			Collection<BusinessEntityFormPageListener<?>> results = new ArrayList<>();
			if(aClass!=null)
				for(BusinessEntityFormPageListener<?> listener : BusinessEntityFormPageListener.COLLECTION)
					if(listener.getEntityTypeClass().isAssignableFrom(aClass))
						results.add(listener);
			return results;
		}
	}
	
}
