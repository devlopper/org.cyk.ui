package org.cyk.ui.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.Input;
import org.cyk.utility.common.annotation.user.interfaces.InputCalendar;

@Getter @Setter
public class EventSearchFormModel implements Serializable {

	private static final long serialVersionUID = -392868128587378419L;

	@Input @InputCalendar
	private Date fromDate;
	
	@Input @InputCalendar
	private Date toDate;
	
}
