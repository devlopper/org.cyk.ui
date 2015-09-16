package org.cyk.ui.web.primefaces.page.crud;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.ui.api.model.AbstractOutputDetails;
import org.cyk.ui.api.model.DetailsBlock;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.primefaces.model.menu.MenuModel;

@Named @ViewScoped @Getter @Setter
public class ConsultActorPage extends AbstractConsultPage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	private DetailsBlock<MenuModel> mainDetails,medicalDetails,contactDetails,jobDetails,otherDetails;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		mainDetails = createDetailsBlock(new MainDetails(identifiable),navigationManager.getOutcomeDynamicCrudOne());
		contactDetails = createDetailsBlock(new ContactDetails(identifiable),navigationManager.getOutcomeDynamicCrudOne());
		/*
		medicalDetails = createDetailsBlock("medical", new MedicalDetails(identifiable));
		contactDetails = createDetailsBlock("contact", new ContactDetails(identifiable));
		jobDetails = createDetailsBlock("job", new JobDetails(identifiable));
		otherDetails = createDetailsBlock("other", new OtherDetails(identifiable));
		*/
	}
		
	@Getter @Setter
	public static class MainDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		@Input @InputText
		private String firstName,lastNames;
		
		public MainDetails(AbstractActor actor) {
			super(actor);
			firstName = actor.getPerson().getName();
			lastNames = actor.getPerson().getLastName();
		}
	}
	
	@Getter @Setter
	public static class MedicalDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		@Input @InputText
		private String allergies;
		
		public MedicalDetails(AbstractActor actor) {
			super(actor);
			if(actor.getPerson().getMedicalInformations()!=null){
				allergies = StringUtils.join(actor.getPerson().getMedicalInformations().getAllergies(),Constant.CHARACTER_COMA);
			}
			
			
		}
	}
	
	@Getter @Setter
	public static class ContactDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		@Input @InputText
		private String phoneNumbers,electronicMails,locations;
		
		public ContactDetails(AbstractActor actor) {
			super(actor);
			phoneNumbers = StringUtils.join(actor.getPerson().getContactCollection().getPhoneNumbers(),Constant.CHARACTER_COMA);
			electronicMails = StringUtils.join(actor.getPerson().getContactCollection().getElectronicMails(),Constant.CHARACTER_COMA);
			locations = StringUtils.join(actor.getPerson().getContactCollection().getLocations(),Constant.CHARACTER_COMA);
		}
	}
	
	@Getter @Setter
	public static class JobDetails extends AbstractOutputDetails<AbstractActor> implements Serializable {
		private static final long serialVersionUID = -1498269103849317057L;
		
		@Input @InputText
		private String company,occupation;
		
		public JobDetails(AbstractActor actor) {
			super(actor);
			if(actor.getPerson().getJobInformations()!=null){
				company = actor.getPerson().getJobInformations().getCompany();
				occupation = actor.getPerson().getJobInformations().getTitle().getName();
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
}
