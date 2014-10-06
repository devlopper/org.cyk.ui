package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.CommandAdapter;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.editor.AbstractFormData;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;

@Getter
@Setter
public abstract class AbstractBusinessEntityFormOnePage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	protected FormOneData<Object> form;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		crud = crudFromRequestParameter();
		form = (FormOneData<Object>) createFormOneData(data(uiManager.formData((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz())),crud);
		form.setShowCommands(Boolean.FALSE);
		form.getSubmitCommandable().getCommand().getCommandListeners().add(new CommandAdapter(){
			private static final long serialVersionUID = -4119943624542439662L;
			@Override
			public void serve(UICommand command, Object parameter) {
				super.serve(command, parameter);
				try {
					__serve__(parameter);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public Object succeed(UICommand command, Object parameter) {
				messageDialogOkButtonOnClick=webManager.javaScriptWindowHref(url);
				return super.succeed(command, parameter);
			}
		});
	}
	
	protected abstract void __serve__(Object parameter);
	
	@SuppressWarnings("unchecked")
	protected Object data(Class<?> aClass){
		try{
			if(identifiable==null)
				identifiable =  (ENTITY) businessEntityInfos.getClazz().newInstance();
			return identifiableFormData(aClass);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	protected Object identifiableFormData(Class<?> dataClass) throws InstantiationException, IllegalAccessException{
		if(AbstractFormData.class.isAssignableFrom(dataClass)){
			@SuppressWarnings("unchecked")
			AbstractFormData<AbstractIdentifiable> data = (AbstractFormData<AbstractIdentifiable>) dataClass.newInstance();
			data.setIdentifiable(identifiable);
			data.read();
			return data;
		}else{
			return identifiable;
		}
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return crud!=null && !Crud.READ.equals(crud);
	}
}
