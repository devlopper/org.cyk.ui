package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.userinterface.container.window.ListWindow;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonListWindow extends ListWindow implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	public static class DataTable extends org.cyk.utility.common.userinterface.collection.DataTable implements Serializable {
		private static final long serialVersionUID = 1L;
		
		public DataTable() {
			super(Person.class);
			//columns
			addColumn("code", "globalIdentifier.code");
			addColumn("name", "globalIdentifier.name");
			addColumn("lastnames", "lastnames");
			addColumn("sex", "sex");
			//rows
			//addManyRow(inject(PersonBusiness.class).findAll());
		}
		
	}
	
	
}
