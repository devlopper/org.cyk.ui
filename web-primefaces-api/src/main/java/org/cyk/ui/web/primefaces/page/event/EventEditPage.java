package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.geography.ContactCollection;
import org.cyk.ui.api.UIManager;
import org.cyk.ui.api.data.collector.form.FormConfiguration;
import org.cyk.ui.api.model.AbstractBusinessIdentifiedEditFormModel;
import org.cyk.ui.api.model.geography.ContactCollectionFormModel;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.page.AbstractBusinessEntityFormOnePage;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventEditPage extends AbstractCrudOnePage<Event> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	/*@Override
	protected void afterInitialisation() {
		super.afterInitialisation();
		List<?> parties = form.findInputByClassByFieldName(InputChoice.class, "parties").getList();
		for(int i = 0;i<parties.size();){
			Party party = (Party) parties.get(i);
			if(party instanceof Person){
				//Person person = (Person) party;
				i++;
			}else
				parties.remove(i);
		}
	}*/
	
	/*@Override
	protected void create() {
		EventFormModel model = (EventFormModel) form.getData();
		if(model.getEventReminder()==null)
			eventBusiness.create(identifiable);
		else
			eventBusiness.create(identifiable,Arrays.asList(model.getEventReminder()));
	}*/
	
	public static class Form extends AbstractBusinessIdentifiedEditFormModel<Event> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL) PeriodFormModel period = new PeriodFormModel();
		@IncludeInputs(layout=IncludeInputs.Layout.VERTICAL) ContactCollectionFormModel contactCollection = new ContactCollectionFormModel();
		
		@Override
		public void read() {
			super.read();
			period.setFromDate(identifiable.getGlobalIdentifierCreateIfNull().getExistencePeriod().getFromDate());
			period.setToDate(identifiable.getExistencePeriod().getToDate());
			contactCollection.setIdentifiable(identifiable.getContactCollection());
			contactCollection.read();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.getExistencePeriod().setFromDate(period.getFromDate());
			identifiable.getExistencePeriod().setToDate(period.getToDate());
			contactCollection.write();
		}
		
		public static final String FIELD_PERIOD = "period";
		public static final String FIELD_CONTACT_COLLECTION = "contactCollection";
	}
	
	/*public static class Adapter extends AbstractBusinessEntityFormOnePage.BusinessEntityFormOnePageListener.Adapter.Default<Event> implements Serializable {

		private static final long serialVersionUID = 4370361826462886031L;

		public Adapter() {
			super(Event.class);
			FormConfiguration configuration = createFormConfiguration(Crud.CREATE, FormConfiguration.TYPE_INPUT_SET_SMALLEST);
			configuration.addRequiredFieldNames(AbstractBusinessIdentifiedEditFormModel.FIELD_NAME,PeriodFormModel.FIELD_FROM_DATE,PeriodFormModel.FIELD_TO_DATE);
			configuration.addFieldNames(Form.FIELD_PERIOD);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(entityTypeClass).getUserInterface().getLabelId());
			configuration.addRequiredFieldNames(AbstractBusinessIdentifiedEditFormModel.FIELD_NAME,PeriodFormModel.FIELD_FROM_DATE,PeriodFormModel.FIELD_TO_DATE);
			configuration.addFieldNames(Form.FIELD_PERIOD);
			
			configuration = createFormConfiguration(Crud.UPDATE, UIManager.getInstance().businessEntityInfos(ContactCollection.class).getUserInterface().getLabelId());
			configuration.addFieldNames(Form.FIELD_CONTACT_COLLECTION,ContactCollectionFormModel.FIELD_MOBILE_PHONE_NUMBER
					,ContactCollectionFormModel.FIELD_LAND_PHONE_NUMBER,ContactCollectionFormModel.FIELD_ELECTRONICMAIL,ContactCollectionFormModel.FIELD_POSTALBOX
					,ContactCollectionFormModel.FIELD_HOME_LOCATION);
			
			configuration = createFormConfiguration(Crud.DELETE);
			configuration.addFieldNames(AbstractBusinessIdentifiedEditFormModel.FIELD_NAME,Form.FIELD_PERIOD,PeriodFormModel.FIELD_FROM_DATE
					,PeriodFormModel.FIELD_TO_DATE);
			
		}
		
	}*/
	
}
