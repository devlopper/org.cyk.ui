package org.cyk.ui.web.primefaces.page;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.commons.collections.CollectionUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.cdi.AbstractBean;

public interface BusinessEntityFormOnePageListener<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormPageListener<ENTITY> {

	void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page);
	
	/**/
	
	public static class Adapter<ENTITY_TYPE extends AbstractIdentifiable> extends BusinessEntityFormPageListener.Adapter<ENTITY_TYPE> implements BusinessEntityFormOnePageListener<ENTITY_TYPE>,Serializable {

		private static final long serialVersionUID = -7944074776241690783L;

		public Adapter(Class<ENTITY_TYPE> entityTypeClass) {
			super(entityTypeClass);
		}
		
		@Override
		public void onSucceed(AbstractBusinessEntityFormOnePage<? extends AbstractIdentifiable> page) {
			
		}

		/**/
		
		public static class Default<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormOnePageListener.Adapter<ENTITY> implements Serializable {

			private static final long serialVersionUID = -4255109770974601234L;

			public Default(Class<ENTITY> entityTypeClass) {
				super(entityTypeClass);
			}
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void initialisationEnded(AbstractBean bean) {
				super.initialisationEnded(bean);
				if(bean instanceof AbstractCrudOnePage<?>){
					final AbstractCrudOnePage<AbstractActor> page = (AbstractCrudOnePage<AbstractActor>) bean;
					//TODO should be checked before adding because POJO reuse is possible by container. Is it real idea ???
					page.getForm().getControlSetListeners().add(new ControlSetAdapter(){
						
						@Override
						public Boolean build(Field field) {
							FormConfiguration configuration = getFormConfiguration(page.getCrud(),page.getSelectedTabId());
							if(configuration==null || CollectionUtils.isEmpty(configuration.getFieldNames()))
								return super.build(field);
							else
								return configuration.getFieldNames().contains(field.getName());
						}
						
						@Override
						public void input(ControlSet controlSet, Input input) {
							super.input(controlSet, input);
							if(Crud.CREATE.equals(page.getCrud())){
								if(Boolean.FALSE.equals(input.getRequired())){
									FormConfiguration configuration = getFormConfiguration(page.getCrud());
									if(configuration!=null && !configuration.getRequiredFieldNames().isEmpty())
										input.setRequired(configuration.getRequiredFieldNames().contains(input.getField().getName()));
								}	
							}
						}
					});	
				}
			}
				
		}
	}
	
}
