package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.api.data.collector.form.AbstractFormModel;
import org.cyk.utility.common.annotation.user.interfaces.IncludeInputs;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputManyPickList;
import org.cyk.utility.common.annotation.user.interfaces.InputOneChoice;
import org.cyk.utility.common.annotation.user.interfaces.InputOneCombo;
import org.cyk.utility.common.annotation.user.interfaces.InputText;
import org.cyk.utility.common.annotation.user.interfaces.InputTextarea;
import org.cyk.utility.common.annotation.user.interfaces.OutputSeperator;
import org.cyk.utility.common.annotation.user.interfaces.Text;

@Getter @Setter
public class EventFormModel extends AbstractFormModel<Event> implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input @InputChoice @InputOneChoice @InputOneCombo @NotNull
	private EventType type;
	
	@Input @InputText
	private String object;
	
	@Input @InputTextarea
	private String comments;
	
	@IncludeInputs @OutputSeperator
    private Period period = new Period();
	
	@IncludeInputs @OutputSeperator(label=@Text(value="field.alarm"))
    private Period alarmPeriod = new Period();
	
	@Input @InputChoice @InputManyChoice @InputManyPickList
	@Size(min=1)
	private List<Party> parties = new ArrayList<>();
	
	@Override
	public void read() {
		super.read();
		period = identifiable.getPeriod();
		alarmPeriod = identifiable.getAlarm().getPeriod();
		for(EventParticipation eventParticipation : identifiable.getEventParticipations())
			parties.add(eventParticipation.getParty());
	}
	
	@Override
	public void write() {
		super.write();
		identifiable.setPeriod(period);
		identifiable.getAlarm().setPeriod(alarmPeriod);
		for(Party party : parties)
			identifiable.getEventParticipations().add(new EventParticipation(party, identifiable));
	}
}
