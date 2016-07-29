package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.impl.AbstractOutputDetails;
import org.cyk.system.root.model.file.File;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.FileExtensionGroup;
import org.cyk.utility.common.annotation.user.interfaces.FileExtensions;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputFile;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Getter @Setter
public abstract class AbstractActorConsultPage<ACTOR extends AbstractActor> extends AbstractPersonConsultPage<ACTOR> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
				
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
		
		public static final String FIELD_TITLE = "title";
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
	
	public static class Adapter<ACTOR extends AbstractActor> extends AbstractActorConsultPageAdapter.Default<ACTOR>{
		private static final long serialVersionUID = -5657492205127185872L;
		
		public Adapter(Class<ACTOR> entityTypeClass) {
			super(entityTypeClass);
		}
		
	}
}
