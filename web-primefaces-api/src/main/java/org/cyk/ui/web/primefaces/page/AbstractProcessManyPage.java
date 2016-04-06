package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractProcessManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private String actionIdentifier;
	
	private FormOneData<Object> form;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationStarted(this);
		actionIdentifier = requestParameter(uiManager.getActionIdentifierParameter());
		
		Object formData = null;
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners()){
			Object v = processPageListener.getFormData(this, actionIdentifier);
			if(v!=null)
				formData = v;
		}
		form = (FormOneData<Object>) createFormOneData(formData,Crud.CREATE);
		form.setDynamic(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.initialisationEnded(this);
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationStarted(this);
		
		/*
		addInputListener(AbstractQueryOneFormModel.FIELD_IDENTIFIABLE,new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("unchecked")
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = (ENTITY) value;
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
		*/
		
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier(actionIdentifier);
		return parameters;
	}
		
	@Override
	protected Class<?> __formModelClass__() {
		if(identifiableConfiguration==null || identifiableConfiguration.getFormMap()==null || identifiableConfiguration.getFormMap().getQueryMany()==null){
			logError("No query many form explicitly defined for entity {}",businessEntityInfos.getClazz());
			return null;
		}
		return identifiableConfiguration.getFormMap().getQueryMany();
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.READ;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		for(AbstractProcessManyPage.Listener<?,?> processPageListener : getListeners())
			processPageListener.serve(this,parameter,actionIdentifier);
	}
	
	private Collection<AbstractProcessManyPage.Listener<?,Object>> getListeners(){
		return primefacesManager.getProcessManyPageListeners(businessEntityInfos.getClazz());
	}
		
	/**/
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		/**/
		Object getFormData(AbstractProcessManyPage<?> processManyPage, String actionIdentifier);
		void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			
			@Override
			public Object getFormData(AbstractProcessManyPage<?> processManyPage,String actionIdentifier) {
				return null;
			}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractProcessManyPage<?> processPage = (AbstractProcessManyPage<?>) bean;
				initialiseProcess(processPage);
			}
			
			protected void initialiseProcess(AbstractProcessManyPage<?> processPage){}
						
			@Override
			public void serve(AbstractProcessManyPage<?> processManyPage,Object data, String actionIdentifier) {}
			
			/**/
			
			public static class Default<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends Listener.Adapter<ENTITY,IDENTIFIER_TYPE> implements Serializable {

				private static final long serialVersionUID = -4255109770974601234L;

				public Default(Class<ENTITY> entityTypeClass) {
					super(entityTypeClass);
				}
					
			}
		}
		
	}

}
