package org.cyk.ui.api.model.time;

import java.io.Serializable;

import org.cyk.system.root.model.time.Instant;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstantFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputNumber	private Short year;
	@Input @InputNumber	private Byte month;
	@Input @InputNumber	private Byte day;
	@Input @InputNumber	private Byte dayInWeekIndex;
	@Input @InputNumber	private Byte hour;
	@Input @InputNumber	private Byte minute;
	@Input @InputNumber	private Byte second;	
	@Input @InputNumber	private Short millisecond;
	
	public void set(Instant instant){
		if(instant==null)
			return;
		year = instant.getYear();
		month = instant.getMonth();
		day = instant.getDay();
		dayInWeekIndex = instant.getDayInWeekIndex();
		hour = instant.getHour();
		minute = instant.getMinute();
		second = instant.getSecond();
		millisecond = instant.getMillisecond();
	}
	
	public void write(Instant instant){
		instant.setYear(year);
		instant.setMonth(month);
		instant.setDay(day);
		instant.setDayInWeekIndex(dayInWeekIndex);
		instant.setHour(hour);
		instant.setMinute(minute);
		instant.setSecond(second);
		instant.setMillisecond(millisecond);
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_DAY = "day";
	public static final String FIELD_HOUR = "hour";
	public static final String FIELD_MINUTE = "minute";
	public static final String FIELD_SECOND = "second";
	public static final String FIELD_MILLISECOND = "millisecond";
	public static final String FIELD_DAY_IN_WEEK_INDEX = "dayInWeekIndex";
	
}
