package org.cyk.ui.api.model.time;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.cyk.system.root.model.time.Period;
import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar.Format;
import org.cyk.utility.common.helper.NumberHelper;
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
	
	@Input @InputNumber private BigDecimal duration;
	
	private SetListener setListener;
	private WriteListener writeListener;
	
	public void set(Period period,SetListener listener){
		if(period==null)
			return;
		fromDate = period.getFromDate();
		toDate = period.getToDate();
		if(period.getNumberOfMillisecond()!=null)
			duration = listener == null ? NumberHelper.getInstance().get(BigDecimal.class, period.getNumberOfMillisecond().get()) : listener.convertFromMillisecond(period.getNumberOfMillisecond().get());
	}
	
	public void set(Period period){
		set(period,setListener);
	}
	
	public void write(Period period,WriteListener listener){
		if(period==null)
			return;
		period.setFromDate(fromDate);
		period.setToDate(toDate);
		if(duration==null)
			period.getNumberOfMillisecond().set(null);
		else
			period.getNumberOfMillisecond().set(listener == null ? duration.longValue() : listener.convertToMillisecond(duration));
	}
	
	public void write(Period period){
		write(period, writeListener);
	}
	
	/**/
	
	public static interface SetListener {
		BigDecimal convertFromMillisecond(Long millisecond);
	}
	
	public static interface WriteListener {
		Long convertToMillisecond(BigDecimal value);
	}
	
	/**/
	
	public static final String FIELD_FROM_DATE = "fromDate";
	public static final String FIELD_TO_DATE = "toDate";
	public static final String FIELD_DURATION = "duration";
	
}
