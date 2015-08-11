package org.cyk.ui.web.api;

public interface AjaxListener {
	
	void setEvent(String value);
	String getEvent();
	
	void setGlobalStatus(Boolean value);
	Boolean getGlobalStatus();
	
	void setUpdate(String value);
	String getUpdate();
	
	void setProcess(String value);
	String getProcess();
	
	void setOnComplete(String value);
	String getOnComplete();
	
	void setEnabled(Boolean value);
	Boolean getEnabled();
	
	void setQueued(Boolean value);
	Boolean getQueued();
	
	void setImmediate(Boolean value);
	Boolean getImmediate();
	
	void listen();
	
	/**/
	
	interface ListenValueMethod<TYPE>{
		void execute(TYPE value);
	}

}
