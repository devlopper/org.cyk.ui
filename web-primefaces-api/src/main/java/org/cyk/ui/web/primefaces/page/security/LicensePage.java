package org.cyk.ui.web.primefaces.page.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.ui.api.command.UICommand;

@Named
@RequestScoped
@Getter
@Setter
public class LicensePage extends AbstractLicensePage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Override
	public void serve(UICommand command, Object parameter) {
		
	}
	
	@Override
	protected Crud crudFromRequestParameter() {
		return Crud.READ;
	}
	

}
