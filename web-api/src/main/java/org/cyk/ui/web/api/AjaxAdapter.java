package org.cyk.ui.web.api;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public abstract class AjaxAdapter implements AjaxListener, Serializable {

	private static final long serialVersionUID = -4951599198292798814L;

	private String event,update,process,onComplete;
	private Boolean enabled=Boolean.TRUE,queued=Boolean.TRUE,globalStatus=Boolean.TRUE,immediate=Boolean.FALSE;
	
	public AjaxAdapter(String event) {
		super();
		this.event = event;
	}

	
	
}
