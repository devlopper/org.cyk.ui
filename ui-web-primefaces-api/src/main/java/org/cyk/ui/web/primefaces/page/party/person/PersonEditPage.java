package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonEditPage;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.Form.Master.SubmitCommandActionAdapter;
import org.cyk.utility.common.userinterface.input.InputText;
import org.cyk.utility.common.userinterface.input.InputTextarea;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private org.cyk.utility.common.userinterface.container.Form.Master form2;
	private Form d = new Form();
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		form2 = new org.cyk.utility.common.userinterface.container.Form.Master(SubmitCommandActionAdapter.class)
				.setObject(d).setLabelFromIdentifier("editperson");
		
		org.cyk.utility.common.userinterface.container.Form.Detail formDetail = form2.instanciateDetail(org.cyk.utility.common.userinterface.Layout.Type.ADAPTIVE);
    	InputText c1 = new InputText();
    	c1.setField(d, Form.FIELD_NAME);
    	InputText c2 = new InputText();
    	c2.setField(d, Form.FIELD_LAST_NAMES);
    	InputText c3 = new InputText();
    	c3.setField(d, Form.FIELD_DESCRIPTION);
    	InputText c4 = new InputText();
    	c4.setField(d, Form.FIELD_BIRTH_DATE);
    	InputText c5 = new InputText();
    	c5.setField(d, Form.FIELD_BIRTH_LOCATION);
    	InputText c6 = new InputText();
    	c6.setField(d, Form.FIELD_SEX);
    	InputTextarea c7 = new InputTextarea();
    	c7.setField(d, Form.FIELD_OTHER_DETAILS);
    	c7.getArea().getWidth().setDistance(2);
    	InputText c8 = new InputText();
    	c8.setField(d, Form.FIELD_NATIONALITY);
    	
    	formDetail.layOut(c1).layOut(c2).layOutBreak().layOut(c3).layOutBreak().layOut(c4).layOut(c5).layOutBreak().layOut(c6).layOut(c7).layOutBreak().layOut(c8).layOutBreak();
		
		form2.build();
	}
	
	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends /*AbstractPersonEditFormModel.Extends.Default*/AbstractPersonEditPage.AbstractForm<Person> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;

		@Override
		protected Person getPerson() {
			return identifiable;
		}
		
	}
	
}

