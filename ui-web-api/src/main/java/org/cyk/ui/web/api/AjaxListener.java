package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	/**/
	
	@Getter @Setter @NoArgsConstructor
	public static class Adapter implements AjaxListener, Serializable {

		private static final long serialVersionUID = -4951599198292798814L;

		protected String event,update,process,onComplete;
		protected Boolean enabled=Boolean.TRUE,queued=Boolean.TRUE,globalStatus=Boolean.TRUE,immediate=Boolean.FALSE;
		
		public Adapter(String event) {
			super();
			this.event = event;
		}
		
		@Override
		public void listen() {}
		
		/**/
		
		@Getter @Setter @NoArgsConstructor
		public static class Default extends Adapter implements Serializable {
			private static final long serialVersionUID = -2343483172217193827L;
			
			public Default(String event) {
				super(event);
			}
		}
		
	}
	
	/**/
	
	String EVENT_CHANGE = "change";

}
