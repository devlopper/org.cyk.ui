package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.party.person.AbstractPersonDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.FormConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPersonListPage<PERSON extends AbstractIdentifiable> extends AbstractPartyListPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static abstract class AbstractPersonListPageAdapter<PERSON extends AbstractIdentifiable> extends AbstractPartyListPageAdapter<PERSON> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPersonListPageAdapter(Class<PERSON> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = getFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractPersonDetails.FIELD_LASTNAMES);	
		}
		
		/**/
		
		public static class Default extends AbstractPersonListPageAdapter<Person> implements Serializable{

			private static final long serialVersionUID = -2256223077759190879L;

			public Default() {
				super(Person.class);
			}
			
		}
		
	}
}
