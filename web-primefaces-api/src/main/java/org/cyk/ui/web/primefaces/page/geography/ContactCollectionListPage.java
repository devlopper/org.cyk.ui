package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.geography.ContactCollectionDetails;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class ContactCollectionListPage extends AbstractCrudManyPage<ContactCollection> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static class Adapter extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<ContactCollection> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(ContactCollection.class);
			FormConfiguration configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(ContactCollectionDetails.FIELD_CODE,ContactCollectionDetails.FIELD_NAME);
		}
		
	}
}
