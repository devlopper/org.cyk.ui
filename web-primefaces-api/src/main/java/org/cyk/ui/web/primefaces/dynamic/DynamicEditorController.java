package org.cyk.ui.web.primefaces.dynamic;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.utility.common.AbstractMethod;

@Named
@ViewScoped
@Getter
@Setter
public class DynamicEditorController extends AbstractDynamicBusinessEntityPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	protected PrimefacesEditor editor;
	protected Crud crud;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		try {
			editor = (PrimefacesEditor) editorInstance(identifiable==null?businessEntityInfos.getClazz().newInstance():identifiable,crud = crudFromRequestParameter());
			editor.setShowCommands(Boolean.FALSE);
			editor.getSubmitCommand().getCommand().setAfterSuccessNotificationMessageMethod(new AbstractMethod<Object,Object>(){
				private static final long serialVersionUID = -9058153097352454644L;
				@Override
				protected Object __execute__(Object parameter) {
					/*
					//if(Boolean.TRUE.equals(getRenderedAsDialog()))
					//	RequestContext.getCurrentInstance().closeDialog(parameter);
					//else
					*/
					if(Boolean.TRUE.equals(editor.getSubmitMethodMainExecuted()))
						messageDialogOkButtonOnClick=webManager.javaScriptWindowHref(url);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public Boolean getShowContentMenu() {
		return crud!=null && !Crud.READ.equals(crud);
	}
	/*
	@Override
	public Boolean getOnDocumentBeforeUnLoadWarn() {
		return Crud.CREATE.equals(crud) || Crud.UPDATE.equals(crud);
	}*/
}
