package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputOneButton<VALUE_TYPE> extends AbstractInputOneChoice<VALUE_TYPE> implements org.cyk.ui.web.api.data.collector.control.WebInputOneButton<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputOneButton<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1490472924426610838L;

}