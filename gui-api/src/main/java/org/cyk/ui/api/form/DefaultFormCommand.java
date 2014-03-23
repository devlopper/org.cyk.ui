package org.cyk.ui.api.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.cyk.ui.api.command.DefaultActionCommand;

public class DefaultFormCommand extends DefaultActionCommand implements UIFormCommand,Serializable {

	private static final long serialVersionUID = 7921874103877192519L;

	@Getter @Setter private ExecutionPhase executionPhase;
	
	@Getter @Setter private ProcessGroup processGroup;
	
}
