package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.data.collector.control.Input;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.party.AbstractPartyFormModel;
import org.cyk.ui.api.model.party.PersonFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.DefaultBusinessEntityFormOnePageAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.cdi.AbstractBean;

public class ActorCrudOnePageListener<ACTOR extends AbstractActor> extends DefaultBusinessEntityFormOnePageAdapter<ACTOR> implements Serializable {

	private static final long serialVersionUID = 4370361826462886031L;

	public ActorCrudOnePageListener(Class<ACTOR> entityTypeClass,CreateMode createMode) {
		super(entityTypeClass, createMode);
		onCreateFields.add(AbstractPartyFormModel.FIELD_NAME);
		onCreateFields.add(PersonFormModel.FIELD_LAST_NAME);
		//onCreateFields.add(ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER);
		
		requiredFields.add(PersonFormModel.FIELD_LAST_NAME);
	}

	public ActorCrudOnePageListener(Class<ACTOR> entityTypeClass) {
		this(entityTypeClass,CreateMode.FAST);
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
					return onCreateFields.isEmpty()?super.build(field):onCreateFields.contains(field.getName());
				}else
					return super.build(field);
			}
			
			@Override
			public void input(ControlSet controlSet, Input input) {
				super.input(controlSet, input);
				if(Crud.CREATE.equals(page.getCrud())){
					if(CreateMode.FAST.equals(createMode)){
						
					}
				}
				if(Boolean.FALSE.equals(input.getRequired())){
					if(!requiredFields.isEmpty())
						input.setRequired(requiredFields.contains(input.getField().getName()));
				}
				
			}
		});
	}
		
}
