package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import org.cyk.ui.api.data.collector.control.AbstractControl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AbstractWebControl<MODEL,ROW,LABEL,CONTROL> extends AbstractControl<MODEL, ROW, LABEL, CONTROL, SelectItem> 
	implements WebControl<MODEL,ROW,LABEL,CONTROL>,Serializable {

	private static final long serialVersionUID = 5671513590779656492L;

} 
