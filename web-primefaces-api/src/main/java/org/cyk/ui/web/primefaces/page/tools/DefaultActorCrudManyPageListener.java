package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.data.collector.form.FormConfiguration.Type;
import org.cyk.ui.api.model.geography.ContactCollectionReadFormModel;
import org.cyk.ui.api.model.party.AbstractActorReadFormModel;
import org.cyk.ui.api.model.party.DefaultActorReadFormModel;
import org.cyk.ui.web.api.WebNavigationManager;
import org.cyk.ui.web.primefaces.page.DefaultBusinessEntityFormManyPageAdapter;

public class DefaultActorCrudManyPageListener<ACTOR extends AbstractActor> extends DefaultBusinessEntityFormManyPageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public DefaultActorCrudManyPageListener(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		FormConfiguration configuration = new FormConfiguration(Type.INPUT_SET_SMALLEST);
		configuration.addRequiredFieldNames(DefaultActorReadFormModel.FIELD_REGISTRATION_CODE);
		configuration.addRequiredFieldNames(DefaultActorReadFormModel.FIELD_FIRST_NAME);
		configuration.addFieldNames(DefaultActorReadFormModel.FIELD_LAST_NAME);
		configuration.addFieldNames(ContactCollectionReadFormModel.FIELD_CONTACTS);
		getReadFormConfigurationMap().put(configuration.getType(), configuration);
	}
	
	@Override
	public Boolean canRedirect(Crud crud, Object data) {
		return Boolean.TRUE;
	}
	
	@Override
	public void redirect(Crud crud, Object data) {
		WebNavigationManager.getInstance().redirectToDynamicCrudOne(((AbstractActorReadFormModel<ACTOR>)data).getIdentifiable(),crud);
	}
		
}
