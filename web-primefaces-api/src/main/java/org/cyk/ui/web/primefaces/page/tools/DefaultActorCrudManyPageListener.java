package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.model.party.AbstractPartyFormModel;
import org.cyk.ui.api.model.party.PersonFormModel;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.web.primefaces.page.DefaultBusinessEntityFormManyPageAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;
import org.cyk.utility.common.cdi.AbstractBean;

public class DefaultActorCrudManyPageListener<ACTOR extends AbstractActor> extends DefaultBusinessEntityFormManyPageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public DefaultActorCrudManyPageListener(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		onReadFields.add(AbstractPartyFormModel.FIELD_NAME);
		onReadFields.add(PersonFormModel.FIELD_LAST_NAME);
		//onCreateFields.add(ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void initialisationEnded(AbstractBean bean) {
		super.initialisationEnded(bean);
		final AbstractCrudManyPage<AbstractActor> page = (AbstractCrudManyPage<AbstractActor>) bean;
		page.getTable().getColumnListeners().add(new ColumnAdapter(){
			
			@Override
			public Boolean isColumn(Field field) {
				return onReadFields.contains(field.getName());
			}
			
		});
		
	}
		
}
