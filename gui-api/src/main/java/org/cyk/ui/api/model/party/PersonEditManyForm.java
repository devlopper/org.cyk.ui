package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public class PersonEditManyForm extends AbstractItemCollectionItem<Person> implements Serializable{
	
	private static final long serialVersionUID = -829786138986362643L;

	@Input @InputText private String name;
	@Input @InputText private String lastname;
	@Input @InputText private String surname;
	
}