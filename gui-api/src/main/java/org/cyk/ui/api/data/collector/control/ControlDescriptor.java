package org.cyk.ui.api.data.collector.control;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ControlDescriptor implements Serializable {

	private static final long serialVersionUID = 4778829958574328116L;

	private org.cyk.utility.common.annotation.user.interfaces.Input input;
	private Class<?> fieldType;
	
}
