package org.cyk.ui.api.component.input;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DefaultSelectItem implements ISelectItem, Serializable{
	
	private static final long serialVersionUID = -3101571248693084193L;
	private String label;
	private Object value;
}