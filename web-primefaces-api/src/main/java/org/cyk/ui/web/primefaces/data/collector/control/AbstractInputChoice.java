package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.ObjectConverter;
import org.cyk.ui.web.api.data.collector.control.WebInputChoice;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public abstract class AbstractInputChoice<VALUE_TYPE> extends AbstractInput<VALUE_TYPE> implements WebInputChoice<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputChoice<VALUE_TYPE,DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = -1270441695945429412L;

	protected List<SelectItem> list = new ArrayList<SelectItem>();

	@Override
	public Converter getConverter() {
		return ObjectConverter.getInstance();
	}
	
}
