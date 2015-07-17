package org.cyk.ui.api.model.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.cyk.system.root.model.event.Event;
import org.cyk.system.root.model.event.EventParticipation;
import org.cyk.system.root.model.event.EventReminder;
import org.cyk.system.root.model.event.EventType;
import org.cyk.system.root.model.party.Party;
import org.cyk.system.root.model.time.Period;
import org.cyk.ui.api.UIManager;
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
import org.joda.time.DateTimeConstants;

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
	
	//@IncludeInputs @OutputSeperator(label=@Text(value="field.alarm"))
    //private Period alarmPeriod = new Period();
	
	@Input @InputChoice @InputOneChoice @InputOneCombo
	private Minutes reminder;
	
	@Input @InputChoice @InputManyChoice @InputManyPickList
	@Size(min=1)
	private List<Party> parties = new ArrayList<>();
	
	private EventReminder eventReminder;
	
	@Override
	public void read() {
		super.read();
		period = identifiable.getPeriod();
		for(EventParticipation eventParticipation : identifiable.getEventParticipations())
			parties.add(eventParticipation.getParty());
	}
	
	@Override
	public void write() {
		super.write();
		identifiable.setPeriod(period);
		for(Party party : parties)
			identifiable.getEventParticipations().add(new EventParticipation(party));
		if(reminder==null)
			;
		else{
			eventReminder = new EventReminder();
			eventReminder.getPeriod().setFromDate(new Date(identifiable.getPeriod().getFromDate().getTime()-reminder.getMillisecond()));
			eventReminder.getPeriod().setToDate(identifiable.getPeriod().getFromDate());
		}
	}
	
	/**/
	
	@AllArgsConstructor @Getter
	public static enum Minutes {

		_10("minute.count.10",DateTimeConstants.MILLIS_PER_MINUTE*10l),
		_20("minute.count.20",DateTimeConstants.MILLIS_PER_MINUTE*20l),
		_30("minute.count.30",DateTimeConstants.MILLIS_PER_MINUTE*30l),
		_ONE_HOUR("hour.count.one",DateTimeConstants.MILLIS_PER_HOUR*1l),
		_ONE_DAY("day.count.one",DateTimeConstants.MILLIS_PER_DAY*1l),
		_ONE_WEEK("week.count.one",DateTimeConstants.MILLIS_PER_DAY*7l),
		
		;
		
		private String labelId;
		private Long millisecond;
		
		@Override
		public String toString() {
			return UIManager.getInstance().getLanguageBusiness().findText(labelId);
		}
	}
}
