package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.SelectItem;

import org.cyk.ui.web.api.data.collector.control.WebInputManyChoice;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;


public abstract class AbstractInputManyChoice<VALUE_TYPE> extends AbstractInputChoice<List<VALUE_TYPE>> implements WebInputManyChoice<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputManyChoice<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = -1270441695945429412L;

	
}
