package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.collections.CollectionUtils;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;
import org.cyk.utility.common.cdi.AbstractBean;

public class DefaultBusinessEntityFormManyPageAdapter<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormManyPageAdapter<ENTITY> implements Serializable {

	private static final long serialVersionUID = -4255109770974601234L;

	public DefaultBusinessEntityFormManyPageAdapter(Class<ENTITY> entityTypeClass) {
		super(entityTypeClass);
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
