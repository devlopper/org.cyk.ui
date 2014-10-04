package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.editor.AbstractFormData;
import org.cyk.ui.web.primefaces.PrimefacesEditor;

@Getter
@Setter
public abstract class AbstractEditorPage<ENTITY extends AbstractIdentifiable> extends AbstractBusinessEntityPrimefacesPage<ENTITY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected Crud crud;
	protected PrimefacesEditor editor;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() { 
		super.initialisation();
		crud = crudFromRequestParameter();
		
		editor = (PrimefacesEditor) editorInstance(data(uiManager.formData((Class<? extends AbstractIdentifiable>) businessEntityInfos.getClazz())),crud);
		editor.setShowCommands(Boolean.FALSE);
		/*
		editor.getSubmitCommand().getCommand().setExecuteMethod(new AbstractMethod<Object, Object>() {
			private static final long serialVersionUID = 3338121576606276955L;
			@Override
			protected Object __execute__(Object parameter) {
				try {
					edit();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});
		*/
		/*
		editor.getSubmitCommand().getCommand().setAfterSuccessNotificationMessageMethod(new AbstractMethod<Object,Object>(){
			private static final long serialVersionUID = -9058153097352454644L;
			@Override
			protected Object __execute__(Object parameter) {
				if(Boolean.TRUE.equals(editor.getSubmitMethodMainExecuted()))
					messageDialogOkButtonOnClick=webManager.javaScriptWindowHref(url);
				return null;
			}
		});*/
	}
	/*
	@Override
	public void transfer(UICommand command, Object parameter) throws Exception {
		super.transfer(command, parameter);
		editor.updateValues();
	}
	
	@Override
	public Object succeed(UICommand command, Object parameter) {
		super.succeed(command, parameter);
		if(Boolean.TRUE.equals(editor.getSubmitMethodMainExecuted()))
			messageDialogOkButtonOnClick=webManager.javaScriptWindowHref(url);
		return null;
	}
	*/
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
	/*
	@SuppressWarnings("unchecked")
	protected Object createIdentifiableFormData(Class<?> dataClass) throws InstantiationException, IllegalAccessException{
		identifiable = (ENTITY) businessEntityInfos.getClazz().newInstance();
		if(AbstractFormData.class.isAssignableFrom(dataClass)){
			AbstractFormData<AbstractIdentifiable> data = (AbstractFormData<AbstractIdentifiable>) dataClass.newInstance();
			data.setIdentifiable(identifiable);
			data.initialise();
			return data;
		}else{
			return identifiable;
		}
	}
	*/
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
