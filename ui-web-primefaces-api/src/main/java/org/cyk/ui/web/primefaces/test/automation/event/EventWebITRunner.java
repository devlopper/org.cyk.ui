package org.cyk.ui.web.primefaces.test.automation.event;

import java.io.Serializable;

import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.model.event.Event;
import org.cyk.ui.api.model.time.PeriodFormModel;
import org.cyk.ui.web.primefaces.page.event.EventEditPage;
import org.cyk.ui.web.primefaces.test.automation.Form;
import org.cyk.ui.web.primefaces.test.automation.IdentifiableWebITRunner;

public class EventWebITRunner extends IdentifiableWebITRunner<Event> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String[] getListMenuItemPath() {
		return new String[]{"commandable_model_entity_event_","commandable_list_event_"};
	}
	@Override
	public String getCode(Crud crud) {
		switch(crud){
		case CREATE:return "evt025";
		case READ:return "evt025";
		case UPDATE:return "evt025";
		case DELETE:return "evt025";
		}
		return null;
	}
	@Override
	public void fillForm(Form form,Crud crud) {
		switch(crud){
		case CREATE:
			form.addInputText(EventEditPage.Form.FIELD_NAME, "journée portes ouvertes")
		        .addInputCalendar(PeriodFormModel.FIELD_FROM_DATE, "Vendredi , 09/09/2016 à 00:00")
		        .addInputCalendar(PeriodFormModel.FIELD_TO_DATE, "Vendredi , 09/09/2016 à 00:00")
		        ;
			break;
		case READ:
			break;
		case UPDATE:
			form.addInputText(EventEditPage.Form.FIELD_NAME, "Renforcement des capacités")
				.addInputCalendar(PeriodFormModel.FIELD_FROM_DATE, "Vendredi , 09/09/2016 à 00:00")
		        .addInputCalendar(PeriodFormModel.FIELD_TO_DATE, "Vendredi , 09/09/2016 à 00:00")
		        ;
			break;
		case DELETE:
			break;
		}
	}

}
