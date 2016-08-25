package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.geography.LocalityBusiness;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.model.geography.LocalityType;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;
import org.cyk.ui.web.api.AjaxListener.ListenValueMethod;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	/*
	@Override
	protected void initialisation() {
		super.initialisation();
		form.getControlSetListeners().add(new ControlSetAdapter<Object>(){
			private static final long serialVersionUID = 1L;
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Object data,Field field) {
				if(data instanceof LocationFormModel && ((Form)form.getData()).getBirthLocation() == data )
					return inject(LanguageBusiness.class).findText("field.birth.location");
				return super.fiedLabel(controlSet, data,field);
			}
		});
	}
	*/
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
		
		createAjaxBuilder(LocationFormModel.FIELD_LOCALITY_TYPE).updatedFieldNames(LocationFormModel.FIELD_LOCALITY)
		.method(LocalityType.class,new ListenValueMethod<LocalityType>() {
			@Override
			public void execute(LocalityType localityType) {
				//System.out.println(localityType+" : "+inject(LocalityBusiness.class).findByType(localityType).size());
				//setFieldValue(LocationFormModel.FIELD_LOCALITY, inject(LocalityBusiness.class).findByType(localityType));
				setChoices(LocationFormModel.FIELD_LOCALITY, inject(LocalityBusiness.class).findByType(localityType));
			}
		}).build();
	}
	
	@Override
	protected Person getPerson() {
		return identifiable;
	}
	
	@Getter @Setter
	public static class Form extends AbstractPersonEditFormModel.AbstractDefault.Default<Person> implements Serializable {

		private static final long serialVersionUID = 2646571878912106597L;
		
	}

	
	
}
