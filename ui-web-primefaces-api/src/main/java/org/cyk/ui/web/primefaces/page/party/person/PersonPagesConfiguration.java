package org.cyk.ui.web.primefaces.page.party.person;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.ArrayUtils;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.language.LanguageBusiness;
import org.cyk.system.root.business.api.party.person.PersonBusiness;
import org.cyk.system.root.business.impl.language.LanguageCollectionDetails;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.PersonBusinessImpl.AbstractPersonDetails;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.ControlSet;
import org.cyk.ui.api.model.geography.LocationFormModel;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;
import org.cyk.ui.web.primefaces.data.collector.control.ControlSetAdapter;
import org.cyk.ui.web.primefaces.page.AbstractIdentifiablePagesConfiguration;
import org.cyk.ui.web.primefaces.page.DetailsConfiguration;
import org.cyk.ui.web.primefaces.page.party.AbstractPersonEditPage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PersonPagesConfiguration extends AbstractIdentifiablePagesConfiguration<Person> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*@Override
	protected String[] getRequiredFieldNames(Action action) {
		return new String[]{PersonEditPage.Form.FIELD_NAME};
	}
	
	@Override
	protected String[] getFieldNames(Action action) {
		return new String[]{PersonEditPage.Form.FIELD_CODE,PersonEditPage.Form.FIELD_NAME};
	}*/
	
	@Override
	protected DetailsConfiguration getDetailsConfiguration() {
		return new PersonDetailsConfiguration();
	}
	
	/**/
	
	@Getter @Setter
	public static class PersonDetailsConfiguration extends DetailsConfiguration implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@SuppressWarnings("rawtypes")
		@Override
		public ControlSetAdapter.Details getFormControlSetAdapter(Class clazz) {
			return new DetailsControlSetAdapter(clazz);
		}
		
		/**/
		
		@Getter @Setter
		public static class FormControlSetAdapter extends ControlSetAdapter.Form<Object> implements Serializable{
			private static final long serialVersionUID = 1L;
			
			public FormControlSetAdapter(Class<?> identifiableClass){
				super(identifiableClass,Crud.CREATE);
				if(Boolean.TRUE.equals(PersonBusiness.FindNamesArguments.FIRST_NAME_IS_FIRST))
					addFieldNamePairOrder(AbstractPersonEditFormModel.FIELD_NAME, AbstractPersonEditFormModel.FIELD_LAST_NAMES);
				else
					addFieldNamePairOrder(AbstractPersonEditFormModel.FIELD_LAST_NAMES, AbstractPersonEditFormModel.FIELD_NAME);
			}
			
			@Override
			public String fiedLabel(ControlSet<Object, DynaFormModel, DynaFormRow, DynaFormLabel, DynaFormControl, SelectItem> controlSet,Object data,Field field) {
				System.out.println("PersonDetailsConfiguration.FormControlSetAdapter.fiedLabel()");
				if(data instanceof LocationFormModel && ((AbstractPersonEditPage.AbstractForm<?>)controlSet.getFormData().getData()).getBirthLocation() == data ){
					if(LocationFormModel.FIELD_LOCALITY.equals(field.getName()))
						return inject(LanguageBusiness.class).findText("field.birth.location");
					else if(LocationFormModel.FIELD_OTHER_DETAILS.equals(field.getName()))
						return inject(LanguageBusiness.class).findText("field.birth.location.other.details");
				}
				return super.fiedLabel(controlSet, data,field);
			}	
		}
		
		@Getter @Setter @NoArgsConstructor
		public static class DetailsControlSetAdapter extends DetailsConfiguration.DefaultControlSetAdapter implements Serializable{
			private static final long serialVersionUID = 1L;
			
			public DetailsControlSetAdapter(Class<?> identifiableClass) {
				super(identifiableClass);
				if(Boolean.TRUE.equals(PersonBusiness.FindNamesArguments.FIRST_NAME_IS_FIRST))
					addFieldNamePairOrder(AbstractPersonDetails.FIELD_NAME, AbstractPersonDetails.FIELD_LASTNAMES);
				else
					addFieldNamePairOrder(AbstractPersonDetails.FIELD_LASTNAMES, AbstractPersonDetails.FIELD_NAME);
			}
			
			@Override
			public String[] getFieldNames() {
				if(expectedFieldNames==null)
					return super.getFieldNames();
				return expectedFieldNames.toArray(new String[]{});
			}
			
			@Override
			public List<String> getExpectedFieldNames() {
				return Arrays.asList(AbstractPersonDetails.FIELD_CODE
						,AbstractPersonDetails.FIELD_NAME,AbstractPersonDetails.FIELD_LASTNAMES
						,AbstractPersonDetails.FIELD_IMAGE,AbstractPersonDetails.FIELD_BIRTH_DATE,AbstractPersonDetails.FIELD_BIRTH_LOCATION,AbstractPersonDetails.FIELD_TITLE
						,AbstractPersonDetails.FIELD_SEX,AbstractPersonDetails.FIELD_NATIONALITY
						,AbstractPersonDetails.FIELD_LANGUAGE_COLLECTION,AbstractPersonDetails.FIELD_OTHER_DETAILS);
			}
			
			@Override
			public Boolean build(Object data,Field field) {
				if( data instanceof JobDetails ){
					return isFieldNameIn(field,ArrayUtils.addAll(fieldNames
							,AbstractPersonDetails.FIELD_CODE,AbstractPersonDetails.FIELD_IMAGE,AbstractPersonDetails.FIELD_NAME,AbstractPersonDetails.FIELD_LASTNAMES
							,AbstractPersonDetails.FIELD_BIRTH_DATE,AbstractPersonDetails.FIELD_BIRTH_LOCATION,AbstractPersonDetails.FIELD_NATIONALITY,AbstractPersonDetails.FIELD_SEX
							,AbstractPersonDetails.FIELD_LANGUAGE_COLLECTION));
				}
				return (data instanceof AbstractPersonDetails) && isFieldNameIn(field,ArrayUtils.addAll(fieldNames
						,AbstractPersonDetails.FIELD_CODE,AbstractPersonDetails.FIELD_IMAGE,AbstractPersonDetails.FIELD_NAME,AbstractPersonDetails.FIELD_LASTNAMES
						,AbstractPersonDetails.FIELD_BIRTH_DATE,AbstractPersonDetails.FIELD_BIRTH_LOCATION,AbstractPersonDetails.FIELD_NATIONALITY,AbstractPersonDetails.FIELD_TITLE
						,AbstractPersonDetails.FIELD_SEX,AbstractPersonDetails.FIELD_LANGUAGE_COLLECTION,AbstractPersonDetails.FIELD_OTHER_DETAILS))
						||
						(data instanceof LanguageCollectionDetails) && isFieldNameIn(field,ArrayUtils.addAll(fieldNames,LanguageCollectionDetails.FIELD_LANGUAGES));
			}
		}
	}

}
