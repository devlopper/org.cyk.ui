package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.geography.LocalityBusinessImpl;
import org.cyk.system.root.business.impl.information.TagBusinessImpl;
import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.data.collector.control.OutputCollection;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class OutputCollectionDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private OutputCollection<Person> personCollection;
	private OutputCollection<Country> countryCollection;
	private OutputCollection<Locality> localityCollection;
	private OutputCollection<Tag> tagCollection;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		personCollection = new OutputCollection<Person>(Person.class,new String[]{PersonBusinessImpl.Details.FIELD_CODE,PersonBusinessImpl.Details.FIELD_NAME,PersonBusinessImpl.Details.FIELD_LASTNAMES
				,PersonBusinessImpl.Details.FIELD_BIRTH_DATE},inject(PersonBusiness.class).findAll());
		
		countryCollection = new OutputCollection<Country>(Country.class);
		
		localityCollection = new OutputCollection<Locality>(Locality.class,new String[]{LocalityBusinessImpl.Details.FIELD_CODE,LocalityBusinessImpl.Details.FIELD_NAME});
		
		tagCollection = new OutputCollection<Tag>(Tag.class,new String[]{TagBusinessImpl.Details.FIELD_CODE,TagBusinessImpl.Details.FIELD_NAME});
		
	}
	
}
