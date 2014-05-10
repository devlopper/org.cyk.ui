package org.cyk.ui.web.api.form.input;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import lombok.Getter;

import org.cyk.ui.api.editor.input.UIInputComponent;
import org.cyk.ui.web.api.form.WebEditorInputs;

@Getter
public class InputDate extends AbstractWebInputComponent<Date> implements WebUIInputDate,Serializable  {

	private static final long serialVersionUID = 7029658406107605595L;

	private Boolean navigator=Boolean.TRUE,showButtonPanel=Boolean.TRUE;
	private Date minDate,maxDate;
	private String pattern="dd/MM/yyyy",mode="popup",showOn="button";
	private Locale locale;
	
	public InputDate(WebEditorInputs<?, ?, ?, ?> containerForm,UIInputComponent<Date> input) {
		super(containerForm,input);
	}
	
}
