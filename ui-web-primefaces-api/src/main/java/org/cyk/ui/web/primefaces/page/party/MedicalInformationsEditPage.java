package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.AbstractActor;
import org.cyk.system.root.model.party.person.Allergy;
import org.cyk.system.root.model.party.person.MedicalInformations;
import org.cyk.system.root.model.party.person.MedicalInformationsAllergy;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.command.UICommand;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.AbstractItemCollectionItem;
import org.cyk.ui.web.primefaces.ItemCollection;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MedicalInformationsEditPage extends AbstractCrudOnePage<AbstractActor> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	private ItemCollection<AllergyItem,MedicalInformationsAllergy,MedicalInformations> allergyItems;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		allergyItems = createItemCollection(form,"qwerty",AllergyItem.class,MedicalInformationsAllergy.class,((Person)identifiable.getPerson()).getMedicalInformations(),null,null);
		
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 1L;

			@Override
			public Boolean build(Object data,Field field) {
				return Boolean.TRUE;
			}
		});
	}
		
	@Override
	protected Class<?> __formModelClass__() {
		return FormModel.class;
	}
	
	@Override
	public void serve(UICommand command, Object parameter) {
		System.out.println("MedicalInformationsEditPage.serve()");
		debug(form.getData());
	}
		
	/**/
	
	@Getter @Setter @NoArgsConstructor @AllArgsConstructor
	public static class AllergyItem extends AbstractItemCollectionItem<MedicalInformationsAllergy> implements Serializable {
		
		private static final long serialVersionUID = 4978336459733952862L;

		@Input @InputChoice @InputOneChoice @InputOneCombo private Allergy allergy;
		@Input @InputText private String reaction;
		@Input @InputText private String response;
		
	}
	
	@Getter @Setter
	public static class FormModel extends AbstractFormModel<AbstractActor> implements Serializable{
		private static final long serialVersionUID = 2231017067376454414L;
		
		@Input @InputText protected String firstName;
		@Input @InputText protected String lastNames;
		@Input @InputText protected String phoneNumber;
		@Input @InputText protected String relationShip;
		
		@Input @InputText protected String doctorFirstName;
		@Input @InputText protected String doctorLastNames;
		@Input @InputText protected String doctorPhoneNumber;
	
	}
}
