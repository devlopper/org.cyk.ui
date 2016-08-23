package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness.FindDoSomethingTextParameters;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.Identifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.AbstractQueryOneFormModel;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractSelectOnePage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private AbstractSelectOnePage.Listener.Type type = AbstractSelectOnePage.Listener.Type.IDENTIFIABLE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationStarted(this);
	
		//form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners()){
			AbstractSelectOnePage.Listener.Type v = selectPageListener.getType();
			if(v!=null)
				type = v;
		}
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			public Boolean build(Object data,Field field) {
				if(type==null){
					return super.build(data,field);
				}else
					switch(type){
					case IDENTIFIER:
						return AbstractQueryOneFormModel.FIELD_IDENTIFIER.equals(field.getName());
					case IDENTIFIABLE:
						return !AbstractQueryOneFormModel.FIELD_IDENTIFIER.equals(field.getName())/*AbstractQueryFormModel.FIELD_IDENTIFIABLE.equals(field.getName())*/;
					case CUSTOM:
						return super.build(data,field);
					default:
						return super.build(data,field);
					}
			}
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Field field) {
				if(AbstractQueryOneFormModel.FIELD_IDENTIFIABLE.equals(field.getName())){
					Class<?> aClass = null;
					for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
						if(selectPageListener.getEntityTypeClass()!=null)
							aClass = selectPageListener.getEntityTypeClass();
					if(aClass!=null)
						return languageBusiness.findClassLabelText(aClass);
				}
				return super.fiedLabel(controlSet, field);
			}
		});
		
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationEnded(this);
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationStarted(this);
		
		addInputListener(AbstractQueryOneFormModel.FIELD_IDENTIFIER,new WebInput.Listener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = find((String) value);
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
		
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
		
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected FindDoSomethingTextParameters getContentTitleDoSomethingTextParameters() {
		FindDoSomethingTextParameters parameters = super.getContentTitleDoSomethingTextParameters();
		parameters.setActionIdentifier("choice");
		parameters.setForWhat(languageBusiness.findActionIdentifierText(actionIdentifier, businessEntityInfos, Boolean.TRUE));
		return parameters;
	}
		
	@Override
	protected Class<?> __formModelClass__() {
		if(identifiableConfiguration==null || identifiableConfiguration.getFormMap()==null || identifiableConfiguration.getFormMap().getQueryOne()==null){
			logError("No query form explicitly defined for entity {}",businessEntityInfos.getClazz());
			return null;
		}
		return identifiableConfiguration.getFormMap().getQueryOne();
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
		if(StringUtils.isBlank(actionIdentifier)){
			WebNavigationManager.getInstance().redirectToDynamicConsultOne(identifiable);
		}else{
			for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners())
				selectPageListener.serve(parameter,actionIdentifier);
		}	
	}
	
	protected Class<ENTITY> identifiableClass(){
		Class<ENTITY> identifiableClass = null;
		for(AbstractSelectOnePage.Listener<?,?> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			Class<ENTITY> v = (Class<ENTITY>) selectPageListener.getEntityTypeClass();
			if(v!=null)
				identifiableClass = v;
		}
		return identifiableClass;
	}
	
	protected ENTITY find(Object identifier){
		ENTITY entity = null;
		for(AbstractSelectOnePage.Listener<?,Object> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			ENTITY v = (ENTITY) selectPageListener.findByIdentifier(identifier);
			if(v!=null)
				entity = v;
		}
		return entity;
	}
	
	private Collection<AbstractSelectOnePage.Listener<?,Object>> getListeners(){
		return AbstractSelectOnePage.Listener.Adapter.getListeners(businessEntityInfos);
	}
	
	/**/
	
	public interface Listener<ENTITY extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener<ENTITY> {

		Collection<Listener<?,?>> COLLECTION = new ArrayList<>();
		
		/**/
		
		ENTITY findByIdentifier(IDENTIFIER_TYPE identifier);
		
		Type getType();
		void setType(Type type);
		
		void serve(Object data, String actionIdentifier);
		
		@Getter @Setter
		public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable,IDENTIFIER_TYPE> extends BusinessEntityFormOnePageListener.Adapter<ENTITY_TYPE> implements Listener<ENTITY_TYPE,IDENTIFIER_TYPE>,Serializable {

			private static final long serialVersionUID = -7944074776241690783L;

			protected Type type = Type.IDENTIFIABLE;
			
			public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
				super(entityTypeClass);
			}

			@Override
			public ENTITY_TYPE findByIdentifier(IDENTIFIER_TYPE identifier) {
				logWarning("findByIdentifier has not been override , so it will always return null");
				return null;
			}
			
			@Override
			public void afterInitialisationEnded(AbstractBean bean) {
				super.afterInitialisationEnded(bean);
				final AbstractSelectOnePage<?> selectPage = (AbstractSelectOnePage<?>) bean;
				initialiseSelect(selectPage);
			}
			
			protected void initialiseSelect(AbstractSelectOnePage<?> selectPage){}
			
			@Override
			public void serve(Object data, String actionIdentifier) {}
			
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
					
			}
		}
		
		public static enum Type{IDENTIFIER,IDENTIFIABLE,CUSTOM}
		
	}

}
