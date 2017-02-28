package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractItemCollection;
import org.cyk.ui.api.model.party.PersonEditManyForm;
import org.cyk.ui.web.api.ItemCollectionWebAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractEditManyPage;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PersonEditManyPage extends AbstractEditManyPage<Person,PersonEditManyForm> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	/*
	@Inject private PersonBusiness personBusiness;
	
	@Override
	protected void initialisation() {
		super.initialisation();
				
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
	}*/
	
	@Override
	public ItemCollectionWebAdapter<PersonEditManyForm, Person,AbstractIdentifiable> getItemCollectionAdapter() {
		return new ItemCollectionAdapter<PersonEditManyForm,Person,AbstractIdentifiable>(businessEntityInfos){
			private static final long serialVersionUID = -5381415970572336750L;
			
			@Override
			public void instanciated(AbstractItemCollection<PersonEditManyForm, Person,AbstractIdentifiable, SelectItem> itemCollection,PersonEditManyForm item) {
				super.instanciated(itemCollection, item);
				item.setName(item.getIdentifiable().getName());
				item.setLastname(item.getIdentifiable().getLastnames());
				item.setSurname(item.getIdentifiable().getSurname());
			}
			
			@Override
			public void write(PersonEditManyForm item) {
				super.write(item);
				item.getIdentifiable().setName(item.getName());
				item.getIdentifiable().setLastnames(item.getLastname());
				item.getIdentifiable().setSurname(item.getSurname());
			}
		};
	}
	
	/*
	@Override
	protected Class<PersonEditManyForm> getItemCollectionItemClass() {
		return PersonEditManyForm.class;
	}
	*/
}
