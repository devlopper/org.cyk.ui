package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.collections.CollectionUtils;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.data.collector.form.FormConfiguration.Type;
import org.cyk.ui.api.model.party.AbstractPartyFormModel;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.web.primefaces.page.DefaultBusinessEntityFormManyPageAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;
import org.cyk.utility.common.cdi.AbstractBean;

public class DefaultActorCrudManyPageListener<ACTOR extends AbstractActor> extends DefaultBusinessEntityFormManyPageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public DefaultActorCrudManyPageListener(Class<ACTOR> entityTypeClass) {
		super(entityTypeClass);
		FormConfiguration configuration = new FormConfiguration(Type.INPUT_SET_SMALLEST);
		configuration.addRequiredFieldNames(AbstractPartyFormModel.FIELD_NAME);
		configuration.addFieldNames(DefaultPersonEditFormModel.FIELD_LAST_NAME);
		getReadFormConfigurationMap().put(configuration.getType(), configuration);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void initialisationEnded(AbstractBean bean) {
		super.initialisationEnded(bean);
		final AbstractCrudManyPage<AbstractActor> page = (AbstractCrudManyPage<AbstractActor>) bean;
		page.getTable().getColumnListeners().add(new ColumnAdapter(){
			
			@Override
			public Boolean isColumn(Field field) {
				FormConfiguration configuration = readFormConfigurationMap.entrySet().iterator().next().getValue();
				return configuration==null || CollectionUtils.isEmpty(configuration.getFieldNames())?super.isColumn(field):configuration.getFieldNames().contains(field.getName());
			}
			
		});
		
	}
		
}
