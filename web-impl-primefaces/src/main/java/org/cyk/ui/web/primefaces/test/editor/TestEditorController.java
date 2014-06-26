package org.cyk.ui.web.primefaces.test.editor;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.PrimefacesEditor;
import org.cyk.ui.web.primefaces.test.Master;

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
		try {
			editor = (PrimefacesEditor) editorInstance(new Master(),Crud.CREATE);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	


}
