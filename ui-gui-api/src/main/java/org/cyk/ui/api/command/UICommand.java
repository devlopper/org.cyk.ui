package org.cyk.ui.api.command;

import java.util.Collection;

import org.cyk.ui.api.UIMessageManager;
import org.cyk.utility.common.computation.ExecutionProgress;

/**
 * An element on which user can interact to request something
 * @author Komenan Y .Christian
 *
 */
public interface UICommand {
	
	Collection<CommandListener> getCommandListeners();
	
	Boolean getConfirm();
	void setConfirm(Boolean value);
	
	/**
	 * The code to be executed when validation has succeed
	 */
	Object execute(Object object);
	
	Object execute();
		
	UIMessageManager getMessageManager();
	void setMessageManager(UIMessageManager messageManager);
	
	void setExecutionProgress(ExecutionProgress executionProgress);
	ExecutionProgress getExecutionProgress();
	
	Boolean getShowExecutionProgress();
	void setShowExecutionProgress(Boolean value);
	
}
