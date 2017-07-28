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
	@Input @InputNumber	@Size(min=-1,max=12) private Byte month=0;
	@Input @InputNumber	@Size(min=-1,max=31) private Byte day=0;
	@Input @InputNumber	@Size(min=-1,max=7) private Byte dayInWeekIndex=0;
	@Input @InputNumber	@Size(min=-1,max=23) private Byte hour=-1;
	@Input @InputNumber	@Size(min=-1,max=59) private Byte minute=-1;
	@Input @InputNumber	@Size(min=-1,max=59) private Byte second=-1;	
	@Input @InputNumber	@Size(min=-1,max=999) private Short millisecond=-1;
	
	private List<org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?>> inputs;
	
	public void set(Instant instant){
		if(instant==null)
			return;
		year = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getYear(),new Short("-1"));
		month = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMonth(),new Byte("0"));
		day = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getDay(),new Byte("0"));
		dayInWeekIndex = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getDayInWeekIndex(),new Byte("0"));
		hour = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getHour(),new Byte("-1"));
		minute = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMinute(),new Byte("-1"));
		second = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getSecond(),new Byte("-1"));
		millisecond = InstanceHelper.getInstance().getIfNotNullElseDefault(instant.getMillisecond(),new Short("-1"));
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
	
	public InstantFormModel addInput(org.cyk.ui.api.data.collector.control.Input<?, ?, ?, ?, ?, ?> input){
		if(inputs==null)
			inputs = new ArrayList<>();
		inputs.add(input);
		return this;
	}
	
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_DAY = "day";
	public static final String FIELD_HOUR = "hour";
	public static final String FIELD_MINUTE = "minute";
	public static final String FIELD_SECOND = "second";
	public static final String FIELD_MILLISECOND = "millisecond";
	public static final String FIELD_DAY_IN_WEEK_INDEX = "dayInWeekIndex";

	/**/
	
	@Getter @Setter
	public static class Unit implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		
	}
}
