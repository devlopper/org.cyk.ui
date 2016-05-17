package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.geography.ContactCollectionEditFormModel;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;

public abstract class AbstractActorCrudOnePageAdapter<ACTOR extends AbstractActor> extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public AbstractActorCrudOnePageAdapter(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
		configuration.addRequiredFieldNames(DefaultPersonEditFormModel.FIELD_NAME);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_LAST_NAME);
		/*
		configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_NAME,DefaultPersonEditFormModel.FIELD_LAST_NAME);
		*/
		configuration = createFormConfiguration(Crud.UPDATE, DefaultPersonEditFormModel.TAB_PERSON_ID);
		configuration.addRequiredFieldNames(DefaultPersonEditFormModel.FIELD_NAME);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_LAST_NAME);
		
		configuration = createFormConfiguration(Crud.UPDATE, DefaultPersonEditFormModel.TAB_CONTACT_ID);
		configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_MOBILE_PHONE_NUMBER);
		configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_LAND_PHONE_NUMBER);
		configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_ELECTRONICMAIL);
		configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_POSTALBOX);
		configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_HOME_LOCATION);
		
		configuration = createFormConfiguration(Crud.UPDATE, DefaultPersonEditFormModel.TAB_SIGNATURE_ID);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_SIGNATURE_SPECIMEN);
		
		configuration = createFormConfiguration(Crud.UPDATE, DefaultPersonEditFormModel.TAB_JOB_ID);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_JOB_TITLE);
			
	}
	
	/**/
	
	public static class Default<ACTOR extends AbstractActor> extends AbstractActorCrudOnePageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Default(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}

	}

}
