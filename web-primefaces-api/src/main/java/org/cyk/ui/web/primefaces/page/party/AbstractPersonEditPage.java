package org.cyk.ui.web.primefaces.page.party;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.impl.party.person.JobDetails;
import org.cyk.system.root.business.impl.party.person.SignatureDetails;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.party.AbstractPersonEditFormModel;

@Getter @Setter
public abstract class AbstractPersonEditPage<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage<PERSON> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;
	
	protected abstract Person getPerson();
	
	@Override
	protected Party getParty() {
		return getPerson();
	}
	
	/**/
	
	public static abstract class AbstractPageAdapter<PERSON extends AbstractIdentifiable> extends AbstractPartyEditPage.AbstractPageAdapter<PERSON> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public AbstractPageAdapter(Class<PERSON> entityTypeClass) {
			super(entityTypeClass);
			FormConfiguration configuration = getFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addFieldNames(AbstractPersonEditFormModel.FIELD_LAST_NAMES,AbstractPersonEditFormModel.FIELD_SEX,AbstractPersonEditFormModel.FIELD_BIRTH_DATE
					,AbstractPersonEditFormModel.FIELD_BIRTH_LOCATION,AbstractPersonEditFormModel.FIELD_NATIONALITY,AbstractPersonEditFormModel.FIELD_TITLE
					);
			
			configuration = getFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addFieldNames(AbstractPersonEditFormModel.FIELD_LAST_NAMES,AbstractPersonEditFormModel.FIELD_SEX,AbstractPersonEditFormModel.FIELD_BIRTH_DATE
					,AbstractPersonEditFormModel.FIELD_BIRTH_LOCATION,AbstractPersonEditFormModel.FIELD_NATIONALITY,AbstractPersonEditFormModel.FIELD_TITLE
					);
			
			configuration = createFormConfiguration(Crud.UPDATE, SignatureDetails.LABEL_IDENTIFIER);
			configuration.addFieldNames(AbstractPersonEditFormModel.FIELD_SIGNATURE_SPECIMEN);
			
			configuration = createFormConfiguration(Crud.UPDATE, JobDetails.LABEL_IDENTIFIER);
			configuration.addFieldNames(AbstractPersonEditFormModel.FIELD_JOB_TITLE);
			
		}
		
		/**/
		
		public static class Default<PERSON extends Person> extends AbstractPageAdapter<PERSON> implements Serializable {

			private static final long serialVersionUID = 4370361826462886031L;

			public Default(Class<PERSON> entityTypeClass) {
				super(entityTypeClass);	
			}
			
		}
		
	}
	
}
