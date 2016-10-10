package org.cyk.system.test.business;

import java.io.Serializable;

import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.web.primefaces.UserSession;

public class PrimefacesManager extends org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.PrimefacesManager implements Serializable {

	private static final long serialVersionUID = -8716834916609095637L;

	public PrimefacesManager() {
		/*PersonFormConfigurationControlSetAdapter formConfigurationControlSetAdapter = new PersonFormConfigurationControlSetAdapter(Actor.class){
			private static final long serialVersionUID = 1L;
			@Override
			public String[] getFieldNames() {
				return new String[]{ActorEditPage.Form.FIELD_REGISTRATION_DATE};
			}
		};
		configurePersonFormConfiguration(Actor.class,formConfigurationControlSetAdapter);
		
		//PersonDetailsControlSetAdapter detailsControlSetAdapter = new PersonDetailsControlSetAdapter(fieldNames);
		
		registerDetailsConfiguration(ActorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override 
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new PersonDetailsControlSetAdapter(Actor.class){
					private static final long serialVersionUID = 1L;

					@Override
					public String[] getFieldNames() {
						return new String[]{ActorEditPage.Form.FIELD_REGISTRATION_DATE};
					}
				};
			}
		});*/
	}
	
	@Override
	public SystemMenu getSystemMenu(UserSession userSession) {
		return SystemMenuBuilder.getInstance().build(userSession);
	}
	/*
	@Override
	protected Boolean isAutoConfigureClass(Class<?> actorClass) {
		return !actorClass.equals(Actor.class);
	}*/
}
