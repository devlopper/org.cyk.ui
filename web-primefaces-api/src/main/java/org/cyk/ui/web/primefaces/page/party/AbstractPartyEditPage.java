package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractPartyEditPage<PARTY extends AbstractIdentifiable> extends AbstractCrudOnePage<PARTY> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Party getParty();
	
	/**/
	
	/*public static abstract class AbstractPageAdapter<PARTY extends AbstractIdentifiable> extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<PARTY> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPageAdapter(Class<PARTY> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(AbstractPartyEditFormModel.FIELD_CODE,AbstractPartyEditFormModel.FIELD_NAME);
			configuration.addFieldNames(AbstractPartyEditFormModel.FIELD_IMAGE);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(AbstractPersonEditFormModel.FIELD_CODE,AbstractPersonEditFormModel.FIELD_NAME);
			configuration.addFieldNames(AbstractPartyEditFormModel.FIELD_IMAGE);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(ContactCollection.class).getUserInterface().getLabelId());
			configuration.addFieldNames(AbstractPartyEditFormModel.FIELD_CONTACT_COLLECTION,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER
					,ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_ELECTRONICMAIL,ContactCollectionFormModel.FIELD_POSTALBOX
					,ContactCollectionFormModel.FIELD_HOME_LOCATION);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addRequiredFieldNames(AbstractPersonEditFormModel.FIELD_CODE,AbstractPersonEditFormModel.FIELD_NAME);
			configuration.addFieldNames(AbstractPartyEditFormModel.FIELD_IMAGE);
		}
		
	}*/
}
