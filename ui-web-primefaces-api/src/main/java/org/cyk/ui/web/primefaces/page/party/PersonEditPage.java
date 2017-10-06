package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;

import lombok.Getter;
import lombok.Setter;

//@Named @ViewScoped 
@Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	 
	@Override
	protected void afterInitialisation() {
		// TODO Auto-generated method stub
		super.afterInitialisation();
		/*form.findInputByClassByFieldName(InputOneCascadeList.class, "locality").setValue(
				((SelectItemGroup)form.findInputByClassByFieldName(InputOneCascadeList.class, "locality").getList().get(1)).getValue() );
		((org.cyk.ui.web.primefaces.data.collector.control.InputOneCascadeList)form.findInputByClassByFieldName(InputOneCascadeList.class, "locality")).setWidgetVar("aze");
		debug(form.findInputByClassByFieldName(InputOneCascadeList.class, "locality"));
		*/
		/*
		onDocumentLoadJavaScript = onDocumentLoadJavaScript + ";$( '.ui-multiselectlistbox-list li:nth-child(2)' ).css( 'background-color', 'red' ); "
				+ "PF('aze').showItemGroup($( '.ui-multiselectlistbox-list li:nth-child(2)' )); $( '.ui-multiselectlistbox-list li:nth-child(3)' ).click();"
				+ "$( '.ui-multiselectlistbox-list li:nth-child(2)' ).click(function(){alert(\"hello click\");});";
		*/
		//onDocumentReadyJavaScript =  "$( '.ui-multiselectlistbox-list > li:nth-child(2)' ).click(function(){alert(\"hello click\");});";
		/*onDocumentReadyJavaScript =  "$( '.ui-multiselectlistbox-list > li:nth-child(2)' ).click();"
				+ "$( '.ui-multiselectlistbox-list > li:nth-child(2) > ul > li:nth-child(3)' ).click();"
				;*/
		/*
		createAjaxBuilder(LocationFormModel.FIELD_LOCALITY_TYPE).updatedFieldNames(LocationFormModel.FIELD_LOCALITY)
		.method(LocalityType.class,new ListenValueMethod<LocalityType>() {
			@Override
			public void execute(LocalityType localityType) {
				//System.out.println(localityType+" : "+inject(LocalityBusiness.class).findByType(localityType).size());
				//setFieldValue(LocationFormModel.FIELD_LOCALITY, inject(LocalityBusiness.class).findByType(localityType));
				setChoices(LocationFormModel.FIELD_LOCALITY, inject(LocalityBusiness.class).findByType(localityType));
			}
		}).build();
		*/
	}
	
	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractPersonEditFormModel.Extends.Default<Person> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
	}

	
	
}
