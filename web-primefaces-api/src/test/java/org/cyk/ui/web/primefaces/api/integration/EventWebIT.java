package org.cyk.ui.web.primefaces.api.integration;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.AbstractIdentifiable;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.page.party.PersonEditPage;
import org.cyk.ui.web.primefaces.test.automation.Form;

public class EventWebIT extends AbstractEntityWebIT {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String[] getListMenuItemPath() {
		return new String[]{"commandable_model_entity_event_","commandable_list_event_"};
	}
	
	@Override
	protected Form getForm(Crud crud) {
		switch(crud){
		case CREATE:
			return new Form().addInputText(EventEditPage.Form.FIELD_CODE, getIdentifier(crud))
					.addInputText(EventEditPage.Form.FIELD_NAME, "journée portes ouvertes")
		        	.addInputCalendar(PeriodFormModel.FIELD_FROM_DATE, "Vendredi , 09/09/2016 à 00:00")
		        	.addInputCalendar(PeriodFormModel.FIELD_TO_DATE, "Vendredi , 09/09/2016 à 00:00")
		        	;
		case READ:return null;
		case UPDATE:
			return new Form().addInputText(PersonEditPage.Form.FIELD_CODE, getIdentifier(crud))
					.addInputText(EventEditPage.Form.FIELD_NAME, "Renforcement des capacités")
					.addInputCalendar(PeriodFormModel.FIELD_FROM_DATE, "Vendredi , 09/09/2016 à 00:00")
		        	.addInputCalendar(PeriodFormModel.FIELD_TO_DATE, "Vendredi , 09/09/2016 à 00:00");
		case DELETE:return new Form();
		}
		return null;
	}
	
	@Override
	protected Class<? extends AbstractIdentifiable> getIdentifiableClass() {
		return Event.class;
	}
	
	@Override
	protected String getIdentifier(Crud crud) {
		return "evt025";
	}
	   
}
