package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.party.PersonFormModel;
import org.cyk.ui.api.model.table.Cell;
import org.cyk.ui.api.model.table.Column;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;
import org.cyk.ui.web.primefaces.test.form.PersonSearchPage.PersonQueryFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.model.table.TableAdapter;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class PersonSearchPage extends AbstractBusinessQueryPage<Person,PersonQueryFormModel, PersonFormModel> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private PersonBusiness personBusiness;
	
	{
		showGraphics = Boolean.FALSE;
	}
	
	private int i = 0;
	
	@Override
	protected void initialisation() {
		super.initialisation();
		form.getControlSetListeners().add(new ControlSetAdapter<PersonQueryFormModel>(){
			
			@Override
			public Boolean build(Field field) {
				return !field.getName().equals("identifier");
			}
			
			@Override
			public Boolean showFieldLabel(
					ControlSet<PersonQueryFormModel, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
					Field field) {
				return Boolean.FALSE;
			}
			
			@Override
			public Boolean canCreateRow(
					ControlSet<PersonQueryFormModel, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,
					Object object) {
				// TODO Auto-generated method stub
				return Boolean.FALSE;
			}
		}); 
		
		table.getTableListeners().add(new TableAdapter<Row<Object>, Column, Object, String, Cell, String>(){
			@Override
			public void rowAdded(Row<Object> row) {
				super.rowAdded(row);
				if(i++%2==0)
					row.getCascadeStyleSheet().addClass("qwerty_sr");
			}
		});
	}
	
	@Override
	protected Class<Person> __entityClass__() {
		return Person.class;
	}
	
	@Override
	protected Class<PersonQueryFormModel> __queryClass__() {
		return PersonQueryFormModel.class;
	}

	@Override
	protected Class<PersonFormModel> __resultClass__() {
		return PersonFormModel.class;
	}

	@Override
	protected Collection<Person> __query__() {
		return personBusiness.findByCriteria(new PersonSearchCriteria(query.getName()));
	}

	@Override
	protected Long __count__() {
		return personBusiness.countByCriteria(new PersonSearchCriteria(query.getName()));
	}	
	
	@Getter @Setter
	public static class PersonQueryFormModel{
		
		@Input @InputText
		private String name;
		
		@Input @InputText
		private String lastName;
		
	}
	
}
