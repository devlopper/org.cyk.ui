package org.cyk.ui.api.model.time;

import java.io.Serializable;
import java.util.Date;

import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar.Format;
import org.cyk.utility.common.annotation.user.interfaces.InputNumber;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PeriodFormModel implements Serializable {

	private static final long serialVersionUID = -465747050467060317L;
 
	@Input @InputCalendar(format=Format.DATETIME_LONG)
	private Date fromDate;
	
	@Input @InputCalendar(format=Format.DATETIME_LONG)
	private Date toDate;
	
	@Input @InputNumber private Long duration;
	
	public void set(Period period){
		if(period==null)
			return;
		fromDate = period.getFromDate();
		toDate = period.getToDate();
	}
	
	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
	public static final String FIELD_DURATION = "duration";
	
}
