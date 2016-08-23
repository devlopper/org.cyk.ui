package org.cyk.system.test.business;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.language.LanguageCollectionDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.test.model.actor.Actor;
import org.cyk.system.test.model.actor.ActorDetails;
import org.cyk.system.test.model.actor.ActorEditPage;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;

public class PrimefacesManager extends org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.PrimefacesManager implements Serializable {

	private static final long serialVersionUID = -8716834916609095637L;
	
	public PrimefacesManager() {
		getFormConfiguration(Person.class, Crud.CREATE).addRequiredFieldNames(PersonEditPage.Form.FIELD_CODE)
		.addFieldNames(PersonEditPage.Form.FIELD_IMAGE,PersonEditPage.Form.FIELD_NAME,PersonEditPage.Form.FIELD_LAST_NAMES
				,PersonEditPage.Form.FIELD_BIRTH_DATE,PersonEditPage.Form.FIELD_BIRTH_LOCATION,LocationFormModel.FIELD_LOCALITY,LocationFormModel.FIELD_OTHER_DETAILS
				,PersonEditPage.Form.FIELD_NATIONALITY,PersonEditPage.Form.FIELD_SEX,PersonEditPage.Form.FIELD_BLOOD_GROUP,PersonEditPage.Form.FIELD_LANGUAGE_COLLECTION
				,LanguageCollectionFormModel.FIELD_LANGUAGE_1);
		
		registerDetailsConfiguration(PersonDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,PersonDetails.FIELD_CODE,PersonDetails.FIELD_IMAGE,PersonDetails.FIELD_NAME,PersonDetails.FIELD_LASTNAMES
								,PersonDetails.FIELD_BIRTH_DATE,PersonDetails.FIELD_BIRTH_LOCATION,PersonDetails.FIELD_NATIONALITY,PersonDetails.FIELD_SEX
								,PersonDetails.FIELD_BLOOD_GROUP,PersonDetails.FIELD_LANGUAGE_COLLECTION
								,LanguageCollectionDetails.FIELD_LANGUAGES);
					}
				};
			}
		});
		
		getFormConfiguration(Actor.class, Crud.CREATE).addRequiredFieldNames(ActorEditPage.Form.FIELD_CODE)
		.addFieldNames(ActorEditPage.Form.FIELD_IMAGE,ActorEditPage.Form.FIELD_NAME,ActorEditPage.Form.FIELD_LAST_NAMES
				,ActorEditPage.Form.FIELD_BIRTH_DATE,ActorEditPage.Form.FIELD_BIRTH_LOCATION,ActorEditPage.Form.FIELD_NATIONALITY,ActorEditPage.Form.FIELD_SEX
				,ActorEditPage.Form.FIELD_BLOOD_GROUP,ActorEditPage.Form.FIELD_LANGUAGE_COLLECTION,ActorEditPage.Form.FIELD_REGISTRATION_DATE
				,LanguageCollectionFormModel.FIELD_LANGUAGE_1);
		
		registerDetailsConfiguration(ActorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return (data instanceof ActorDetails) && isFieldNameIn(field,ActorDetails.FIELD_CODE,ActorDetails.FIELD_IMAGE,ActorDetails.FIELD_NAME,ActorDetails.FIELD_LASTNAMES
								,ActorDetails.FIELD_BIRTH_DATE,ActorDetails.FIELD_BIRTH_LOCATION,ActorDetails.FIELD_NATIONALITY,ActorDetails.FIELD_SEX
								,ActorDetails.FIELD_BLOOD_GROUP,ActorDetails.FIELD_LANGUAGE_COLLECTION,ActorDetails.FIELD_REGISTRATION_DATE
								,LanguageCollectionDetails.FIELD_LANGUAGES);
					}
				};
			}
		});
	}
	
	@Override
	public SystemMenu getSystemMenu(UserSession userSession) {
		return SystemMenuBuilder.getInstance().build(userSession);
	}
	
	@Override
	protected Boolean isAutoConfigureClass(Class<?> actorClass) {
		return !actorClass.equals(Actor.class);
	}
}
