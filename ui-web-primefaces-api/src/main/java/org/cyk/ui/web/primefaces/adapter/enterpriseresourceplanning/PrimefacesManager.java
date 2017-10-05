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
import org.cyk.system.root.business.impl.language.LanguageDetails;
import org.cyk.system.root.business.impl.mathematics.IntervalDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionIdentifiableGlobalIdentifierDetails;
import org.cyk.system.root.business.impl.mathematics.MetricCollectionTypeDetails;
import org.cyk.system.root.business.impl.mathematics.MetricDetails;
import org.cyk.system.root.business.impl.mathematics.MetricValueDetails;
import org.cyk.system.root.business.impl.mathematics.MovementActionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementCollectionDetails;
import org.cyk.system.root.business.impl.mathematics.MovementDetails;
import org.cyk.system.root.business.impl.message.SmtpPropertiesDetails;
import org.cyk.system.root.business.impl.network.ComputerDetails;
import org.cyk.system.root.business.impl.network.ServiceDetails;
import org.cyk.system.root.business.impl.party.ApplicationDetails;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.MedicalDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsAllergyDetails;
import org.cyk.system.root.business.impl.party.person.MedicalInformationsMedicationDetails;
import org.cyk.system.root.business.impl.party.person.PersonDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipExtremityDetails;
import org.cyk.system.root.business.impl.party.person.PersonRelationshipTypeRoleDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeDetails;
import org.cyk.system.root.business.impl.pattern.tree.DataTreeTypeDetails;
import org.cyk.system.root.business.impl.security.CredentialsDetails;
import org.cyk.system.root.business.impl.security.LicenseDetails;
import org.cyk.system.root.business.impl.security.RoleDetails;
import org.cyk.system.root.business.impl.security.RoleUniformResourceLocatorDetails;
import org.cyk.system.root.business.impl.security.SoftwareDetails;
import org.cyk.system.root.business.impl.security.UserAccountDetails;
import org.cyk.system.root.business.impl.time.PeriodDetails;
import org.cyk.system.root.business.impl.value.MeasureDetails;
import org.cyk.system.root.business.impl.value.ValueCollectionDetails;
import org.cyk.system.root.business.impl.value.ValueCollectionItemDetails;
import org.cyk.system.root.business.impl.value.ValueDetails;
import org.cyk.system.root.business.impl.value.ValuePropertiesDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.file.FileRepresentationType;
import org.cyk.system.root.model.file.Script;
import org.cyk.system.root.model.file.report.ReportTemplate;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.system.root.model.language.Language;
import org.cyk.system.root.model.mathematics.Interval;
import org.cyk.system.root.model.mathematics.Metric;
import org.cyk.system.root.model.mathematics.MetricCollection;
import org.cyk.system.root.model.mathematics.MetricCollectionIdentifiableGlobalIdentifier;
import org.cyk.system.root.model.mathematics.MetricCollectionType;
import org.cyk.system.root.model.mathematics.MetricValue;
import org.cyk.system.root.model.mathematics.Movement;
import org.cyk.system.root.model.mathematics.MovementAction;
import org.cyk.system.root.model.mathematics.MovementCollection;
import org.cyk.system.root.model.message.SmtpProperties;
import org.cyk.system.root.model.network.Computer;
import org.cyk.system.root.model.network.Service;
import org.cyk.system.root.model.party.Application;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationshipTypeRole;
import org.cyk.system.root.model.pattern.tree.DataTree;
import org.cyk.system.root.model.pattern.tree.DataTreeType;
import org.cyk.system.root.model.security.Credentials;
import org.cyk.system.root.model.security.License;
import org.cyk.system.root.model.security.Role;
import org.cyk.system.root.model.security.Software;
import org.cyk.system.root.model.security.UserAccount;
import org.cyk.system.root.model.value.Measure;
import org.cyk.system.root.model.value.Value;
import org.cyk.system.root.model.value.ValueCollection;
import org.cyk.system.root.model.value.ValueProperties;
import org.cyk.ui.api.command.menu.SystemMenu;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.language.LanguageCollectionFormModel;
import org.cyk.ui.api.model.mathematics.IntervalExtremityFormModel;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;
import org.cyk.ui.api.model.security.CredentialsFormModel;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.api.model.value.ValueFormModel;
import org.cyk.ui.web.primefaces.AbstractPrimefacesManager;
import org.cyk.ui.web.primefaces.Table.ColumnAdapter;
import org.cyk.ui.web.primefaces.UserSession;
import org.cyk.ui.web.primefaces.adapter.enterpriseresourceplanning.ActorDetailsConfiguration.FormControlSetAdapter;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.event.EventMissedEditPage;
import org.cyk.ui.web.primefaces.page.event.EventPartyEditPage;
import org.cyk.ui.web.primefaces.page.file.FileEditPage;
import org.cyk.ui.web.primefaces.page.file.FileRepresentationTypeEditPage;
import org.cyk.ui.web.primefaces.page.file.ReportTemplateEditPage;
import org.cyk.ui.web.primefaces.page.file.ScriptEditPage;
import org.cyk.ui.web.primefaces.page.language.LanguageEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.IntervalEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionIdentifiableGlobalIdentifierEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricCollectionTypeEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MetricValueEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementActionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementCollectionEditPage;
import org.cyk.ui.web.primefaces.page.mathematics.MovementEditPage;
import org.cyk.ui.web.primefaces.page.message.mail.SmtpPropertiesEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.DataTreeEditPage;
import org.cyk.ui.web.primefaces.page.nestedset.DataTreeTypeEditPage;
import org.cyk.ui.web.primefaces.page.network.ComputerEditPage;
import org.cyk.ui.web.primefaces.page.network.ServiceEditPage;
import org.cyk.ui.web.primefaces.page.party.ApplicationEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsAllergyEditPage;
import org.cyk.ui.web.primefaces.page.party.MedicalInformationsMedicationEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonRelationshipTypeRoleEditPage;
import org.cyk.ui.web.primefaces.page.security.CredentialsEditPage;
import org.cyk.ui.web.primefaces.page.security.LicenseEditPage;
import org.cyk.ui.web.primefaces.page.security.RoleEditPage;
import org.cyk.ui.web.primefaces.page.security.SoftwareEditPage;
import org.cyk.ui.web.primefaces.page.security.UserAccountEditPage;
import org.cyk.ui.web.primefaces.page.value.MeasureEditPage;
import org.cyk.ui.web.primefaces.page.value.ValueCollectionEditPage;
import org.cyk.ui.web.primefaces.page.value.ValueEditPage;
import org.cyk.ui.web.primefaces.page.value.ValuePropertiesEditPage;
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
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,FileIdentifiableGlobalIdentifierDetails.FIELD_CODE
								,FileIdentifiableGlobalIdentifierDetails.FIELD_NAME
								,FileIdentifiableGlobalIdentifierDetails.FIELD_FILE
								);
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
		getFormConfiguration(MovementAction.class,Crud.CREATE)
		.addRequiredFieldNames(MovementActionEditPage.Form.FIELD_CODE)
		.addFieldNames(MovementActionEditPage.Form.FIELD_NAME,MovementActionEditPage.Form.FIELD_INTERVAL);
	
		registerDetailsConfiguration(MovementActionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MovementActionDetails.FIELD_CODE,MovementActionDetails.FIELD_NAME,MovementActionDetails.FIELD_INTERVAL);
					}
				};
			}
		});
		
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
		/*
		getFormConfiguration(IntervalCollection.class,Crud.CREATE).addRequiredFieldNames(IntervalCollectionEditPage.Form.FIELD_CODE).addFieldNames(IntervalCollectionEditPage.Form.FIELD_NAME
				,IntervalCollectionEditPage.Form.FIELD_NUMBER_OF_DECIMAL_AFTER_DOT);
	
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
		*/
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
				,MetricCollectionEditPage.Form.FIELD_TYPE,MetricCollectionEditPage.Form.FIELD_VALUE_PROPERTIES,MetricCollectionEditPage.Form.FIELD_VALUE);
	
		registerDetailsConfiguration(MetricCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricCollectionDetails.FIELD_CODE,MetricCollectionDetails.FIELD_NAME,MetricCollectionDetails.FIELD_TYPE
								,MetricCollectionDetails.FIELD_VALUE_PROPERTIES,MetricCollectionDetails.FIELD_VALUE);
					}
				};
			}
		});
		
		getFormConfiguration(MetricCollectionType.class,Crud.CREATE).addFieldNames(MetricCollectionTypeEditPage.Form.FIELD_CODE,MetricCollectionTypeEditPage.Form.FIELD_NAME
				,MetricCollectionTypeEditPage.Form.FIELD_COLLECTION_VALUE_PROPERTIES,MetricCollectionTypeEditPage.Form.FIELD_METRIC_VALUE_PROPERTIES);
	
		registerDetailsConfiguration(MetricCollectionTypeDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricCollectionTypeDetails.FIELD_CODE,MetricCollectionTypeDetails.FIELD_NAME
								,MetricCollectionTypeDetails.FIELD_COLLECTION_VALUE_PROPERTIES,MetricCollectionTypeDetails.FIELD_METRIC_VALUE_PROPERTIES);
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
			.addFieldNames(MetricEditPage.Form.FIELD_COLLECTION,MetricEditPage.Form.FIELD_CODE,MetricEditPage.Form.FIELD_NAME,MetricEditPage.Form.FIELD_VALUE_PROPERTIES);
		
		registerDetailsConfiguration(MetricDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricDetails.FIELD_COLLECTION,MetricDetails.FIELD_CODE,MetricDetails.FIELD_NAME,MetricDetails.FIELD_VALUE_PROPERTIES);
					}
				};
			}
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,MetricDetails.FIELD_CODE,MetricDetails.FIELD_NAME,MetricDetails.FIELD_VALUE_PROPERTIES);
					}
				};
			}
		});
		
		getFormConfiguration(MetricValue.class,Crud.CREATE).addFieldNames(MetricValueEditPage.Form.FIELD_CODE
				,MetricValueEditPage.Form.FIELD_NAME,MetricValueEditPage.Form.FIELD_METRIC,MetricValueEditPage.Form.FIELD_VALUE);
		
		registerDetailsConfiguration(MetricValueDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L; @SuppressWarnings("rawtypes") @Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MetricValueDetails.FIELD_CODE,MetricValueDetails.FIELD_NAME,MetricValueDetails.FIELD_METRIC
								,MetricValueDetails.FIELD_VALUE);
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
			
			@Override
			public org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable, AbstractOutputDetails<AbstractIdentifiable>> getFormConfigurationAdapter() {
				return new org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener.Form.Adapter(Person.class,JobDetails.class){
					@Override
					public String getFormConfigurationIdentifier() {
						return "job";
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
			
			@Override
			public org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener.Form.Adapter<AbstractIdentifiable, AbstractOutputDetails<AbstractIdentifiable>> getFormConfigurationAdapter() {
				return new org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage.DetailsConfigurationListener.Form.Adapter(MedicalInformations.class,MedicalDetails.class){
					@Override
					public String getFormConfigurationIdentifier() {
						return "medical";
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
						return isFieldNameIn(field,PersonRelationshipDetails.FIELD_EXTREMITY1,PersonRelationshipDetails.FIELD_EXTREMITY2
								,PersonRelationshipExtremityDetails.FIELD_PERSON,PersonRelationshipExtremityDetails.FIELD_ROLE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field, PersonRelationshipDetails.FIELD_PERSON_1,PersonRelationshipDetails.FIELD_ROLE_1
								,PersonRelationshipDetails.FIELD_PERSON_2,PersonRelationshipDetails.FIELD_ROLE_2);
					}
					
				};
			}
		});
		
		getFormConfiguration(PersonRelationshipTypeRole.class, Crud.CREATE)
		.addRequiredFieldNames(PersonRelationshipTypeRoleEditPage.Form.FIELD_CODE,PersonRelationshipTypeRoleEditPage.Form.FIELD_NAME
				,PersonRelationshipTypeRoleEditPage.Form.FIELD_PERSON_RELATIONSHIP_TYPE,PersonRelationshipTypeRoleEditPage.Form.FIELD_ROLE);
	
		registerDetailsConfiguration(PersonRelationshipTypeRoleDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,PersonRelationshipTypeRoleDetails.FIELD_CODE,PersonRelationshipTypeRoleDetails.FIELD_NAME
								,PersonRelationshipTypeRoleDetails.FIELD_PERSON_RELATIONSHIP_TYPE,PersonRelationshipTypeRoleDetails.FIELD_ROLE);
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
		
		getFormConfiguration(Application.class, Crud.CREATE).addRequiredFieldNames(ApplicationEditPage.Form.FIELD_NAME)
		.addFieldNames(ApplicationEditPage.Form.FIELD_SMTP_PROPERTIES
				,ApplicationEditPage.Form.FIELD_WEB_CONTEXT,ApplicationEditPage.Form.FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED);
		
		registerDetailsConfiguration(ApplicationDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ApplicationDetails.FIELD_NAME,ApplicationDetails.FIELD_SMTP_PROPERTIES
								,ApplicationDetails.FIELD_WEB_CONTEXT,ApplicationDetails.FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz, AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,ApplicationDetails.FIELD_NAME,ApplicationDetails.FIELD_MENU_PRIMARY,ApplicationDetails.FIELD_MENU_SECONDARY
								,ApplicationDetails.FIELD_SMTP_PROPERTIES,ApplicationDetails.FIELD_WEB_CONTEXT,ApplicationDetails.FIELD_UNIFORM_RESOURCE_LOCATOR_FILTERED);
					}
				};
			}
		});
		
		getFormConfiguration(License.class, Crud.CREATE).addRequiredFieldNames(LicenseEditPage.Form.FIELD_CODE)
		.addFieldNames(LicenseEditPage.Form.FIELD_EXISTENCE_PERIOD,PeriodFormModel.FIELD_FROM_DATE
				,PeriodFormModel.FIELD_TO_DATE);
		
		registerDetailsConfiguration(LicenseDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,LicenseDetails.FIELD_CODE,LicenseDetails.FIELD_EXISTENCE_PERIOD
								,PeriodDetails.FIELD_FROM_DATE,PeriodDetails.FIELD_TO_DATE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz, AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,LicenseDetails.FIELD_CODE,LicenseDetails.FIELD_EXISTENCE_PERIOD
								,PeriodDetails.FIELD_FROM_DATE,PeriodDetails.FIELD_TO_DATE);
					}
				};
			}
		});
		
		getFormConfiguration(Role.class, Crud.CREATE).addRequiredFieldNames(RoleEditPage.Form.FIELD_CODE)
		.addFieldNames(RoleEditPage.Form.FIELD_NAME,RoleEditPage.Form.FIELD_ONE_ITEM_MASTER_SELECTED);
	
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
		
		registerDetailsConfiguration(RoleUniformResourceLocatorDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,RoleUniformResourceLocatorDetails.FIELD_ROLE,RoleUniformResourceLocatorDetails.FIELD_UNIFORM_RESOURCE_LOCATOR);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,RoleUniformResourceLocatorDetails.FIELD_UNIFORM_RESOURCE_LOCATOR);
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
		
		getFormConfiguration(Software.class, Crud.CREATE)
		.addRequiredFieldNames(SoftwareEditPage.Form.FIELD_CODE,SoftwareEditPage.Form.FIELD_NAME);
	
		registerDetailsConfiguration(SoftwareDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,SoftwareDetails.FIELD_CODE,SoftwareDetails.FIELD_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(Credentials.class, Crud.CREATE)
		.addRequiredFieldNames(CredentialsEditPage.Form.FIELD_SOFTWARE,CredentialsEditPage.Form.FIELD_USERNAME,CredentialsEditPage.Form.FIELD_PASSWORD);
	
		registerDetailsConfiguration(CredentialsDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,CredentialsDetails.FIELD_SOFTWARE,CredentialsDetails.FIELD_USERNAME,CredentialsDetails.FIELD_PASSWORD);
					}
				};
			}
		});
	}
	
	@Override
	protected void configureValueModule() {
		super.configureValueModule();
		getFormConfiguration(Value.class, Crud.CREATE)
		.addRequiredFieldNames(ValueEditPage.Form.FIELD_CODE).addFieldNames(ValueEditPage.Form.FIELD_NAME,ValueEditPage.Form.FIELD_PROPERTIES
				,ValueEditPage.Form.FIELD_VALUE,ValueFormModel.FIELD_DATE_VALUE,ValueFormModel.FIELD_BOOLEAN_VALUE
				,ValueFormModel.FIELD_NUMBER_VALUE,ValueFormModel.FIELD_STRING_VALUE);
	
		registerDetailsConfiguration(ValueDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ValueDetails.FIELD_CODE,ValueDetails.FIELD_NAME,ValueDetails.FIELD_PROPERTIES
								,ValueDetails.FIELD_VALUE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,ValueDetails.FIELD_CODE,ValueDetails.FIELD_NAME,ValueDetails.FIELD_CODE,ValueDetails.FIELD_NAME
								,ValueDetails.FIELD_PROPERTIES,ValueDetails.FIELD_VALUE);
					}
				};
			}
		});
		
		getFormConfiguration(ValueProperties.class, Crud.CREATE)
		.addRequiredFieldNames(ValuePropertiesEditPage.Form.FIELD_CODE).addFieldNames(ValuePropertiesEditPage.Form.FIELD_NAME,ValuePropertiesEditPage.Form.FIELD_MEASURE
				,ValuePropertiesEditPage.Form.FIELD_INTERVAL_COLLECTION,ValuePropertiesEditPage.Form.FIELD_TYPE,ValuePropertiesEditPage.Form.FIELD_SET
				,ValuePropertiesEditPage.Form.FIELD_DERIVED,ValuePropertiesEditPage.Form.FIELD_DERIVATION_SCRIPT,ValuePropertiesEditPage.Form.FIELD_NULLABLE
				,ValuePropertiesEditPage.Form.FIELD_NULL_STRING);
	
		registerDetailsConfiguration(ValuePropertiesDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ValuePropertiesDetails.FIELD_CODE,ValuePropertiesDetails.FIELD_NAME,ValuePropertiesDetails.FIELD_MEASURE
								,ValuePropertiesDetails.FIELD_INTERVAL_COLLECTION,ValuePropertiesDetails.FIELD_TYPE,ValuePropertiesDetails.FIELD_SET
								,ValuePropertiesDetails.FIELD_DERIVED,ValuePropertiesDetails.FIELD_DERIVATION_SCRIPT,ValuePropertiesDetails.FIELD_NULLABLE
								,ValuePropertiesDetails.FIELD_NULL_STRING);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,ValuePropertiesDetails.FIELD_CODE,ValuePropertiesDetails.FIELD_NAME,ValuePropertiesDetails.FIELD_MEASURE
								,ValuePropertiesDetails.FIELD_INTERVAL_COLLECTION,ValuePropertiesDetails.FIELD_TYPE,ValuePropertiesDetails.FIELD_SET
								,ValuePropertiesDetails.FIELD_DERIVED,ValuePropertiesDetails.FIELD_DERIVATION_SCRIPT,ValuePropertiesDetails.FIELD_NULLABLE
								,ValuePropertiesDetails.FIELD_NULL_STRING);
					}
				};
			}
		});
		
		getFormConfiguration(ValueCollection.class, Crud.CREATE)
		.addRequiredFieldNames(ValueCollectionEditPage.Form.FIELD_CODE).addFieldNames(ValueCollectionEditPage.Form.FIELD_NAME
				,ValueCollectionEditPage.Form.FIELD_ONE_ITEM_MASTER_SELECTED);
	
		registerDetailsConfiguration(ValueCollectionDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ValueCollectionDetails.FIELD_CODE,ValueCollectionDetails.FIELD_NAME);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,ValueCollectionDetails.FIELD_CODE,ValueCollectionDetails.FIELD_NAME);
					}
				};
			}
		});
		
		registerDetailsConfiguration(ValueCollectionItemDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ValueCollectionItemDetails.FIELD_CODE,ValueCollectionItemDetails.FIELD_NAME
								,ValueCollectionItemDetails.FIELD_VALUE);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,ValueCollectionItemDetails.FIELD_CODE,ValueCollectionItemDetails.FIELD_NAME
								,ValueCollectionItemDetails.FIELD_VALUE);
					}
				};
			}
		});
		
		getFormConfiguration(Measure.class, Crud.CREATE)
		.addRequiredFieldNames(MeasureEditPage.Form.FIELD_CODE).addFieldNames(MeasureEditPage.Form.FIELD_NAME,MeasureEditPage.Form.FIELD_TYPE,MeasureEditPage.Form.FIELD_VALUE);
	
		registerDetailsConfiguration(MeasureDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,MeasureDetails.FIELD_CODE,MeasureDetails.FIELD_NAME,MeasureDetails.FIELD_TYPE,MeasureDetails.FIELD_VALUE);
					}
				};
			}
		});
	}
	
	@Override
	protected void configureNetworkModule() {
		super.configureNetworkModule();
		getFormConfiguration(Computer.class, Crud.CREATE)
		.addRequiredFieldNames(ComputerEditPage.Form.FIELD_CODE,ComputerEditPage.Form.FIELD_NAME
				,ComputerEditPage.Form.FIELD_IP_ADDRESS,ComputerEditPage.Form.FIELD_IP_ADDRESS_NAME);
	
		registerDetailsConfiguration(ComputerDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ComputerDetails.FIELD_CODE,ComputerDetails.FIELD_NAME
								,ComputerDetails.FIELD_IP_ADDRESS,ComputerDetails.FIELD_IP_ADDRESS_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(Service.class, Crud.CREATE)
		.addRequiredFieldNames(ServiceEditPage.Form.FIELD_CODE,ServiceEditPage.Form.FIELD_NAME,ServiceEditPage.Form.FIELD_COMPUTER,ServiceEditPage.Form.FIELD_PORT
				,ServiceEditPage.Form.FIELD_AUTHENTICATED,ServiceEditPage.Form.FIELD_SECURED);
	
		registerDetailsConfiguration(ServiceDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,ServiceDetails.FIELD_CODE,ServiceDetails.FIELD_NAME,ServiceDetails.FIELD_COMPUTER,ServiceDetails.FIELD_PORT
								,ServiceDetails.FIELD_AUTHENTICATED,ServiceDetails.FIELD_SECURED);
					}
				};
			}
		});
		
	}

	@Override
	protected void configureMessageModule() {
		super.configureMessageModule();
		getFormConfiguration(SmtpProperties.class, Crud.CREATE)
		.addRequiredFieldNames(SmtpPropertiesEditPage.Form.FIELD_CODE,SmtpPropertiesEditPage.Form.FIELD_SERVICE
				,SmtpPropertiesEditPage.Form.FIELD_CREDENTIALS,SmtpPropertiesEditPage.Form.FIELD_FROM);
	
		registerDetailsConfiguration(SmtpPropertiesDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){ 
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,SmtpPropertiesDetails.FIELD_CODE,SmtpPropertiesDetails.FIELD_SERVICE
								,SmtpPropertiesDetails.FIELD_CREDENTIALS,SmtpPropertiesDetails.FIELD_FROM);
					}
				};
			}
		});
	}
	
	@Override
	protected void configureUserInterfaceModule() {}
	
	protected void configurePatternModule(){
		getFormConfiguration(DataTreeType.class,Crud.CREATE).addRequiredFieldNames(DataTreeTypeEditPage.Form.FIELD_CODE)
		.addFieldNames(DataTreeTypeEditPage.Form.FIELD_NAME,DataTreeTypeEditPage.Form.FIELD_PARENT);
		registerDetailsConfiguration(DataTreeTypeDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,DataTreeTypeDetails.FIELD_CODE,DataTreeTypeDetails.FIELD_NAME,DataTreeTypeDetails.FIELD_PARENT);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,DataTreeTypeDetails.FIELD_CODE,DataTreeTypeDetails.FIELD_NAME);
					}
				};
			}
		});
		
		getFormConfiguration(DataTree.class,Crud.CREATE).addRequiredFieldNames(DataTreeEditPage.Form.FIELD_CODE)
				.addFieldNames(DataTreeEditPage.Form.FIELD_NAME,DataTreeEditPage.Form.FIELD_TYPE
						,DataTreeEditPage.Form.FIELD_PARENT);
		registerDetailsConfiguration(DataTreeDetails.class, new DetailsConfiguration(){
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			@Override
			public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
				return new DetailsConfiguration.DefaultControlSetAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean build(Object data,Field field) {
						return isFieldNameIn(field,DataTreeDetails.FIELD_CODE,DataTreeDetails.FIELD_NAME
								,DataTreeDetails.FIELD_TYPE,DataTreeDetails.FIELD_PARENT);
					}
				};
			}
			
			@Override
			public ColumnAdapter getTableColumnAdapter(@SuppressWarnings("rawtypes") Class clazz,AbstractPrimefacesPage page) {
				return new DetailsConfiguration.DefaultColumnAdapter(){
					private static final long serialVersionUID = 1L;
					@Override
					public Boolean isColumn(Field field) {
						return isFieldNameIn(field,DataTreeDetails.FIELD_CODE,DataTreeDetails.FIELD_NAME,DataTreeDetails.FIELD_TYPE);
					}
				};
			}
		});
	}
	
	@Override
	protected void configureTimeModule() {
		super.configureTimeModule();
		
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
