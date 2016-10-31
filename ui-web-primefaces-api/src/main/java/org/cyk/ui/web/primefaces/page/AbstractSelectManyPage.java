package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindClassLabelTextParameters;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.AbstractQueryManyFormModel;
import org.cyk.ui.api.model.CodesFormModel;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public abstract class AbstractSelectManyPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationStarted(this);
		
		//form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.select"));
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean build(Object data,Field field) {
				if(field.getName().equals(AbstractQueryManyFormModel.FIELD_IDENTIFIABLES))
					return ((AbstractQueryManyFormModel<?,?>)form.getData()).getShowIdentifiables();
				if(field.getDeclaringClass().equals(CodesFormModel.class))
					return ((AbstractQueryManyFormModel<?,?>)form.getData()).getShowCodes();
				return super.build(data,field);
			}
			
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Object data,Field field) {
				if(AbstractQueryManyFormModel.FIELD_IDENTIFIABLES.equals(field.getName())){
					Class<?> aClass = null;
					for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
						if(selectPageListener.getEntityTypeClass()!=null)
							aClass = selectPageListener.getEntityTypeClass();
					if(aClass!=null){
						FindClassLabelTextParameters parameters = new FindClassLabelTextParameters(aClass);
						parameters.setOne(Boolean.FALSE);
						return languageBusiness.findClassLabelText(parameters);
					}
				}
				return super.fiedLabel(controlSet, data,field);
			}
		});
		
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationEnded(this);
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationStarted(this);
		
		setChoices(AbstractQueryManyFormModel.FIELD_IDENTIFIABLES,getIdentifiables());
		
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
		
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier("choice");
		parameters.setForWhat(languageBusiness.findActionIdentifierText(actionIdentifier, businessEntityInfos, Boolean.TRUE).getValue());
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
	protected Object data(Class<?> aClass) {
		return newInstance(aClass);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}

	@Override
	public void serve(UICommand command, Object parameter) {
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.beforeServe(this,parameter,actionIdentifier);
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.serve(this,parameter,actionIdentifier);
	}
	
	protected Class<ENTITY> identifiableClass(){
		Class<ENTITY> identifiableClass = null;
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			Class<ENTITY> v = (Class<ENTITY>) selectPageListener.getEntityTypeClass();
			if(v!=null)
				identifiableClass = v;
		}
		return identifiableClass;
	}
	
	private Collection<AbstractSelectManyPage.Listener<?,Object>> getListeners(){
		return AbstractSelectManyPage.Listener.Adapter.getListeners(businessEntityInfos.getClazz());
	}
	
	/**/
	
	protected Collection<ENTITY> getIdentifiables(){
		Collection<ENTITY> collection = null;
		for(AbstractSelectManyPage.Listener<?,?> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			Collection<ENTITY> v = (Collection<ENTITY>) selectPageListener.getIdentifiables(this);
			if(v!=null)
				collection = v;
		}
		return collection;
	}
	
	/**/
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		Collection<Listener<?,?>> COLLECTION = new ArrayList<>();
		
		/**/
		Collection<ENTITY> getIdentifiables(AbstractSelectManyPage<?> selectManyPage);
		void beforeServe(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier);
		void serve(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier);
		Object[] getParameters(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractSelectManyPage<?> selectPage = (AbstractSelectManyPage<?>) bean;
				initialiseSelect(selectPage);
			}
			
			protected void initialiseSelect(AbstractSelectManyPage<?> selectPage){}
			
			@Override
			public Collection<ENTITY_TYPE> getIdentifiables(AbstractSelectManyPage<?> selectManyPage) {
				return null;
			}
			
			@Override
			public void beforeServe(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier) {}
			@Override
			public void serve(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier) {}
			@Override
			public Object[] getParameters(AbstractSelectManyPage<?> selectManyPage, Object data,String actionIdentifier) {
				return null;
			}
			
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
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public void serve(AbstractSelectManyPage<?> selectManyPage,Object data, String actionIdentifier) {
					if(StringUtils.isBlank(actionIdentifier))
						logTrace("No action identifier found for {} to process many", selectManyPage.getBusinessEntityInfos().getClazz());
					else
						WebNavigationManager.getInstance().redirectToDynamicProcessManyPage((Class<ENTITY>) selectManyPage.getBusinessEntityInfos().getClazz()
							,((AbstractQueryManyFormModel)data).getIdentifiables(), actionIdentifier,getParameters(selectManyPage, data, actionIdentifier));
				}
			}
		}
		
	}

}
