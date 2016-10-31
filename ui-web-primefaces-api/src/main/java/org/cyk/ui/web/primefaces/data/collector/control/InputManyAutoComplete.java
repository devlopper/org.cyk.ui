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
public class InputManyAutoComplete<VALUE_TYPE> extends AbstractInputManyChoice<VALUE_TYPE> implements org.cyk.ui.web.api.data.collector.control.WebInputManyAutoComplete<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputManyAutoComplete<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem,InputAutoCompleteCommon<VALUE_TYPE>>, Serializable  {

	private static final long serialVersionUID = 1490472924426610838L;

	private InputAutoCompleteCommon<VALUE_TYPE> common = new InputAutoCompleteCommon<>();
	
}
