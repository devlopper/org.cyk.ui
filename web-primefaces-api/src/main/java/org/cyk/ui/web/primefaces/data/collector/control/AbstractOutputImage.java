package org.cyk.ui.web.primefaces.data.collector.control;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.cyk.system.root.model.file.File;
import org.cyk.ui.web.api.data.collector.control.WebOutputImage;
import org.primefaces.extensions.model.dynaform.DynaFormControl;
import org.primefaces.extensions.model.dynaform.DynaFormLabel;
import org.primefaces.extensions.model.dynaform.DynaFormModel;
import org.primefaces.extensions.model.dynaform.DynaFormRow;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractOutputImage extends AbstractOutput implements WebOutputImage<DynaFormModel,DynaFormRow,DynaFormLabel,DynaFormControl>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

	protected File value;
	protected Integer previewWidth=100,previewHeight=100;
	private Boolean readOnly;//TODO this is a mic mac to solve an issue
	
	public AbstractOutputImage(File value) {
		super();
		this.value = value;
	} 
	
	
}
