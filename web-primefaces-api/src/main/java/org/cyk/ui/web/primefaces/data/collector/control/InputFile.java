package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;
import org.primefaces.model.UploadedFile;

@Getter @Setter
public class InputFile extends AbstractInput<Byte[]> implements  org.cyk.ui.web.api.data.collector.control.WebInputFile<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>, 
org.cyk.ui.api.data.collector.control.InputFile<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl,SelectItem>, Serializable {

	private static final long serialVersionUID = 1390099136018097004L;

	private UploadedFile uploadedFile;
	
}
