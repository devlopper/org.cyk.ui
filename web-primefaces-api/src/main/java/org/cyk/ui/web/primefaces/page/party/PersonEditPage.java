package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class PersonEditPage extends AbstractPersonEditPage<Person> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		/*
		InputOneCascadeList<Locality> inputOneCascadeList = (InputOneCascadeList<Locality>) form.findInputByFieldName(LocationFormModel.FIELD_LOCALITY);

		inputOneCascadeList.setHeader("Continent");
		List<SelectItem> categories = inputOneCascadeList.getList();
		categories.addAll(webManager.getSelectItemsFromNodes((List<? extends AbstractDataTreeNode>) inject(LocalityBusiness.class).findHierarchies()));
		
		*/
		/*
		InputOneAutoComplete<Locality> localityInputOneAutoComplete = (InputOneAutoComplete<Locality>) form.findInputByFieldName(LocationFormModel.FIELD_LOCALITY);
		localityInputOneAutoComplete.getCommon().getAutoCompleteListeners().add(new InputAutoCompleteCommon.Listener.Adapter.Default(Locality.class));
		/*
		InputOneAutoComplete<Country> countryInputOneAutoComplete = (InputOneAutoComplete<Country>) form.findInputByFieldName(AbstractPersonEditFormModel.FIELD_NATIONALITY);
		countryInputOneAutoComplete.getCommon().getAutoCompleteListeners().add(new InputAutoCompleteCommon.Listener.Adapter.Default(Country.class));
		*/
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
