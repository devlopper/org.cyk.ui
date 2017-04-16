package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.security.Software;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class SoftwareEditPage extends AbstractCrudOnePage<Software> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.UPDATE;
	}
	
	/**/
	
	@Getter @Setter
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Software> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
		
		
		
	}

}
