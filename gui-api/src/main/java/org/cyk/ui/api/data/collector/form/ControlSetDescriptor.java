package org.cyk.ui.api.data.collector.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.annotation.user.interfaces.ControlSet;
import org.cyk.utility.common.annotation.user.interfaces.ControlSetPosition;

@Getter @Setter
public class ControlSetDescriptor implements Serializable {

	private static final long serialVersionUID = -1947949082438310719L;

	private ControlSet set;
	private ControlSetPosition position;
	
}
