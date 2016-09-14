package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.Table;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractProcessManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private Table<ProcessItem> table;
	private Collection<ENTITY> elements;
	private Boolean showForm;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationStarted(this);
		Class<ENTITY> entityClass = (Class<ENTITY>) businessEntityInfos.getClazz();
		
		Collection<Long> identifiers = webManager.decodeIdentifiersRequestParameter();
		elements = (Collection<ENTITY>) genericBusiness.use((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz()).findByIdentifiers(identifiers);
		
		@SuppressWarnings("rawtypes")
		DetailsConfigurationListener.Table.Adapter listener = new DetailsConfigurationListener.Table.Adapter<ENTITY,ProcessItem>(entityClass
				, ProcessItem.class){
			private static final long serialVersionUID = 1L;
			@Override
			public Collection<ENTITY> getIdentifiables() {
				return elements;
			}
			
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
			@Override
			public String getTabId() {
				return "1";
			}
		};
		table = createDetailsTable(entityClass, listener);
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Boolean v = processPageListener.getShowForm(this,actionIdentifier);
			if(v!=null)
				showForm = v;
		}
		
		/*
		Object formData = null;
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Object v = processPageListener.getFormData(this, actionIdentifier);
			if(v!=null)
				formData = v;
		}*/
		/*
		form = (FormOneData<Object>) createFormOneData(formData,Crud.CREATE);
		form.setDynamic(Boolean.TRUE);
		*/
		form.getSubmitCommandable().setLabel(text("command.execute"));
	
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationEnded(this);
	}
	
	@Override
	public String getContentTitle() {
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			String v = processPageListener.getContentTitle(this,actionIdentifier);
			if(v!=null)
				contentTitle = v;
		}
		return contentTitle;
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationStarted(this);
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected <T extends AbstractIdentifiable> T identifiableFromRequestParameter(Class<T> aClass, String identifierId) {
		return null;
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}

	@Override
	protected Class<?> __formModelClass__() {
		Class<?> formDataClass = null;
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Class<?> v = processPageListener.getFormDataClass(this, actionIdentifier);
			if(v!=null)
				formDataClass = v;
		}
		return formDataClass;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.serve(this,parameter,actionIdentifier);
	}
	
	private Collection<AbstractProcessManyPage.Listener<?,Object>> getListeners(){
		return AbstractProcessManyPage.Listener.Adapter.getListeners(businessEntityInfos.getClazz());
	}
		
	/**/
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		Collection<Listener<?,?>> COLLECTION = new ArrayList<>();
		/**/
		Class<?> getFormDataClass(AbstractProcessManyPage<?> processManyPage, String actionIdentifier);
		Object getFormData(AbstractProcessManyPage<?> processManyPage, String actionIdentifier);
		Boolean getShowForm(AbstractProcessManyPage<?> processManyPage,String actionIdentifier);
		void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier);
		String getContentTitle(AbstractProcessManyPage<?> processManyPage,String actionIdentifier);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			@Override
			public Class<?> getFormDataClass(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			@Override
			public Object getFormData(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			@Override
			public Boolean getShowForm(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return Boolean.FALSE;
			}
			@Override
			public String getContentTitle(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			@Override
			public void initialisationEnded(AbstractBean bean) {
				super.initialisationEnded(bean);
				final AbstractProcessManyPage<?> processPage = (AbstractProcessManyPage<?>) bean;
				initialiseProcessOnInitialisationEnded(processPage);
			}
			
			protected void initialiseProcessOnInitialisationEnded(AbstractProcessManyPage<?> processPage){}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractProcessManyPage<?> processPage = (AbstractProcessManyPage<?>) bean;
				initialiseProcessOnAfterInitialisationEnded(processPage);
			}
			
			protected void initialiseProcessOnAfterInitialisationEnded(AbstractProcessManyPage<?> processPage){}
						
			@Override
			public void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier) {}
			
			@SuppressWarnings("unchecked")
			public static Collection<Listener<?,Object>> getListeners(Class<? extends Identifiable<?>> aClass){
				Collection<Listener<?,Object>> results = new ArrayList<>();
				if(aClass!=null)
					for(Listener<?,?> listener : Listener.COLLECTION)
						if(listener.getEntityTypeClass().isAssignableFrom(aClass))
							results.add((Listener<?, Object>) listener);
				return results;
			}
			public static Collection<Listener<?,Object>> getListeners(BusinessEntityInfos businessEntityInfos){
				return getListeners(businessEntityInfos==null ? null : businessEntityInfos.getClazz());
			}
			
			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends Listener.Adapter<ENTITY,IDENTIFIER_TYPE> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
				@Override
				public String getContentTitle(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
					return inject(LanguageBusiness.class).findActionIdentifierText(actionIdentifier, processManyPage.getBusinessEntityInfos(), Boolean.FALSE).getValue();
				}	
			}
		}
		
	}

	/**/
	
	@Getter @Setter
	public static class ProcessItem extends AbstractOutputDetails<AbstractIdentifiable> implements Serializable{
		private static final long serialVersionUID = 2840997923510834856L;
		
		@Input @InputText private String name;
		
		public ProcessItem(AbstractIdentifiable identifiable) {
			super(identifiable);
			name = rootBusinessLayer.getFormatterBusiness().format(identifiable);
		}
	}
}
