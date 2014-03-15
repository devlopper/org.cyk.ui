package org.cyk.ui.web.api.form.input;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import lombok.Getter;

import org.cyk.ui.api.form.UIFormData;
import org.cyk.ui.api.form.input.UIInputComponent;

@Getter
public class InputDate extends AbstractWebInputComponent<Date> implements WebUIInputDate,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	private Boolean navigator=Boolean.TRUE,showButtonPanel=Boolean.TRUE;
	private Date minDate,maxDate;
	private String pattern="dd/MM/yyyy",mode="popup",showOn="button";
	private Locale locale;
	
	public InputDate(UIFormData<?, ?, ?, ?> containerForm,UIInputComponent<Date> input) {
		super(containerForm,input);
	}
	
}
