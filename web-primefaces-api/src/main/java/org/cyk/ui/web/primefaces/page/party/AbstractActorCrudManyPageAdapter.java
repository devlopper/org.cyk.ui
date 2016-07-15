package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.party.AbstractActorOutputDetails;
import org.cyk.ui.api.model.party.DefaultActorOutputDetails;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.api.model.table.RowAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormManyPage;
import org.cyk.utility.common.cdi.AbstractBean;

public abstract class AbstractActorCrudManyPageAdapter<ACTOR extends AbstractActor> extends AbstractBusinessEntityFormManyPage.BusinessEntityFormManyPageListener.Adapter.Default<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public AbstractActorCrudManyPageAdapter(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		FormConfiguration configuration = createFormConfiguration(Crud.READ, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
		configuration.addRequiredFieldNames(DefaultActorOutputDetails.FIELD_REGISTRATION_CODE);
		configuration.addRequiredFieldNames(DefaultActorOutputDetails.FIELD_FIRST_NAME);
		configuration.addFieldNames(DefaultActorOutputDetails.FIELD_LAST_NAME);
		//configuration.addFieldNames(ContactCollectionReadFormModel.FIELD_CONTACTS); // Because of lazy load
	}
	
	@Override
	public Boolean canRedirect(Crud crud, Object data) {
		return Boolean.TRUE;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ACTOR getIdentifiable(Object data) {
		return ((AbstractActorOutputDetails<ACTOR>)data).getMaster()/*Identifiable()*/;
	}
	
	@Override
	public Boolean canRedirectToConsultView(Object data) {
		return Boolean.TRUE;
	}
	
	@Override
	public void initialisationEnded(AbstractBean bean) {
		super.initialisationEnded(bean);
		AbstractBusinessEntityFormManyPage<?> page = (AbstractBusinessEntityFormManyPage<?>) bean;
		page.getTable().setShowOpenCommand(Boolean.TRUE);
		page.getTable().getRowListeners().add(new RowAdapter<Object>(){
			@Override
			public void added(Row<Object> row) {
				super.added(row);
				row.setOpenable(Boolean.TRUE);
			}
		});
	}
	
	/**/
	
	public static class Default<ACTOR extends AbstractActor> extends AbstractActorCrudManyPageAdapter<ACTOR> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Default(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}
		
	}

	
}
