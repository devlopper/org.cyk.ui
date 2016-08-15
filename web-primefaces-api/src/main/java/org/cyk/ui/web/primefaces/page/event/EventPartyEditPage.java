package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.system.root.model.party.person.Person;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventPartyEditPage extends AbstractCrudOnePage<EventParty> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractFormModel<EventParty> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private Event event;
		@Input @InputChoice @InputOneChoice @InputOneCombo private Person party;
		
		@Override
		public void read() {
			super.read();
			party = (Person) identifiable.getParty();
		}
		
		@Override
		public void write() {
			super.write();
			identifiable.setParty(party);
		}
		
		public static final String FIELD_EVENT = "event";
		public static final String FIELD_PARTY = "party";
	}
	
}
