package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.web.primefaces.dynamic.EditorPage;

@Named
@ViewScoped
@Getter
@Setter
public class TestDynamicEditorController extends EditorPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() { 
		super.initialisation();
		editor.debug();
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(Event.class);
	}
	
	

}
