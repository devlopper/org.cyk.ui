package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.system.root.model.party.person.PersonSearchCriteria;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.table.Row;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractBusinessQueryPage;
import org.cyk.ui.web.primefaces.test.form.PersonSearchPage.PersonQueryFormModel;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.model.table.Dimension.DimensionType;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Named
@ViewScoped
@Getter
@Setter
public class PersonSearchPage extends AbstractBusinessQueryPage<Person,PersonQueryFormModel, PersonSearchPage.PersonResultFormModel> implements Serializable {

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
		
	}
	
	@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		
	}
	
	@Override
	protected DimensionType getRowType(Row<Object> row) {
		//return super.getRowType(row);
		return StringUtils.isBlank(((PersonResultFormModel)row.getData()).getNames()) ? DimensionType.SUMMARY:super.getRowType(row);
	}
	
	@Override
	protected Boolean autoLoad() {
		return Boolean.TRUE;
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
	protected Class<PersonResultFormModel> __resultClass__() {
		return PersonResultFormModel.class;
	}

	private PersonSearchCriteria criteria(){
		PersonSearchCriteria c = new PersonSearchCriteria(query.getName());
		return c;
	}
	
	@Override
	protected Collection<Person> __query__() {
		PersonSearchCriteria c = criteria();
		//c.getReadConfig().setFirstResultIndex(queryFirst);
		//c.getReadConfig().setMaximumResultCount(3l);
		return personBusiness.findByCriteria(c);
	}

	@Override
	protected Long __count__() {
		return personBusiness.countByCriteria(criteria());
	}	
	
	@Override
	protected Collection<PersonResultFormModel> __results__(Collection<Person> identifiables) {
		Integer cb = 0;
		Collection<PersonResultFormModel> collection = new ArrayList<>();
		int i=0;
		for(PersonResultFormModel p : super.__results__(identifiables)){
			collection.add(p);
			if(i++%8==0){
				PersonResultFormModel personResultFormModel = new PersonResultFormModel();
				personResultFormModel.c1 = "RECAP "+i+" Here";
				collection.add(personResultFormModel);	
				cb++;
			}		
		}
		
		return collection;
	}
		
	@Getter @Setter
	public static class PersonQueryFormModel{
		
		@Input @InputText
		private String name;
		
		@Input @InputText
		private String lastName;
		
	}
	
	@Getter @Setter @NoArgsConstructor
	public static class PersonResultFormModel{
		
		@Input @InputText
		private String names,c1,c2;
		
		public PersonResultFormModel(Person person) {
			names = person.getNames();
		}
	}
	
}
