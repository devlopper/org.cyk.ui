package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.impl.geography.LocalityBusinessImpl;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.ui.web.primefaces.data.collector.control.OutputCollection;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class OutputCollectionDemoPage extends AbstractPrimefacesPage implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private OutputCollection<Country> countryCollection;
	private OutputCollection<Locality> localityCollection;
	
	@Override
	protected void initialisation() { 
		super.initialisation(); 
		countryCollection = new OutputCollection<Country>(Country.class);
		localityCollection = new OutputCollection<Locality>(Locality.class,new String[]{LocalityBusinessImpl.Details.FIELD_CODE,LocalityBusinessImpl.Details.FIELD_NAME});		
	}
	
}
