package org.cyk.ui.api.model.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.impl.RootBusinessLayer;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.geography.ContactCollectionReadFormModel;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs.Layout;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.ReportColumn;
import org.cyk.utility.common.annotation.user.interfaces.Text;
import org.cyk.utility.common.cdi.AbstractBean;

@Getter @Setter
public abstract class AbstractPersonReadFormModel<ENTITY extends AbstractIdentifiable> extends AbstractBean  implements Serializable {

	private static final long serialVersionUID = -3897201743383535836L;

	private ENTITY identifiable;
	
	@Input @InputFile (extensions=@FileExtensions(groups=FileExtensionGroup.IMAGE)) private File photo;
	@Input @InputText @ReportColumn protected String title,surname,firstName,lastName,birthDate,birthLocation,sex/*,maritalStatus*/,nationality;
	
	@OutputSeperator(label=@Text(value="field.contacts")) 
	@IncludeInputs(layout=Layout.VERTICAL) 
	protected ContactCollectionReadFormModel contactCollectionFormModel = new ContactCollectionReadFormModel();
	
	//@Input @InputText @ReportColumn protected String bloodGroup,allergicReactionResponse,allergicReactionType,allergies,medications,otherMedicalInformations;
	//@Input @InputText @ReportColumn protected String jobCompany,jobFunction,jobTitle,jobContacts;
	
	public AbstractPersonReadFormModel(ENTITY entity){
		identifiable = entity;
		Person person = getPerson(entity);
		//photo = person.getImage();
		firstName = person.getName();
		lastName = person.getLastName();
		surname = person.getSurname();
		
		if(person.getSex()!=null)
			sex = person.getSex().getName();
		if(person.getNationality()!=null)
			nationality = person.getNationality().getUiString();
		if(person.getBirthDate()!=null)
			birthDate = RootBusinessLayer.getInstance().getTimeBusiness().formatDate(person.getBirthDate());
		
		if(person.getExtendedInformations()!=null){
			if(person.getExtendedInformations().getTitle()!=null)
				title = person.getExtendedInformations().getTitle().getName();
			if(person.getExtendedInformations().getBirthLocation()!=null)
				birthLocation = person.getExtendedInformations().getBirthLocation().getComment();
			if(person.getExtendedInformations().getMaritalStatus()!=null)
				;//maritalStatus = person.getExtendedInformations().getMaritalStatus().getName();
		}
		/*
		if(person.getMedicalInformations()!=null){
			if(person.getMedicalInformations().getBloodGroup()!=null)
				bloodGroup = person.getMedicalInformations().getBloodGroup().getName();
			allergicReactionResponse = person.getMedicalInformations().getAllergicReactionResponse();
			allergicReactionType = person.getMedicalInformations().getAllergicReactionType();
			otherMedicalInformations = person.getMedicalInformations().getOtherInformations();
			allergies = StringUtils.join(person.getMedicalInformations().getAllergies(),Constant.CHARACTER_COMA);
			medications = StringUtils.join(person.getMedicalInformations().getMedications(),Constant.CHARACTER_COMA);
		}
		
		if(person.getJobInformations()!=null){
			jobCompany = person.getJobInformations().getCompany();
			if(person.getJobInformations().getFunction()!=null)
				jobFunction = person.getJobInformations().getFunction().getName();
			if(person.getJobInformations().getTitle()!=null)
				jobTitle = person.getJobInformations().getTitle().getName();
			if(person.getJobInformations().getContactCollection()!=null)
				jobContacts = StringUtils.join(person.getJobInformations().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
		}
		*/
		contactCollectionFormModel.setIdentifiable(person.getContactCollection());
		contactCollectionFormModel.read();
		
		
	}
	
	protected abstract Person getPerson(ENTITY entity);
	
	/**/
	
	public static final String FIELD_PHOTO = "photo";
	public static final String FIELD_FIRST_NAME = "firstName";
	public static final String FIELD_LAST_NAME = "lastName";
	public static final String FIELD_SUR_NAME = "surname";
	public static final String FIELD_BIRTH_DATE = "birthDate";
	public static final String FIELD_BIRTH_LOCATION = "birthLocation";
	public static final String FIELD_SEX = "sex";
	public static final String FIELD_MARITAL_STATUS = "maritalStatus";
	public static final String FIELD_NATIONALITY = "nationality";
	public static final String FIELD_TITLE = "title";
	
	public static final String FIELD_BLOOD_GROUP = "bloodGroup";
	public static final String FIELD_ALLERGIC_REACTION_RESPONSE = "allergicReactionResponse";
	public static final String FIELD_ALLERGIC_REACTION_TYPE = "allergicReactionType";
	public static final String FIELD_ALLERGIES = "allergies";
	public static final String FIELD_MEDICATIONS = "medications";
	public static final String FIELD_OTHER_MEDICAL_INFORMATIONS = "otherMedicalInformations";
	
	public static final String FIELD_JOB_TITLE = "jobTitle";
	public static final String FIELD_JOB_FUNCTION = "jobFunction";
	public static final String FIELD_COMPANY = "jobCompany";
	public static final String FIELD_JOB_CONTACTS = "jobContacts";
		
}
