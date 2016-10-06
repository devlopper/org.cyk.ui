package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.party.person.MedicalInformationsBusiness;
import org.cyk.system.root.model.party.person.MedicalInformationsMedication;
import org.cyk.system.root.model.party.person.Medication;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputBooleanButton;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Getter @Setter @Named @ViewScoped
public class MedicalInformationsMedicationEditPage extends AbstractCrudOnePage<MedicalInformationsMedication> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MedicalInformationsMedication instanciateIdentifiable() {
		MedicalInformationsMedication medicalInformationsMedication = super.instanciateIdentifiable();
		medicalInformationsMedication.setInformations(inject(MedicalInformationsBusiness.class)
				.findByParty(webManager.getIdentifiableFromRequestParameter(Person.class, Boolean.TRUE)));
		return medicalInformationsMedication;
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<MedicalInformationsMedication> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private Medication medication;
		
		@Input @InputBooleanButton private Boolean mustBeAvailable;
		
		@Input @InputBooleanButton private Boolean mustBeGiven;
		
		/**/
		
		public static final String FIELD_MEDICATION = "medication";
		public static final String FIELD_MUST_BE_AVAILABLE = "mustBeAvailable";
		public static final String FIELD_REACTION_RESPONSE = "reactionResponse";
	}

}
