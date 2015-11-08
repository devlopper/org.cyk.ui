package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.data.collector.form.FormConfiguration.Type;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.primefaces.page.DefaultBusinessEntityFormOnePageAdapter;

public abstract class AbstractActorCrudOnePageAdapter<ACTOR extends AbstractActor> extends DefaultBusinessEntityFormOnePageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public AbstractActorCrudOnePageAdapter(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		FormConfiguration configuration = createFormConfiguration(Crud.CREATE, Type.INPUT_SET_SMALLEST);
		configuration.addRequiredFieldNames(DefaultPersonEditFormModel.FIELD_NAME);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_LAST_NAME);
		//configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_MOBILE_PHONE_NUMBER);
		//configuration.addFieldNames(ContactCollectionEditFormModel.FIELD_ELECTRONICMAIL);
		
	}

}
