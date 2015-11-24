package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIProvider;
import org.cyk.ui.api.command.UICommandable;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.party.DefaultPersonEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.form.FormOneData;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractActorConsultPage<ACTOR extends AbstractActor> extends AbstractConsultPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private FormOneData<MainDetails> mainDetails;
	private FormOneData<MedicalDetails> medicalDetails;
	private FormOneData<ContactDetails> contactDetails;
	private FormOneData<JobDetails> jobDetails;
	private FormOneData<RelationshipDetails> relationshipDetails;
	private FormOneData<SignatureDetails> signatureDetails;
	//private FormOneData<MainDetails> otherDetails;
	
	protected Boolean showMainDetails=Boolean.TRUE,showContactDetails=Boolean.FALSE,showRelationshipDetails=Boolean.FALSE,showJobDetails=Boolean.FALSE
			,showMedicalDetails=Boolean.FALSE;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void initialisation() {
		super.initialisation();
		
		mainDetails = createDetailsForm(MainDetails.class, identifiable, new ActorDetailsFormOneDataConfigurationAdapter<MainDetails>(
				(Class<AbstractActor>) identifiable.getClass(),MainDetails.class){
			private static final long serialVersionUID = 1L;
			
			@Override
			public MainDetails createData(AbstractActor identifiable) {
				return new MainDetails(identifiable);
			}
			@Override
			public Boolean getEnabledInDefaultTab() {
				return Boolean.TRUE;
			}
			@Override
			public String getTitleId() {
				return businessEntityInfos.getUiLabelId();
			}
			@Override
			public String getTabId() {
				return DefaultPersonEditFormModel.TAB_PERSON_ID;
			}
		});
		
		contactDetails = createDetailsForm(ContactDetails.class, identifiable, new ActorDetailsFormOneDataConfigurationAdapter<ContactDetails>((Class<AbstractActor>) identifiable.getClass(),ContactDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public ContactDetails createData(AbstractActor identifiable) {
				return new ContactDetails(identifiable);
			}
			@Override
			public String getTitleId() {
				return "contacts";
			}
			@Override
			public String getTabId() {
				return DefaultPersonEditFormModel.TAB_CONTACT_ID;
			}
		});
		
		signatureDetails = createDetailsForm(SignatureDetails.class, identifiable, new ActorDetailsFormOneDataConfigurationAdapter<SignatureDetails>((Class<AbstractActor>) identifiable.getClass(),SignatureDetails.class){
			private static final long serialVersionUID = 1L;
			@Override
			public SignatureDetails createData(AbstractActor identifiable) {
				return new SignatureDetails(identifiable);
			}
			@Override
			public String getTitleId() {
				return "signature";
			}
			@Override
			public String getTabId() {
				return DefaultPersonEditFormModel.TAB_SIGNATURE_ID;
			}
		});
	}
	
	@Override
	protected Collection<UICommandable> contextualCommandables() {
		UICommandable contextualMenu = UIProvider.getInstance().createCommandable("button", null),commandable=null;
		contextualMenu.setLabel(contentTitle); 
		
		commandable = navigationManager.createUpdateCommandable(identifiable, "command.edit", null);
		
		if(StringUtils.isEmpty(selectedTabId))
			;
		else
			commandable.addParameter(webManager.getRequestParameterTabId(), selectedTabId);
		contextualMenu.getChildren().add(commandable);
		
		return Arrays.asList(contextualMenu);
	}
		
	@Getter @Setter
	public static class MainDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File photo;
		@Input @InputText private String registrationCode,registrationDate,title,firstName,lastNames,surname,birthDate,birthLocation,sex/*,maritalStatus,nationality*/;
		public MainDetails(AbstractActor actor) {
			super(actor);
			Person person = actor.getPerson();
			registrationCode = actor.getRegistration().getCode();
			registrationDate = timeBusiness.formatDate(actor.getRegistration().getDate());
			photo = person.getImage();
			
			firstName = person.getName();
			lastNames = person.getLastName();
			surname = person.getSurname();
			if(person.getSex()!=null)
				sex = person.getSex().getName();
			/*
			if(person.getNationality()!=null)
				nationality = person.getNationality().getUiString();
			*/
			if(person.getBirthDate()!=null)
				birthDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDate(person.getBirthDate());
			
			if(person.getExtendedInformations()!=null){
				if(person.getExtendedInformations().getTitle()!=null)
					title = person.getExtendedInformations().getTitle().getName();
				if(person.getExtendedInformations().getBirthLocation()!=null)
					birthLocation = person.getExtendedInformations().getBirthLocation().getUiString();
			}
		}
	}
	
	@Getter @Setter
	public static class ContactDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputText private String phoneNumbers,electronicMails,locations,postalBoxes;
		public ContactDetails(AbstractActor actor) {
			super(actor);
			phoneNumbers = StringUtils.join(actor.getPerson().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
			electronicMails = StringUtils.join(actor.getPerson().getContactCollection().getElectronicMails(),Constant.CHARACTER_COMA);
			locations = StringUtils.join(actor.getPerson().getContactCollection().getLocations(),Constant.CHARACTER_COMA);
			postalBoxes = StringUtils.join(actor.getPerson().getContactCollection().getPostalBoxs(),Constant.CHARACTER_COMA);
		}
	}
	
	@Getter @Setter
	public static class MedicalDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputText private String bloodGroup/*,allergicReactionResponse,allergicReactionType*/,allergies,medications,otherInformations;
		public MedicalDetails(AbstractActor actor) {
			super(actor);
			Person person = actor.getPerson();
			if(actor.getPerson().getMedicalInformations()!=null){
				if(person.getMedicalInformations().getBloodGroup()!=null)
					bloodGroup = person.getMedicalInformations().getBloodGroup().getName();
				//allergicReactionResponse = person.getMedicalInformations().getAllergicReactionResponse();
				//allergicReactionType = person.getMedicalInformations().getAllergicReactionType();
				otherInformations = person.getMedicalInformations().getOtherInformations();
				allergies = StringUtils.join(person.getMedicalInformations().getMedicalInformationsAllergies(),Constant.CHARACTER_COMA);
				medications = StringUtils.join(person.getMedicalInformations().getMedications(),Constant.CHARACTER_COMA);
			}
		}
	}
	
	@Getter @Setter
	public static class JobDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputText private String company,function,title,contacts;
		public JobDetails(AbstractActor actor) {
			super(actor);
			Person person = actor.getPerson();
			if(actor.getPerson().getJobInformations()!=null){
				company = person.getJobInformations().getCompany();
				if(person.getJobInformations().getFunction()!=null)
					function = person.getJobInformations().getFunction().getName();
				if(person.getJobInformations().getTitle()!=null)
					title = person.getJobInformations().getTitle().getName();
				if(person.getJobInformations().getContactCollection()!=null)
					contacts = StringUtils.join(person.getJobInformations().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
			}
		}
	}
	
	@Getter @Setter
	public static class RelationshipDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputText private String father,mother;
		public RelationshipDetails(AbstractActor actor) {
			super(actor);
			//Person person = actor.getPerson();
			
		}
	}
	
	@Getter @Setter
	public static class SignatureDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File specimen;
		public SignatureDetails(AbstractActor actor) {
			super(actor);
			Person person = actor.getPerson();
			if(person.getExtendedInformations()!=null){
				if(person.getExtendedInformations().getSignatureSpecimen()!=null)
					specimen = person.getExtendedInformations().getSignatureSpecimen();
			}
		}
	}
	
	@Getter @Setter
	public static class OtherDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		public OtherDetails(AbstractActor actor) {
			super(actor);
		}
	}
	
	public static class ActorDetailsFormOneDataConfigurationAdapter<DETAILS extends AbstractOutputDetails<AbstractActor>> extends DetailsConfigurationListener.Form.Adapter<AbstractActor,DETAILS>{

		private static final long serialVersionUID = -9101575271431241099L;

		public ActorDetailsFormOneDataConfigurationAdapter(Class<AbstractActor> actorClass,Class<DETAILS> detailsClass) {
			super(actorClass, detailsClass);
		}
		
	}
}
