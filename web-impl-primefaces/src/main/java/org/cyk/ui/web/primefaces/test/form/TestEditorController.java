package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.editor.EditorInputs;
import org.cyk.ui.test.model.Master;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.utility.common.AbstractMethod;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;

@Named
@ViewScoped
@Getter
@Setter
public class TestEditorController extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private PrimefacesEditor editor;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		setEditorInputsEventListenerMethod(new AbstractMethod<Object, EditorInputs<DynaFormModel,DynaFormLabel,DynaFormControl,SelectItem>>() {
			private static final long serialVersionUID = -5480330737090042896L;
			@Override
			protected Object __execute__(EditorInputs<DynaFormModel,DynaFormLabel,DynaFormControl,SelectItem> editorInputs) {
				editorInputs.getLayout().setColumnsCount(2);
				return null;
			}
		});
		try {
			editor = (PrimefacesEditor) editorInstance(new Master(),Crud.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}

}
