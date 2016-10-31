package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.web.api.data.collector.control.WebOutputImage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter
public class OutputImage extends AbstractOutputImage implements org.cyk.ui.api.data.collector.control.OutputImage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>,
WebOutputImage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,Serializable {

	private static final long serialVersionUID = 1390099136018097004L;
	
}
