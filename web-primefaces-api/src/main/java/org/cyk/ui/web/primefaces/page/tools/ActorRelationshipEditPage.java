package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonRelationship;
import org.cyk.system.root.model.party.person.PersonRelationshipType;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

@Named @ViewScoped @Getter @Setter
public class ActorRelationshipEditPage extends AbstractCrudOnePage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private ItemCollection<RelationshipItem,PersonRelationship> collection;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		collection = createItemCollection(form,"qwerty",RelationshipItem.class,PersonRelationship.class,null);
		
	}
			
	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class RelationshipItem extends AbstractItemCollectionItem<PersonRelationship> implements Serializable {
		
		private static final long serialVersionUID = 4978336459733952862L;

		@Input @InputChoice @InputOneChoice @InputOneCombo private PersonRelationshipType type;
		@Input @InputChoice @InputOneChoice @InputOneCombo private Person person1;
		@Input @InputChoice @InputOneChoice @InputOneCombo private Person person2;
		
	}

}
