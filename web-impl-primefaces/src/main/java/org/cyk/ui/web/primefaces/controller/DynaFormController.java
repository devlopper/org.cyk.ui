package org.cyk.ui.web.primefaces.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.component.WebFormContainer;

@Named
@ViewScoped
@Getter
@Setter
public class DynaFormController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private WebFormContainer myForm;

	@Override
	protected void initialisation() { 
		super.initialisation();
	}
	


}
