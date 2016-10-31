package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;

@Getter @Setter @Named @ViewScoped
public class MedicalInformationsAllergyEditPage extends AbstractCrudOnePage<MedicalInformationsAllergy> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected MedicalInformationsAllergy instanciateIdentifiable() {
		MedicalInformationsAllergy medicalInformationsAllergy = super.instanciateIdentifiable();
		medicalInformationsAllergy.setInformations(webManager.getIdentifiableFromRequestParameter(MedicalInformations.class, Boolean.TRUE));
		return medicalInformationsAllergy;
	}
	
	@Getter @Setter
	public static class Form extends AbstractFormModel<MedicalInformationsAllergy> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull private Allergy allergy;
		
		@Input @InputTextarea private String reactionType;
		
		@Input @InputTextarea private String reactionResponse;
		
		/**/
		
		public static final String FIELD_ALLERGY = "allergy";
		public static final String FIELD_REACTION_TYPE = "reactionType";
		public static final String FIELD_REACTION_RESPONSE = "reactionResponse";
	}

}
