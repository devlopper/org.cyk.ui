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

public class DefaultBusinessEntityFormOnePageAdapter<ENTITY extends AbstractIdentifiable> extends BusinessEntityFormOnePageAdapter<ENTITY> implements Serializable {

	private static final long serialVersionUID = -4255109770974601234L;

	public DefaultBusinessEntityFormOnePageAdapter(Class<ENTITY> entityTypeClass) {
		super(entityTypeClass);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialisationEnded(AbstractBean bean) {
		super.initialisationEnded(bean);
		final AbstractCrudOnePage<AbstractActor> page = (AbstractCrudOnePage<AbstractActor>) bean;
		page.getForm().getControlSetListeners().add(new ControlSetAdapter(){
			
			@Override
			public Boolean build(Field field) {
				if(Crud.CREATE.equals(page.getCrud())){
					FormConfiguration configuration = createFormConfigurationMap.entrySet().iterator().next().getValue();
					return configuration==null||CollectionUtils.isEmpty(configuration.getFieldNames()) ?super.build(field):configuration.getFieldNames().contains(field.getName());
				}else
					return super.build(field);
			}
			
			@Override
			public void input(ControlSet controlSet, Input input) {
				super.input(controlSet, input);
				FormConfiguration configuration = createFormConfigurationMap.entrySet().iterator().next().getValue();
				if(Crud.CREATE.equals(page.getCrud())){
					
				}
				if(Boolean.FALSE.equals(input.getRequired())){
					if(configuration!=null && !configuration.getRequiredFieldNames().isEmpty())
						input.setRequired(configuration.getRequiredFieldNames().contains(input.getField().getName()));
				}
				
			}
		});
	}
		
}
