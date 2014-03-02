package org.cyk.ui.web.primefaces.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.form.IForm;
import org.cyk.ui.web.primefaces.component.WebForm;
import org.cyk.ui.web.primefaces.controller.MyEntity.MyDetails;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;

@Named
@ViewScoped
@Getter
@Setter
public class DynaFormController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private WebForm myForm,myForm2;

	@Override
	protected void initialisation() { 
		super.initialisation();

		//myForm = new WebForm(/*viewBuilder,messageManager,new MyEntity()*/);
		MyEntity p;
		myForm.model(p=new MyEntity());
		p.setDetails1(new MyDetails());
		p.getDetails1().setDetailsName("Glory to the lord");
		//myForm.model(p=new MyEntity());
		//p.setName("Only you");
		myForm.build();
		
		//myForm2 = new WebForm(viewBuilder, new MyEntity());
		//myForm2.build(System.class);
	}

}
