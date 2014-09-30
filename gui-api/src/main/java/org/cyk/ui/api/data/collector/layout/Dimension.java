package org.cyk.ui.api.data.collector.layout;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Dimension implements Serializable {

	private static final long serialVersionUID = -7830236378316691899L;

	protected Integer index;
	protected Integer span=1;
	
}
