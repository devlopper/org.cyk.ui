package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.party.AbstractPartyDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPartyListPage<PARTY extends AbstractIdentifiable> extends AbstractCrudManyPage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	/**/
	
	/*public static abstract class AbstractPartyListPageAdapter<PARTY extends AbstractIdentifiable> extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<PARTY> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPartyListPageAdapter(Class<PARTY> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = getFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractPartyDetails.FIELD_CODE,AbstractPartyDetails.FIELD_NAME);
		}
		
	}*/
}
