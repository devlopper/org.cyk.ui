package org.cyk.ui.api.form;

import org.cyk.ui.api.command.UIActionCommand;

/**
 * Form submit button
 * @author Komenan Y .Christian
 *
 */
public interface UIFormCommand extends UIActionCommand {

	enum ExecutionPhase{BEFORE_DATA_SENT,AFTER_DATA_SENT}

	enum ProcessGroup{THIS,FORM,PARTIAL}
	
	ExecutionPhase getExecutionPhase();
	
	void setExecutionPhase(ExecutionPhase anExecutionPhase);
	
	ProcessGroup getProcessGroup();
	
	void setProcessGroup(ProcessGroup aProcessGroup);
	
}
