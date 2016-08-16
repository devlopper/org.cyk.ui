package org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.event.EventDetails;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.geography.CountryDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.party.person.AbstractActorDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedEditPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyEditPage;
import org.cyk.ui.web.primefaces.page.file.FileEditPage;
import org.cyk.ui.web.primefaces.page.geography.ContactCollectionEditPage;
import org.cyk.ui.web.primefaces.page.geography.CountryEditPage;
import org.cyk.ui.web.primefaces.page.geography.PhoneNumberEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;

public class PrimefacesManager extends AbstractPrimefacesManager.AbstractPrimefacesManagerListener.Adapter implements Serializable {

	private static final long serialVersionUID = -8716834916609095637L;
	
	@Override
	public SystemMenu getSystemMenu(UserSession userSession) {
		return SystemMenuBuilder.getInstance().build(userSession);
	}
	
	/**/
	
	@Override
	protected void configureEventModule() {
		super.configureEventModule();
		getFormConfiguration(Event.class, Crud.CREATE)
			.addRequiredFieldNames(EventEditPage.Form.FIELD_NAME,EventEditPage.Form.FIELD_EXISTENCE_PERIOD,PeriodFormModel.FIELD_FROM_DATE,PeriodFormModel.FIELD_TO_DATE);
		
		registerDetailsConfiguration(EventDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,EventDetails.FIELD_NAME,EventDetails.FIELD_PERIOD,PeriodDetails.FIELD_FROM_DATE,PeriodDetails.FIELD_TO_DATE);
					}
				};
			}
		});
	
		getFormConfiguration(EventMissed.class,Crud.CREATE).addRequiredFieldNames(EventMissedEditPage.Form.FIELD_EVENT_PARTY
				,EventMissedEditPage.Form.FIELD_MISSED_EVENT_REASON,EventMissedEditPage.Form.FIELD_NUMBER_OF_MILLISECOND);
		
		getFormConfiguration(EventParty.class,Crud.CREATE).addRequiredFieldNames(EventPartyEditPage.Form.FIELD_EVENT,EventPartyEditPage.Form.FIELD_PARTY);
	}
	
	@Override
	protected void configureFileModule() {
		super.configureFileModule();
		getFormConfiguration(File.class,Crud.CREATE).addFieldNames(FileEditPage.Form.FIELD_CODE)
			.addRequiredFieldNames(FileEditPage.Form.FIELD_NAME,FileEditPage.Form.FIELD_FILE);
		
		getFormConfiguration(File.class,Crud.UPDATE).addFieldNames(FileEditPage.Form.FIELD_CODE,FileEditPage.Form.FIELD_NAME,FileEditPage.Form.FIELD_EXTENSION
				,FileEditPage.Form.FIELD_MIME,FileEditPage.Form.FIELD_UNIFORM_RESOURCE_LOCATOR,FileEditPage.Form.FIELD_DESCRIPTION)
		.addRequiredFieldNames(FileEditPage.Form.FIELD_FILE);
		
		getFormConfiguration(File.class,Crud.DELETE).addFieldNames(FileEditPage.Form.FIELD_CODE,FileEditPage.Form.FIELD_NAME);
		
		registerDetailsConfiguration(FileDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,AbstractOutputDetails.getFieldNames(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
					}
				};
			}
		});
		registerDetailsConfiguration(FileIdentifiableGlobalIdentifierDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,FileIdentifiableGlobalIdentifierDetails.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER,FileIdentifiableGlobalIdentifierDetails.FIELD_FILE);
					}
				};
			}
		});
	}

	@Override
	protected void configureGeographyModule() {
		super.configureGeographyModule();
		getFormConfiguration(PhoneNumber.class,Crud.CREATE).addRequiredFieldNames(PhoneNumberEditPage.Form.FIELD_COLLECTION,PhoneNumberEditPage.Form.FIELD_VALUE)
			.addFieldNames(PhoneNumberEditPage.Form.FIELD_ORDER_NUMBER);
		
		getFormConfiguration(ContactCollection.class,Crud.CREATE).addRequiredFieldNames(ContactCollectionEditPage.Form.FIELD_CODE,ContactCollectionEditPage.Form.FIELD_NAME);
		
		getFormConfiguration(Country.class,Crud.CREATE).addRequiredFieldNames(CountryEditPage.Form.FIELD_CONTINENT,CountryEditPage.Form.FIELD_CODE
				,CountryEditPage.Form.FIELD_NAME,CountryEditPage.Form.FIELD_PHONE_NUMBER_CODE);
		
		registerDetailsConfiguration(CountryDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,CountryDetails.FIELD_NAME,CountryDetails.FIELD_PHONE_NUMBER_CODE);
					}
				};
			}
		});
	}
	
	@Override
	protected void configureMathematicsModule() {
		super.configureMathematicsModule();
		getFormConfiguration(MovementCollection.class,Crud.CREATE)
			.addRequiredFieldNames(MovementCollectionEditPage.Form.FIELD_CODE,MovementCollectionEditPage.Form.FIELD_NAME
					,MovementCollectionEditPage.Form.FIELD_ITEM_CODE_SEPARATOR,MovementCollectionEditPage.Form.FIELD_VALUE);
		
		getFormConfiguration(Movement.class,Crud.CREATE)
			.addRequiredFieldNames(MovementEditPage.Form.FIELD_COLLECTION,/*Form.FIELD_ACTION,*/MovementEditPage.Form.FIELD_VALUE);
		//configuration.addFieldNames(Form.FIELD_CURRENT_TOTAL,Form.FIELD_NEXT_TOTAL);
		
		registerDetailsConfiguration(MovementCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,MovementCollectionDetails.FIELD_CODE,MovementCollectionDetails.FIELD_NAME);
					}
				};
			}
		});
		
		registerDetailsConfiguration(MovementDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Field field) {
						return isFieldNameIn(field,MovementDetails.FIELD_COLLECTION,MovementDetails.FIELD_VALUE,MovementDetails.FIELD_EXISTENCE_PERIOD
								,PeriodDetails.FIELD_FROM_DATE);
					}
				};
			}
		});
	}
	
	@Override
	protected void configurePartyModule() {
		getFormConfiguration(Person.class, Crud.CREATE)
			.addRequiredFieldNames(PersonEditPage.Form.FIELD_NAME)
			.addFieldNames(PersonEditPage.Form.FIELD_LAST_NAMES);
		/*
		getFormConfiguration(Person.class, Crud.READ)
			.addFieldNames(PersonEditPage.Form.FIELD_NAME,PersonEditPage.Form.FIELD_LAST_NAMES);
		*/
		getFormConfiguration(Person.class, Crud.UPDATE)
			.addRequiredFieldNames(Boolean.FALSE,getFormConfiguration(Person.class, Crud.CREATE))
			.addExcludedFieldNames(PersonEditPage.Form.FIELD_CONTACT_COLLECTION);
		
		super.configurePartyModule();
		registerDetailsConfiguration(PersonDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new PersonDetailsControlSetAdapter();
			}
		});
		registerDetailsConfiguration(AbstractActorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter getFormControlSetAdapter(Class clazz) {
				return new PersonDetailsControlSetAdapter();
			}
		});
	}
	
	/**/
	
	public static class PersonDetailsControlSetAdapter extends DetailsConfiguration.DefaultControlSetAdapter implements Serializable{
		private static final long serialVersionUID = 1L;
		@Override
		public Boolean build(Field field) {
			return isFieldNameNotIn(field,PersonDetails.FIELD_CONTACT_COLLECTION);
		}
	}
}
