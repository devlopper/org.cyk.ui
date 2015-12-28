package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.AbstractQueryFormModel;
import org.cyk.ui.web.api.WebInputListener;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public abstract class AbstractSelectPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private SelectPageListener.Type type = SelectPageListener.Type.IDENTIFIABLE;
	
	private String actionIdentifier;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(SelectPageListener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationStarted(this);
		actionIdentifier = requestParameter(uiManager.getActionIdentifierParameter());
		//form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		for(SelectPageListener<?,?> selectPageListener : getListeners()){
			SelectPageListener.Type v = selectPageListener.getType();
			if(v!=null)
				type = v;
		}
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				if(type==null){
					return super.build(field);
				}else
					switch(type){
					case IDENTIFIER:
						return AbstractQueryFormModel.FIELD_IDENTIFIER.equals(field.getName());
					case IDENTIFIABLE:
						return !AbstractQueryFormModel.FIELD_IDENTIFIER.equals(field.getName())/*AbstractQueryFormModel.FIELD_IDENTIFIABLE.equals(field.getName())*/;
					case CUSTOM:
						return super.build(field);
					default:
						return super.build(field);
					}
			}
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Field field) {
				if(AbstractQueryFormModel.FIELD_IDENTIFIABLE.equals(field.getName())){
					Class<?> aClass = null;
					for(SelectPageListener<?,?> selectPageListener : getListeners())
						if(selectPageListener.getEntityTypeClass()!=null)
							aClass = selectPageListener.getEntityTypeClass();
					if(aClass!=null)
						return languageBusiness.findClassLabelText(aClass);
				}
				return super.fiedLabel(controlSet, field);
			}
		});
		
		for(SelectPageListener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationEnded(this);
	}
		
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
		for(SelectPageListener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationStarted(this);
		
		addInputListener(AbstractQueryFormModel.FIELD_IDENTIFIER,new WebInputListener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = find((String) value);
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
		
		addInputListener(AbstractQueryFormModel.FIELD_IDENTIFIABLE,new WebInputListener.Adapter.Default(){
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
		
		for(SelectPageListener<?,?> selectPageListener : getListeners())
			selectPageListener.afterInitialisationEnded(this);
	}
	
	@Override
	protected Class<?> __formModelClass__() {
		if(identifiableConfiguration==null || identifiableConfiguration.getFormMap()==null || identifiableConfiguration.getFormMap().getQuery()==null){
			logError("No query form explicitly defined for entity {}",businessEntityInfos.getClazz());
			return null;
		}
		return identifiableConfiguration.getFormMap().getQuery();
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
			for(SelectPageListener<?,?> selectPageListener : getListeners())
				selectPageListener.serve(parameter,actionIdentifier);
		}	
	}
	
	protected Class<ENTITY> identifiableClass(){
		Class<ENTITY> identifiableClass = null;
		for(SelectPageListener<?,?> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			Class<ENTITY> v = (Class<ENTITY>) selectPageListener.getEntityTypeClass();
			if(v!=null)
				identifiableClass = v;
		}
		return identifiableClass;
	}
	protected ENTITY find(Object identifier){
		ENTITY entity = null;
		for(SelectPageListener<?,Object> selectPageListener : getListeners()){
			@SuppressWarnings("unchecked")
			ENTITY v = (ENTITY) selectPageListener.findByIdentifier(identifier);
			if(v!=null)
				entity = v;
		}
		return entity;
	}
	
	private Collection<SelectPageListener<?,Object>> getListeners(){
		return primefacesManager.getSelectPageListeners(businessEntityInfos.getClazz());
	}
	
}
