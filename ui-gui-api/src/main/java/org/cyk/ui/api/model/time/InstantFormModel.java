package org.cyk.ui.api.model.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.cyk.system.root.model.time.Instant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;
import org.cyk.utility.common.helper.InstanceHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstantFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputNumber @Size(min=-1,max=9999) private Short year=-1;
	@Input @InputNumber	@Size(min=-1,max=12) private Byte monthOfYear=-1;
	@Input @InputNumber	@Size(min=-1,max=31) private Byte dayOfMonth=-1;
	@Input @InputNumber	@Size(min=-1,max=7) private Byte dayOfWeek=-1;
	@Input @InputNumber	@Size(min=-1,max=23) private Byte hourOfDay=-1;
	@Input @InputNumber	@Size(min=-1,max=59) private Byte minuteOfHour=-1;
	@Input @InputNumber	@Size(min=-1,max=59) private Byte secondOfMinute=-1;	
	@Input @InputNumber	@Size(min=-1,max=999) private Short millisecondOfSecond=-1;
	
	private List<org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?>> inputs;
	
	public void set(Instant instant){
		if(instant==null)
			return;
		year = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getYear(),new Short("-1"));
		monthOfYear = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMonthOfYear(),new Byte("-1"));
		dayOfMonth = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getDayOfMonth(),new Byte("-1"));
		dayOfWeek = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getDayOfWeek(),new Byte("-1"));
		hourOfDay = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getHourOfDay(),new Byte("-1"));
		minuteOfHour = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMinuteOfHour(),new Byte("-1"));
		secondOfMinute = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getSecondOfMinute(),new Byte("-1"));
		millisecondOfSecond = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMillisecondOfSecond(),new Short("-1"));
	}
	
	public void write(Instant instant){
		instant.setYear(year);
		instant.setMonthOfYear(monthOfYear);
		instant.setDayOfMonth(dayOfMonth);
		instant.setDayOfWeek(dayOfWeek);
		instant.setHourOfDay(hourOfDay);
		instant.setMinuteOfHour(minuteOfHour);
		instant.setSecondOfMinute(secondOfMinute);
		instant.setMillisecondOfSecond(millisecondOfSecond);
	}
	
	public InstantFormModel addInput(org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?> input){
		if(inputs==null)
			inputs = new ArrayList<>();
		inputs.add(input);
		return this;
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH_OF_YEAR = "monthOfYear";
	public static final String FIELD_DAY_OF_MONTH = "dayOfMonth";
	public static final String FIELD_DAY_OF_WEEK = "dayOfWeek";
	public static final String FIELD_HOUR_OF_DAY = "hourOfDay";
	public static final String FIELD_MINUTE_OF_HOUR = "minuteOfHour";
	public static final String FIELD_SECOND_OF_MINUTE = "secondOfMinute";
	public static final String FIELD_MILLISECOND_OF_SECOND = "millisecondOfSecond";

	/**/
	
	@Getter @Setter
	public static class Unit implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		
	}
}
