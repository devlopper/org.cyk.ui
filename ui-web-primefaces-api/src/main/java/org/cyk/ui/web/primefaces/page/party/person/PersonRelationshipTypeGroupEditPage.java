package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.PersonRelationshipTypeGroup;
import org.cyk.ui.api.model.pattern.tree.AbstractDataTreeForm;
import org.cyk.ui.web.primefaces.page.geography.AbstractDataTreeNodeEditPage;
import org.cyk.utility.common.annotation.FieldOverride;
import org.cyk.utility.common.annotation.FieldOverrides;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonRelationshipTypeGroupEditPage extends AbstractDataTreeNodeEditPage<PersonRelationshipTypeGroup> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;	
	
	@Getter @Setter 
	@FieldOverrides(value = {
			@FieldOverride(name=AbstractDataTreeForm.FIELD_PARENT,type=PersonRelationshipTypeGroup.class)
			})
	public static class Form extends AbstractForm<PersonRelationshipTypeGroup> {
		private static final long serialVersionUID = -4741435164709063863L;
		
		
	}
	
}
