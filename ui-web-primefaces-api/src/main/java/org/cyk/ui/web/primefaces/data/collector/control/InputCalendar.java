package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.Date;

import javax.faces.model.SelectItem;

import lombok.Setter;
import lombok.Getter;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public class InputCalendar extends AbstractInput<Date> implements  org.cyk.ui.web.api.data.collector.control.WebInputCalendar<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputCalendar<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	private String pattern;
	private Date minimum,maximum;
	
}