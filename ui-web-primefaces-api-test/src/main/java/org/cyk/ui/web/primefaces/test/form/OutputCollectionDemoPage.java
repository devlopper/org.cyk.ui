package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.geography.CountryBusiness;
import org.cyk.system.root.business.api.information.TagBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.geography.CountryBusinessImpl;
import org.cyk.system.root.business.impl.information.TagBusinessImpl;
import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.information.Tag;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.web.primefaces.data.collector.control.OutputCollection;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.utility.common.computation.DataReadConfiguration;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class OutputCollectionDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private OutputCollection<Person> personCollection;
	private OutputCollection<Country> countryCollection;
	private OutputCollection<Tag> tagCollection;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		personCollection = new OutputCollection<Person>(Person.class,inject(PersonBusiness.class).findAll());
		personCollection.addElementObjectFieldsAsColumns(PersonBusinessImpl.Details.FIELD_CODE,PersonBusinessImpl.Details.FIELD_NAME,PersonBusinessImpl.Details.FIELD_LASTNAMES
				,PersonBusinessImpl.Details.FIELD_BIRTH_DATE);
		personCollection.get__nameColumn__().setIsShowable(Boolean.FALSE);
		
		countryCollection = new OutputCollection<Country>(Country.class,inject(CountryBusiness.class).findAll(new DataReadConfiguration(0l, 3l)));
		countryCollection.addElementObjectFieldsAsColumns(CountryBusinessImpl.Details.FIELD_CODE,CountryBusinessImpl.Details.FIELD_NAME);
		countryCollection.get__nameColumn__().setIsShowable(Boolean.FALSE);
		countryCollection.setIsLazyLoaded(Boolean.TRUE);
		
		tagCollection = new OutputCollection<Tag>(Tag.class,inject(TagBusiness.class).findAll(new DataReadConfiguration(0l, 10l)));
		tagCollection.addElementObjectFieldsAsColumns(TagBusinessImpl.Details.FIELD_CODE,TagBusinessImpl.Details.FIELD_NAME);
		tagCollection.get__nameColumn__().setIsShowable(Boolean.FALSE);
		
	}
	
}
