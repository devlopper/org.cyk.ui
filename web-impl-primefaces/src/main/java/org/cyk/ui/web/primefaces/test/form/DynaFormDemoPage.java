package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.primefaces.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.data.collector.control.InputText;
import org.cyk.ui.web.primefaces.data.collector.form.ControlSet;
import org.cyk.ui.web.primefaces.data.collector.form.Form;
import org.cyk.ui.web.primefaces.data.collector.form.FormData;

@Named
@ViewScoped
@Getter
@Setter
public class DynaFormDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private EntityWithAnnotation entityWithAnnotation = new EntityWithAnnotation();
	private Form<EntityWithAnnotation> form;
	private FormData<EntityWithAnnotation> formData;
	private ControlSet<EntityWithAnnotation> controlSet;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		form = new Form<>();
		
		formData = form.createFormData();
		formData.setData(entityWithAnnotation);
		controlSet = formData.createControlSet();
		
		controlSet.setTemplate("/org.cyk.ui.web.primefaces/template/controlset/default.xhtml");
		controlSet.setTitle("MyControlSet");
				
		controlSet.row().addLabel("Libelle").add(new InputText())
				.row().addLabel("Libelle 2").add(new InputText());
		
		controlSet.addField("inputText");
		
		form.build();
	}

}
