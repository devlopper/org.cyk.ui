package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.api.WebManager;
import org.cyk.ui.web.api.WebNavigationManager;

public interface BusinessEntityFormPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener<ENTITY> {

	/**
	 * Set of form configuration to create , read , update and delete an entity
	 * @return
	 */
	Map<Crud,Map<String,FormConfiguration>> getFormConfigurationMap();
	
	Boolean canRedirect(Crud crud,Object data);
	
	void redirect(Crud crud,Object data);
	
	Boolean canRedirectToConsultView(Object data);
	void redirectToConsultView(Object data);
	
	ENTITY getIdentifiable(Object data);
	
	Class<?> getFormModelClass();
	
	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityPrimefacesPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormPageListener<ENTITY_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		@Getter protected Map<Crud,Map<String,FormConfiguration>> formConfigurationMap = new HashMap<>();
		
		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
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
						new Object[]{WebManager.getInstance().getRequestParameterClass(),UIManager.getInstance().keyFromClass(businessEntityInfos)
					,WebManager.getInstance().getRequestParameterIdentifiable(),identifiable.getIdentifier(),
					UIManager.getInstance().getCrudParameter(),businessEntityInfos.getUserInterface().getEditViewId().equals(businessEntityInfos.getUserInterface().getConsultViewId())
					?UIManager.getInstance().getCrudReadParameter():null});
			}
		}

		@Override
		public ENTITY_TYPE getIdentifiable(Object data) {
			return null;
		}
		
		/**/
		
		protected FormConfiguration getFormConfiguration(Crud crud,String type){
			if(formConfigurationMap.isEmpty())
				return null;
			Map<String,FormConfiguration> formMap = formConfigurationMap.get(crud);
			if(formMap==null || formMap.isEmpty())
				return null;
			return StringUtils.isBlank(type)?formMap.entrySet().iterator().next().getValue():formMap.get(type);
		}
		
		protected FormConfiguration getFormConfiguration(Crud crud){
			return getFormConfiguration(crud, null);
		}
		
		protected FormConfiguration createFormConfiguration(Crud crud,String type){
			FormConfiguration formConfiguration = new FormConfiguration(type);
			Map<String,FormConfiguration> formMap = formConfigurationMap.get(crud);
			if(formMap==null)
				formMap = new HashMap<>();
			formMap.put(type==null?FormConfiguration.TYPE_INPUT_SET_DEFAULT:type, formConfiguration);
			formConfigurationMap.put(crud, formMap);
			return formConfiguration;
		}
		
		protected FormConfiguration createFormConfiguration(Crud crud){
			return createFormConfiguration(crud, null);
		}

		
		@Override
		public Class<?> getFormModelClass() {
			return null;
		}

	}
	
}
