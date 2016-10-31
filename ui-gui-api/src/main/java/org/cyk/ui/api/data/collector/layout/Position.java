package org.cyk.ui.api.data.collector.layout;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Position implements Serializable {

	private static final long serialVersionUID = -5527596229934291045L;

	private Dimension row=new Dimension();
	private Dimension column = new Dimension();
	
}
