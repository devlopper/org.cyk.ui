package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.collections.CollectionUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.table.ColumnAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;
import org.cyk.utility.common.cdi.AbstractBean;

public interface BusinessEntityFormManyPageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormPageListener<ENTITY> {

	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormManyPageListener<ENTITY_TYPE>,Serializable {
		private static final long serialVersionUID = -7944074776241690783L;

		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}
		
		/**/
		
		public static class Default<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormManyPageListener.Adapter<ENTITY> implements Serializable {
			private static final long serialVersionUID = -4255109770974601234L;

			public Default(Class<ENTITY> entityTypeClass) {
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
						FormConfiguration configuration = getFormConfiguration(Crud.READ);
						return configuration==null || CollectionUtils.isEmpty(configuration.getFieldNames())?super.isColumn(field):configuration.getFieldNames().contains(field.getName());
					}
					
				});	
			}		
		}
	}


}
