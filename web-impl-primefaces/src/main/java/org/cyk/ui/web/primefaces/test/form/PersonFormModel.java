package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.Person;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class PersonFormModel extends AbstractFormModel<Person> implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;

	@Input
	@InputText
	private String nom;
	
	@Override
	public void read() {
		super.read();
		nom = identifiable.getName();
	}

}
