package org.cyk.ui.web.primefaces.page.event;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.system.root.model.event.EventMissed;
import org.cyk.system.root.model.event.EventMissedReason;
import org.cyk.system.root.model.event.EventParty;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.ui.web.primefaces.page.crud.AbstractCrudOnePage;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class EventMissedEditPage extends AbstractCrudOnePage<EventMissed> implements Serializable {

	private static final long serialVersionUID = 3274187086682750183L;

	public static class Form extends AbstractFormModel<EventMissed> implements Serializable{
		private static final long serialVersionUID = -4741435164709063863L;
		
		@Input @InputChoice @InputOneChoice @InputOneCombo private EventParty eventParty;
		@Input @InputChoice @InputOneChoice @InputOneCombo private EventMissedReason reason;
		@Input @InputNumber private Long numberOfMillisecond;
		
		public static final String FIELD_EVENT_PARTY = "eventParty";
		public static final String FIELD_MISSED_EVENT_REASON = "reason";
		public static final String FIELD_NUMBER_OF_MILLISECOND = "numberOfMillisecond";
	}
	
}
