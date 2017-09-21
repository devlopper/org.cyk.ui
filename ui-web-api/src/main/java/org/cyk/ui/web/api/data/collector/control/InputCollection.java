package org.cyk.ui.web.api.data.collector.control;

import java.io.Serializable;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputCollection<T> extends org.cyk.ui.api.data.collector.control.InputCollection<T,SelectItem> implements Serializable {
	private static final long serialVersionUID = -3543754685060813767L;

	/**/
	
	public InputCollection(Class<T> elementClass) {
		super(elementClass);
	}
	
	/**/
	
	
}
