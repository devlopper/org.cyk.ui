package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

//TODO how to show path (different groups ) when selection is set to a custom value taken from any of the group
// On create all are fine but on update the UI component does not render back as it was on create
@Getter @Setter
public class InputOneCascadeList<VALUE_TYPE> extends AbstractInputOneChoice<VALUE_TYPE> implements org.cyk.ui.web.api.data.collector.control.WebInputOneCascadeList<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputOneCascadeList<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1490472924426610838L;

	private String header;
	private Boolean showHeaders=Boolean.TRUE;
	
}
