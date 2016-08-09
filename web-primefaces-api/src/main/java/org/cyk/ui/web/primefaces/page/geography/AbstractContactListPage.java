package org.cyk.ui.web.primefaces.page.geography;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.geography.AbstractContactDetails;
import org.cyk.system.root.model.geography.Contact;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Getter @Setter
public abstract class AbstractContactListPage<IDENTIFIABLE extends Contact> extends AbstractCrudManyPage<IDENTIFIABLE> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	public static class AbstractAdapter<IDENTIFIABLE extends Contact> extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<IDENTIFIABLE> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractAdapter(Class<IDENTIFIABLE> aClass) {
			super(aClass);
			FormConfiguration configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractContactDetails.FIELD_VALUE);
		}
		
	}
}
