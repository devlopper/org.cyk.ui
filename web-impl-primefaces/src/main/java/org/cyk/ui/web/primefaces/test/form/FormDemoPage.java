package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.data.collector.form.AbstractControlSet;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.primefaces.extensions.component.dynaform.DynaForm;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;

@Named
@ViewScoped
@Getter
@Setter
public class FormDemoPage extends AbstractPrimefacesPage implements  Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private AbstractControlSet<?, DynaForm,?, DynaFormLabel, DynaFormControl, ?> controlSet;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		
	}



}
