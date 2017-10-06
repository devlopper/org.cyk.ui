package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Sex;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class SexEditPage extends AbstractCrudOnePage<Sex> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Sex> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		
		
	}
	
}
