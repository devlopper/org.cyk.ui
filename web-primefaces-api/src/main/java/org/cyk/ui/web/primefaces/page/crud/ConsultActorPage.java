package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.primefaces.model.menu.MenuModel;

@Named @ViewScoped @Getter @Setter
public class ConsultActorPage extends AbstractConsultPage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private DetailsBlock<MenuModel> mainDetails,medicalDetails,contactDetails,jobDetails,relationshipDetails,otherDetails;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mainDetails = createDetailsBlock(new MainDetails(identifiable));
		contactDetails = createDetailsBlock(new ContactDetails(identifiable));
		relationshipDetails = createDetailsBlock(new RelationshipDetails(identifiable));
		jobDetails = createDetailsBlock(new JobDetails(identifiable));
		medicalDetails = createDetailsBlock(new MedicalDetails(identifiable));
		//otherDetails = createDetailsBlock(new OtherDetails(identifiable),navigationManager.getOutcomeDynamicCrudOne());
	}
		
	@Getter @Setter
	public static class MainDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File photo;
		@Input @InputText private String registrationCode,registrationDate,title,firstName,lastNames,birthDate,birthLocation,sex,maritalStatus,nationality;
		public MainDetails(AbstractActor actor) {
			super(actor);
			Person person = actor.getPerson();
			registrationCode = actor.getRegistration().getCode();
			registrationDate = timeBusiness.formatDate(actor.getRegistration().getDate());
			photo = person.getImage();
			firstName = person.getName();
			lastNames = person.getLastName();
			if(person.getSex()!=null)
				sex = person.getSex().getName();
			if(person.getNationality()!=null)
				nationality = person.getNationality().getUiString();
			if(person.getBirthDate()!=null)
				birthDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDate(person.getBirthDate());
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
			Person person = actor.getPerson();
			
		}
	}
	
	@Getter @Setter
	public static class OtherDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		public OtherDetails(AbstractActor actor) {
			super(actor);
		}
	}
}
