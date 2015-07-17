package org.cyk.ui.web.primefaces.page.tools;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.business.api.BusinessEntityInfos;
import org.cyk.system.root.business.api.Crud;
import org.cyk.system.root.business.api.event.EventBusiness;
import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.control.InputChoice;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.api.model.event.EventFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;

@Named @ViewScoped @Getter @Setter
public class EventCrudOnePage extends AbstractCrudOnePage<Event> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	@Inject private EventBusiness eventBusiness;
	
	@Override
	protected void initialisation() { 
		super.initialisation();
		//contextualMenu = MenuManager.getInstance().calendarMenu(userSession);
		if(Crud.CREATE.equals(crud))
			identifiable.setOwner(userSession.getUser());
	}
	
	@Override
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
	}
	
	@Override
	protected void create() {
		EventFormModel model = (EventFormModel) form.getData();
		if(model.getEventReminder()==null)
			eventBusiness.create(identifiable);
		else
			eventBusiness.create(identifiable,Arrays.asList(model.getEventReminder()));
	}
	
	@Override
	protected Class<? extends AbstractFormModel<?>> __formModelClass__() {
		return EventFormModel.class;
	}
	
	@Override
	protected BusinessEntityInfos fetchBusinessEntityInfos() {
		return uiManager.businessEntityInfos(Event.class);
	}
}
