package org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.ApplicationBusiness;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.business.impl.event.EventDetails;
import org.cyk.system.root.business.impl.file.FileDetails;
import org.cyk.system.root.business.impl.file.FileIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.file.ScriptDetails;
import org.cyk.system.root.business.impl.geography.ContactCollectionDetails;
import org.cyk.system.root.business.impl.geography.CountryDetails;
import org.cyk.system.root.business.impl.geography.LocalityDetails;
import org.cyk.system.root.business.impl.language.LanguageDetails;
import org.cyk.system.root.business.impl.mathematics.IntervalCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.IntervalDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.mathematics.MetricDetails;
import org.cyk.system.root.business.impl.mathematics.MetricValueDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsAllergyDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsMedicationDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.business.impl.security.RoleDetails;
import org.cyk.system.root.business.impl.security.UniformResourceLocatorDetails;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.geography.Country;
import org.cyk.system.root.model.geography.Locality;
import org.cyk.system.root.model.geography.PhoneNumber;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.IntervalCollection;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.network.UniformResourceLocator;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.api.model.mathematics.IntervalExtremityFormModel;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;
import org.cyk.ui.api.model.security.CredentialsFormModel;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.Table.ColumnAdapter;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.ActorDetailsConfiguration.FormControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedEditPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyEditPage;
import org.cyk.ui.web.primefaces.page.file.FileEditPage;
import org.cyk.ui.web.primefaces.page.file.FileRepresentationTypeEditPage;
import org.cyk.ui.web.primefaces.page.file.ReportTemplateEditPage;
import org.cyk.ui.web.primefaces.page.file.ScriptEditPage;
import org.cyk.ui.web.primefaces.page.geography.ContactCollectionEditPage;
import org.cyk.ui.web.primefaces.page.geography.CountryEditPage;
import org.cyk.ui.web.primefaces.page.geography.LocalityEditPage;
import org.cyk.ui.web.primefaces.page.geography.PhoneNumberEditPage;
import org.cyk.ui.web.primefaces.page.language.LanguageEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.IntervalCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.IntervalEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsAllergyEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsMedicationEditPage;
import org.cyk.ui.web.primefaces.page.security.RoleEditPage;
import org.cyk.ui.web.primefaces.page.security.UniformResourceLocatorEditPage;
import org.cyk.ui.web.primefaces.page.security.UserAccountEditPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

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
			.addRequiredFieldNames(EventEditPage.Form.FIELD_CODE,EventEditPage.Form.FIELD_NAME,EventEditPage.Form.FIELD_EXISTENCE_PERIOD,PeriodFormModel.FIELD_FROM_DATE,PeriodFormModel.FIELD_TO_DATE);
		
		registerDetailsConfiguration(EventDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,EventDetails.FIELD_CODE,EventDetails.FIELD_NAME,EventDetails.FIELD_PERIOD,PeriodDetails.FIELD_FROM_DATE,PeriodDetails.FIELD_TO_DATE);
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
		
		getFormConfiguration(FileRepresentationType.class,Crud.CREATE).addRequiredFieldNames(FileRepresentationTypeEditPage.Form.FIELD_CODE)
		.addFieldNames(FileRepresentationTypeEditPage.Form.FIELD_NAME);
		
		registerDetailsConfiguration(FileDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,AbstractOutputDetails.getFieldNames(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE));
					}
				};
			}
		});
		registerDetailsConfiguration(FileIdentifiableGlobalIdentifierDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,FileIdentifiableGlobalIdentifierDetails.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER,FileIdentifiableGlobalIdentifierDetails.FIELD_FILE);
					}
				};
			}
		});
		
		getFormConfiguration(ReportTemplate.class,Crud.CREATE).addRequiredFieldNames(ReportTemplateEditPage.Form.FIELD_CODE)
			.addFieldNames(ReportTemplateEditPage.Form.FIELD_NAME
				,ReportTemplateEditPage.Form.FIELD_TEMPLATE,ReportTemplateEditPage.Form.FIELD_HEADER_IMAGE,ReportTemplateEditPage.Form.FIELD_BACKGROUND_IMAGE
				,ReportTemplateEditPage.Form.FIELD_DRAFT_BACKGROUND_IMAGE);
		
		getFormConfiguration(Script.class,Crud.CREATE).addRequiredFieldNames(ScriptEditPage.Form.FIELD_CODE,ScriptEditPage.Form.FIELD_FILE
				,ScriptEditPage.Form.FIELD_EVALUATION_ENGINE)
		.addFieldNames(ScriptEditPage.Form.FIELD_NAME);
		
		registerDetailsConfiguration(ScriptDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ScriptDetails.FIELD_FILE,ScriptDetails.FIELD_EVALUATION_ENGINE,ScriptDetails.FIELD_ARGUMENTS,ScriptDetails.FIELD_RETURNED
								,ScriptDetails.FIELD_VARIABLES);
					}
				};
			}
		});
	}

	@Override
	protected void configureGeographyModule() {
		super.configureGeographyModule();
		getFormConfiguration(PhoneNumber.class,Crud.CREATE).addRequiredFieldNames(PhoneNumberEditPage.Form.FIELD_COLLECTION,PhoneNumberEditPage.Form.FIELD_VALUE
				,PhoneNumberEditPage.Form.FIELD_COUNTRY,PhoneNumberEditPage.Form.FIELD_TYPE)
			.addFieldNames(PhoneNumberEditPage.Form.FIELD_ORDER_NUMBER);
		
		getFormConfiguration(Locality.class,Crud.CREATE).addRequiredFieldNames(LocalityEditPage.Form.FIELD_CODE)
				.addFieldNames(LocalityEditPage.Form.FIELD_NAME,LocalityEditPage.Form.FIELD_RESIDENT_NAME,LocalityEditPage.Form.FIELD_TYPE,LocalityEditPage.Form.FIELD_PARENT);
		registerDetailsConfiguration(LocalityDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,LocalityDetails.FIELD_CODE,LocalityDetails.FIELD_NAME
								,LocalityDetails.FIELD_TYPE,LocalityDetails.FIELD_PARENT,LocalityDetails.FIELD_RESIDENT_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(ContactCollection.class,Crud.CREATE).addRequiredFieldNames(ContactCollectionEditPage.Form.FIELD_CODE,ContactCollectionEditPage.Form.FIELD_NAME);
		registerDetailsConfiguration(ContactCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ContactCollectionDetails.FIELD_PHONE_NUMBERS,ContactCollectionDetails.FIELD_ELECTRONIC_MAILS
								,ContactCollectionDetails.FIELD_LOCATIONS,ContactCollectionDetails.FIELD_POSTAL_BOXES);
					}
				};
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable, AbstractOutputDetails<AbstractIdentifiable>> getFormConfigurationAdapter() {
				Class<? extends AbstractIdentifiable> identifiableClass = ContactCollection.class;
				Class<? extends AbstractOutputDetails<? extends AbstractIdentifiable>> outputDetailsClass = ContactCollectionDetails.class;
				return new DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable,AbstractOutputDetails<AbstractIdentifiable>>((Class<AbstractIdentifiable>) identifiableClass,(Class<AbstractOutputDetails<AbstractIdentifiable>>) outputDetailsClass){
					private static final long serialVersionUID = 1L;
					/*@Override
					public Boolean isRendered(AbstractPrimefacesPage page) {
						AbstractIdentifiable identifiable = null;
						if(page instanceof AbstractConsultPage<?>)
							identifiable = ((AbstractConsultPage<AbstractIdentifiable>)page).getIdentifiable();
						AbstractWindow.WindowsInstanceManager.INSTANCE.isShowDetails(ContactCollection.class, identifiable,page);
						return super.isRendered(page);
					}*/
				};
			}
		});
		
		getFormConfiguration(Country.class,Crud.CREATE).addRequiredFieldNames(CountryEditPage.Form.FIELD_CONTINENT,CountryEditPage.Form.FIELD_CODE
				,CountryEditPage.Form.FIELD_NAME,CountryEditPage.Form.FIELD_PHONE_NUMBER_CODE);
		
		registerDetailsConfiguration(CountryDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,CountryDetails.FIELD_NAME,CountryDetails.FIELD_PHONE_NUMBER_CODE);
					}
				};
			}
		});
		
	}
	
	@Override
	protected void configureLanguageModule() {
		super.configureLanguageModule();
		getFormConfiguration(Language.class, Crud.CREATE)
			.addRequiredFieldNames(LanguageEditPage.Form.FIELD_CODE,LanguageEditPage.Form.FIELD_NAME);
		
		registerDetailsConfiguration(LanguageDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,LanguageDetails.FIELD_CODE,LanguageDetails.FIELD_NAME);
					}
				};
			}
		});
	
	}
	
	@Override
	protected void configureMathematicsModule() {
		super.configureMathematicsModule();
		//Movement
		getFormConfiguration(MovementCollection.class,Crud.CREATE)
			.addRequiredFieldNames(MovementCollectionEditPage.Form.FIELD_CODE,MovementCollectionEditPage.Form.FIELD_VALUE)
			.addFieldNames(MovementCollectionEditPage.Form.FIELD_NAME,MovementCollectionEditPage.Form.FIELD_INTERVAL,MovementCollectionEditPage.Form.FIELD_INCREMENT_ACTION
					,MovementCollectionEditPage.Form.FIELD_DECREMENT_ACTION,MovementCollectionEditPage.Form.FIELD_SUPPORT_DOCUMENT_IDENTIFIER);
		
		registerDetailsConfiguration(MovementCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MovementCollectionDetails.FIELD_CODE,MovementCollectionDetails.FIELD_NAME,MovementCollectionDetails.FIELD_VALUE
								,MovementCollectionDetails.FIELD_INTERVAL,MovementCollectionDetails.FIELD_INCREMENT_ACTION,MovementCollectionDetails.FIELD_DECREMENT_ACTION
								,MovementCollectionDetails.FIELD_SUPPORT_DOCUMENT_IDENTIFIER);
					}
				};
			}
		});
		
		getFormConfiguration(Movement.class,Crud.CREATE)
			.addRequiredFieldNames(MovementEditPage.Form.FIELD_COLLECTION,MovementEditPage.Form.FIELD_VALUE)
			.addFieldNames(MovementEditPage.Form.FIELD_ACTION,MovementEditPage.Form.FIELD_CURRENT_TOTAL,MovementEditPage.Form.FIELD_NEXT_TOTAL
					,MovementEditPage.Form.FIELD_EXISTENCE_PERIOD,PeriodFormModel.FIELD_FROM_DATE);
		/*
		getFormConfiguration(Movement.class,Crud.DELETE)
		.addFieldNames(MovementEditPage.Form.FIELD_COLLECTION,MovementEditPage.Form.FIELD_ACTION,MovementEditPage.Form.FIELD_VALUE
				//,MovementEditPage.Form.FIELD_CURRENT_TOTAL,MovementEditPage.Form.FIELD_NEXT_TOTAL
				,MovementEditPage.Form.FIELD_DATE);
		*/
		registerDetailsConfiguration(MovementDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						if(data instanceof MovementDetails)
							return isFieldNameIn(field,MovementDetails.FIELD_COLLECTION,MovementDetails.FIELD_VALUE,MovementDetails.FIELD_EXISTENCE_PERIOD);
						if(data instanceof PeriodDetails)
							return isFieldNameIn(field,PeriodDetails.FIELD_FROM_DATE);
						return Boolean.FALSE;
					}
				};
			}
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,MovementDetails.FIELD_CODE
								,MovementDetails.FIELD_VALUE
								,MovementDetails.FIELD_EXISTENCE_PERIOD,PeriodDetails.FIELD_FROM_DATE
								);
					}
				};
			}
		});
		
		//Interval
		getFormConfiguration(IntervalCollection.class,Crud.CREATE).addFieldNames(IntervalCollectionEditPage.Form.FIELD_CODE,IntervalCollectionEditPage.Form.FIELD_NAME
				,IntervalCollectionEditPage.Form.FIELD_LOWEST_VALUE,IntervalCollectionEditPage.Form.FIELD_HIGHEST_VALUE,IntervalCollectionEditPage.Form.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT);
	
		registerDetailsConfiguration(IntervalCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,IntervalCollectionDetails.FIELD_CODE,IntervalCollectionDetails.FIELD_NAME,IntervalCollectionDetails.FIELD_LOWEST_VALUE
								,IntervalCollectionDetails.FIELD_HIGHEST_VALUE,IntervalCollectionDetails.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT);
					}
				};
			}
		});
		
		getFormConfiguration(Interval.class,Crud.CREATE)
			.addFieldNames(IntervalEditPage.Form.FIELD_COLLECTION,IntervalEditPage.Form.FIELD_LOW,IntervalEditPage.Form.FIELD_HIGH,IntervalExtremityFormModel.FIELD_VALUE
					,IntervalExtremityFormModel.FIELD_EXCLUDED)
					.addControlSetListener(new ControlSetAdapter<Object>(){
						private static final long serialVersionUID = 1L;
						
						@Override
						public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Object data,Field field) {
							if(data instanceof IntervalExtremityFormModel && ((IntervalEditPage.Form)controlSet.getFormData().getData()).getLow() == data ){
								if(IntervalExtremityFormModel.FIELD_VALUE.equals(field.getName()))
									return inject(LanguageBusiness.class).findText("field.low.extremity");
							}
							if(data instanceof IntervalExtremityFormModel && ((IntervalEditPage.Form)controlSet.getFormData().getData()).getHigh() == data ){
								if(IntervalExtremityFormModel.FIELD_VALUE.equals(field.getName()))
									return inject(LanguageBusiness.class).findText("field.high.extremity");
							}
							return super.fiedLabel(controlSet, data,field);
						}	
						
					});
		
		registerDetailsConfiguration(IntervalDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,IntervalDetails.FIELD_COLLECTION,IntervalDetails.FIELD_LOW_EXTREMITY,IntervalDetails.FIELD_HIGH_EXTREMITY);
					}
				};
			}
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,IntervalDetails.FIELD_LOW_EXTREMITY,IntervalDetails.FIELD_HIGH_EXTREMITY);
					}
				};
			}
		});
		
		//Metric
		getFormConfiguration(MetricCollection.class,Crud.CREATE).addFieldNames(MetricCollectionEditPage.Form.FIELD_CODE,MetricCollectionEditPage.Form.FIELD_NAME
				,MetricCollectionEditPage.Form.FIELD_VALUE_INTERVAL_COLLECTION,MetricCollectionEditPage.Form.FIELD_VALUE_TYPE,MetricCollectionEditPage.Form.FIELD_VALUE_INPUTTED);
	
		registerDetailsConfiguration(MetricCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricCollectionDetails.FIELD_CODE,MetricCollectionDetails.FIELD_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(MetricCollectionIdentifiableGlobalIdentifier.class,Crud.CREATE).addFieldNames(MetricCollectionIdentifiableGlobalIdentifierEditPage.Form.FIELD_GLOBAL_IDENTIFIER
				,MetricCollectionIdentifiableGlobalIdentifierEditPage.Form.FIELD_METRIC_COLLECTION);
	
		registerDetailsConfiguration(MetricCollectionIdentifiableGlobalIdentifierDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricCollectionIdentifiableGlobalIdentifierDetails.FIELD_IDENTIFIABLE_GLOBAL_IDENTIFIER
								,MetricCollectionIdentifiableGlobalIdentifierDetails.FIELD_METRIC_COLLECTION);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,MetricCollectionIdentifiableGlobalIdentifierDetails.FIELD_METRIC_COLLECTION);
					}
				};
			}
		});
		
		getFormConfiguration(Metric.class,Crud.CREATE)
			.addFieldNames(MetricEditPage.Form.FIELD_COLLECTION,MetricEditPage.Form.FIELD_CODE,MetricEditPage.Form.FIELD_NAME);
		
		registerDetailsConfiguration(MetricDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricDetails.FIELD_COLLECTION,MetricDetails.FIELD_CODE,MetricDetails.FIELD_NAME);
					}
				};
			}
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,MetricDetails.FIELD_CODE,MetricDetails.FIELD_NAME);
					}
				};
			}
		});
		/*
		getFormConfiguration(MetricValue.class,Crud.CREATE).addFieldNames(MetricValueEditPage.Form.FIELD_GLOBAL_IDENTIFIER
				,MetricValueEditPage.Form.FIELD_METRIC_COLLECTION);
		*/
		registerDetailsConfiguration(MetricValueDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricValueDetails.FIELD_VALUE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,MetricValueDetails.FIELD_VALUE);
					}
				};
			}
		});
	}
	
	@Override
	protected void configurePartyModule() {
		super.configurePartyModule();
		configurePersonFormConfiguration(Person.class,new PersonDetailsConfiguration.FormControlSetAdapter(Person.class));
		
		registerDetailsConfiguration(PersonDetails.class, new PersonDetailsConfiguration());
		
		registerDetailsConfiguration(JobDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,JobDetails.FIELD_FUNCTION,JobDetails.FIELD_TITLE
								,JobDetails.FIELD_CONTACTS,JobDetails.FIELD_COMPANY);
					}
				};
			}
		});
		
		registerDetailsConfiguration(SignatureDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,SignatureDetails.FIELD_SPECIMEN);
					}
				};
			}
		});
		
		registerDetailsConfiguration(MedicalDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MedicalDetails.FIELD_BLOOD_GROUP,MedicalDetails.FIELD_OTHER_MEDICAL_INFORMATIONS);
					}
				};
			}
		});
		
		getFormConfiguration(MedicalInformationsAllergy.class, Crud.CREATE).addRequiredFieldNames(MedicalInformationsAllergyEditPage.Form.FIELD_ALLERGY)
				.addFieldNames(MedicalInformationsAllergyEditPage.Form.FIELD_REACTION_RESPONSE,MedicalInformationsAllergyEditPage.Form.FIELD_REACTION_TYPE);
		
		registerDetailsConfiguration(MedicalInformationsAllergyDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MedicalInformationsAllergyDetails.FIELD_ALLERGY,MedicalInformationsAllergyDetails.FIELD_REACTION_RESPONSE
								,MedicalInformationsAllergyDetails.FIELD_REACTION_TYPE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field, MedicalInformationsAllergyDetails.FIELD_ALLERGY,MedicalInformationsAllergyDetails.FIELD_REACTION_RESPONSE
								,MedicalInformationsAllergyDetails.FIELD_REACTION_TYPE);
					}
				};
			}
		});
		
		getFormConfiguration(MedicalInformationsMedication.class, Crud.CREATE).addRequiredFieldNames(MedicalInformationsMedicationEditPage.Form.FIELD_MEDICATION)
		.addFieldNames(MedicalInformationsMedicationEditPage.Form.FIELD_MUST_BE_AVAILABLE,MedicalInformationsMedicationEditPage.Form.FIELD_MUST_BE_GIVEN);
		
		registerDetailsConfiguration(MedicalInformationsMedicationDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MedicalInformationsMedicationDetails.FIELD_MEDICATION,MedicalInformationsMedicationDetails.FIELD_MUST_BE_AVAILABLE
								,MedicalInformationsMedicationDetails.FIELD_MUST_BE_GIVEN);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field, MedicalInformationsMedicationDetails.FIELD_MEDICATION,MedicalInformationsMedicationDetails.FIELD_MUST_BE_AVAILABLE
								,MedicalInformationsMedicationDetails.FIELD_MUST_BE_GIVEN);
					}
				};
			}
		});
		
		registerDetailsConfiguration(PersonRelationshipDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,PersonRelationshipDetails.FIELD_PERSON1,PersonRelationshipDetails.FIELD_PERSON2
								,PersonRelationshipDetails.FIELD_TYPE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field, PersonRelationshipDetails.FIELD_PERSON1,PersonRelationshipDetails.FIELD_TYPE);
					}
				};
			}
		});
		
		for(BusinessEntityInfos businessEntityInfos : inject(ApplicationBusiness.class).findBusinessEntitiesInfos()){
			final Class<?> identifiableClass = businessEntityInfos.getClazz();
			if(AbstractActor.class.isAssignableFrom(identifiableClass) && Boolean.TRUE.equals(isAutoConfigureClass(identifiableClass))){
				configureActorFormConfiguration(identifiableClass, (FormControlSetAdapter) getFormControlSetAdapter(identifiableClass));				
				registerDetailsConfiguration(businessEntityInfos.getUserInterface().getDetailsClass(), getDetailsConfiguration(identifiableClass));
			}
		}
		/*
		registerDetailsConfiguration(AbstractActorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new ActorDetailsControlSetAdapter();
			}
		});*/
	}
	
	@Override
	protected void configureSecurityModule() {
		super.configureSecurityModule();
		getFormConfiguration(UniformResourceLocator.class, Crud.CREATE)
		.addRequiredFieldNames(UniformResourceLocatorEditPage.Form.FIELD_CODE,UniformResourceLocatorEditPage.Form.FIELD_NAME
				,UniformResourceLocatorEditPage.Form.FIELD_ADDRESS);
	
		registerDetailsConfiguration(UniformResourceLocatorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,UniformResourceLocatorDetails.FIELD_CODE,UniformResourceLocatorDetails.FIELD_NAME
								,UniformResourceLocatorDetails.FIELD_ADDRESS);
					}
				};
			}
		});
		
		getFormConfiguration(Role.class, Crud.CREATE)
		.addRequiredFieldNames(RoleEditPage.Form.FIELD_CODE,RoleEditPage.Form.FIELD_NAME,RoleEditPage.Form.FIELD_UNIFORM_RESOURCE_LOCATOR);
	
		registerDetailsConfiguration(RoleDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,RoleDetails.FIELD_CODE,RoleDetails.FIELD_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(UserAccount.class, Crud.CREATE)
		.addRequiredFieldNames(UserAccountEditPage.Form.FIELD_USER,UserAccountEditPage.Form.FIELD_ROLES,UserAccountEditPage.Form.FIELD_CREDENTIALS
				,CredentialsFormModel.FIELD_USERNAME,CredentialsFormModel.FIELD_PASSWORD,CredentialsFormModel.FIELD_PASSWORD_CONFIRMATION);
	
		registerDetailsConfiguration(UserAccountDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,UserAccountDetails.FIELD_USER,UserAccountDetails.FIELD_USERNAME,UserAccountDetails.FIELD_DISABLED
								,UserAccountDetails.FIELD_CURRENT_LOCK);
					}
				};
			}
		});
	}
	
	protected Boolean isAutoConfigureClass(Class<?> aClass){
		return Boolean.TRUE;
	}
	
	protected ControlSetAdapter.Form<Object> getFormControlSetAdapter(Class<?> aClass){
		if(AbstractActor.class.isAssignableFrom(aClass))
			return new ActorDetailsConfiguration.FormControlSetAdapter(aClass);
		return null;
	}
	
	protected DetailsConfiguration getDetailsConfiguration(Class<?> aClass){
		if(AbstractActor.class.isAssignableFrom(aClass))
			return new ActorDetailsConfiguration();
		return null;
	}
	
	protected void configurePersonFormConfiguration(Class<?> entityClass,PersonDetailsConfiguration.FormControlSetAdapter formConfigurationControlSetAdapter){
		getFormConfiguration(entityClass, Crud.CREATE).addRequiredFieldNames(ArrayUtils.addAll(formConfigurationControlSetAdapter.getRequiredFieldNames(),AbstractPersonEditFormModel.FIELD_CODE
				,AbstractPersonEditFormModel.FIELD_NAME))
				.addFieldNames(ArrayUtils.addAll(formConfigurationControlSetAdapter.getFieldNames(),AbstractPersonEditFormModel.FIELD_LAST_NAMES,AbstractPersonEditFormModel.FIELD_IMAGE
				,AbstractPersonEditFormModel.FIELD_BIRTH_DATE,AbstractPersonEditFormModel.FIELD_BIRTH_LOCATION,LocationFormModel.FIELD_LOCALITY,LocationFormModel.FIELD_OTHER_DETAILS
				,AbstractPersonEditFormModel.FIELD_SEX,AbstractPersonEditFormModel.FIELD_TITLE,AbstractPersonEditFormModel.FIELD_NATIONALITY,AbstractPersonEditFormModel.FIELD_LANGUAGE_COLLECTION
				,LanguageCollectionFormModel.FIELD_LANGUAGE_1,AbstractPersonEditFormModel.FIELD_OTHER_DETAILS,AbstractPersonEditFormModel.FIELD_CONTACT_COLLECTION))
				.addControlSetListener(formConfigurationControlSetAdapter);
		
		getUpdateFormConfiguration(entityClass, ContactCollection.class)
				.addFieldNames(AbstractPersonEditFormModel.FIELD_CONTACT_COLLECTION,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER
						,ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_ELECTRONICMAIL1,ContactCollectionFormModel.FIELD_ELECTRONICMAIL2
						,ContactCollectionFormModel.FIELD_POSTALBOX);
		
		getUpdateFormConfiguration(entityClass, JobDetails.class)
			.addFieldNames(AbstractPersonEditFormModel.FIELD_JOB_COMPANY,AbstractPersonEditFormModel.FIELD_JOB_TITLE,AbstractPersonEditFormModel.FIELD_JOB_FUNCTION
				,AbstractPersonEditFormModel.FIELD_JOB_CONTACT_COLLECTION,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER
				,ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_ELECTRONICMAIL1,ContactCollectionFormModel.FIELD_ELECTRONICMAIL2
				,ContactCollectionFormModel.FIELD_POSTALBOX);
		
		getUpdateFormConfiguration(entityClass, SignatureDetails.class).addFieldNames(AbstractPersonEditFormModel.FIELD_SIGNATURE_SPECIMEN);
		
		getUpdateFormConfiguration(entityClass, MedicalDetails.class).addFieldNames(AbstractPersonEditFormModel.FIELD_BLOOD_GROUP
				,AbstractPersonEditFormModel.FIELD_OTHER_MEDICAL_INFORMATIONS);
		
		getFormConfiguration(entityClass, Crud.READ).addFieldNames(AbstractPersonEditFormModel.FIELD_CODE,AbstractPersonEditFormModel.FIELD_NAME
				,AbstractPersonEditFormModel.FIELD_LAST_NAMES);
		/*
		getFormConfiguration(entityClass, Crud.UPDATE).addRequiredFieldNames(Boolean.FALSE,getFormConfiguration(entityClass, Crud.CREATE).getRequiredFieldNames())
			.addFieldNames(getFormConfiguration(entityClass, Crud.CREATE).getFieldNames()).addControlSetListeners(getFormConfiguration(entityClass, Crud.CREATE)
					.getControlSetListeners());
		*/
		
		//getFormConfiguration(entityClass, Crud.DELETE).addFieldNames(getFormConfiguration(entityClass, Crud.READ).getFieldNames());
	}
	
	protected void configureActorFormConfiguration(final Class<?> entityClass,ActorDetailsConfiguration.FormControlSetAdapter formConfigurationControlSetAdapter){
		configurePersonFormConfiguration(entityClass,formConfigurationControlSetAdapter);
	}
	
	/**/

}
