package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandListener;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.model.AbstractQueryFormModel;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.api.data.collector.control.WebInput;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractSelectPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage<ENTITY> implements CommandListener,Serializable {

	private static final long serialVersionUID = -7392513843271510254L;

	private SelectPageListener.Type type = SelectPageListener.Type.IDENTIFIABLE;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		
		for(SelectPageListener<?,?> selectPageListener : getListeners())
			selectPageListener.initialisationStarted(this);
		
		form.setShowCommands(Boolean.TRUE);
		form.getSubmitCommandable().setLabel(text("command.ok"));
		for(SelectPageListener<?,?> selectPageListener : getListeners()){
			SelectPageListener.Type v = selectPageListener.getType();
			if(v!=null)
				type = v;
		}
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			@Override
			public Boolean build(Field field) {
				switch(type){
				case IDENTIFIER:
					return AbstractQueryFormModel.FIELD_IDENTIFIER.equals(field.getName());
				case IDENTIFIABLE:
					return AbstractQueryFormModel.FIELD_IDENTIFIABLE.equals(field.getName());
				}
				return super.build(field);
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
		
		addInputListener(AbstractQueryFormModel.FIELD_IDENTIFIER,new WebInput.WebInputListener.Adapter.Default(){
			private static final long serialVersionUID = 1L;
			@Override
			public void validate(FacesContext facesContext,UIComponent uiComponent, Object value)throws ValidatorException {
				identifiable = find((String) value);
				if(identifiable==null)
					webManager.throwValidationExceptionUnknownValue(value);
				super.validate(facesContext, uiComponent, value);
			}
		});
		
		addInputListener(AbstractQueryFormModel.FIELD_IDENTIFIABLE,new WebInput.WebInputListener.Adapter.Default(){
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
		WebNavigationManager.getInstance().redirectToDynamicConsultOne(identifiable);
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
