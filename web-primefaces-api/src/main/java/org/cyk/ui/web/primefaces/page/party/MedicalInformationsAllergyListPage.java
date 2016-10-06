package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudManyPage;

@Named @ViewScoped @Getter @Setter
public class MedicalInformationsAllergyListPage extends AbstractCrudManyPage<MedicalInformationsAllergy> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
}
