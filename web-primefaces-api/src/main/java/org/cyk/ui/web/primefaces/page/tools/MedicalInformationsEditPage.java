package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

@Named @ViewScoped @Getter @Setter
public class MedicalInformationsEditPage extends AbstractCrudOnePage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private ItemCollection<AllergyItem> allergyItems;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		allergyItems = createItemCollection("qwerty",AllergyItem.class); //new ItemCollection<>("qwerty",AllergyItem.class);
		/*
		allergyItems.getItemCollectionListeners().add(new ItemCollectionAdapter<AllergyItem>(){
			private static final long serialVersionUID = 4920928936636548919L;
			@Override
			public void instanciated(AbstractItemCollection<AllergyItem> itemCollection,AllergyItem item) {
				item.setForm(createFormOneData(item,Crud.CREATE));
			}
			
			@Override
			public void add(AbstractItemCollection<AllergyItem> itemCollection,AllergyItem item) {
				super.add(itemCollection,item);
				item.setForm(createFormOneData(item,Crud.CREATE));
				item.getForm().setDynamic(Boolean.TRUE);
				item.getForm().build(); 
			}
			
		});*/
	}
	
	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class AllergyItem extends AbstractItemCollectionItem implements Serializable {
		
		private static final long serialVersionUID = 4978336459733952862L;

		@Input @InputChoice @InputOneChoice @InputOneCombo 
		private Allergy allergy;
		
		@Input @InputText
		private String reaction;
		
		@Input @InputText
		private String response;
		
	}
}
