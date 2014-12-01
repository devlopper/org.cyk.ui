package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.test.model.MyIdentifiable;
import org.cyk.ui.web.primefaces.page.crud.CrudOnePage;

@Named
@ViewScoped
@Getter
@Setter
public class StaticEditorPage extends CrudOnePage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected void initialisation() { 
		super.initialisation();
		
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return new BusinessEntityInfos(MyIdentifiable.class, languageBusiness);
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.CREATE;
	}

}
