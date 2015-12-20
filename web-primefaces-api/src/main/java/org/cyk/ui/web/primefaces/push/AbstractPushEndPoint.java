package org.cyk.ui.web.primefaces.push;

import java.io.Serializable;

import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.impl.JSONEncoder;

import lombok.Getter;

@Getter
public abstract class AbstractPushEndPoint<MESSAGE> extends AbstractBean implements Serializable {
    
	private static final long serialVersionUID = 3545052196460272570L;

	protected EventBus bus;// = PrimefacesManager.getInstance().getEventBus();
	
	//FIXME : Annotation are not found
	/*
	@PathParam(UIManager.PUSH_CHANNEL_VAR) protected String channel;
    @PathParam(UIManager.PUSH_RECEIVER_VAR) protected String receiver;
	*/
	
	@OnOpen
    public void onOpen(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
		if(PrimefacesManager.getInstance().getEventBus()==null){
			PrimefacesManager.getInstance().setEventBus(bus = eventBus);
			logInfo("Atmosphere Event Bus has been set to {}",eventBus);
		}
	}
	
    @OnMessage(encoders = {JSONEncoder.class})
    public MESSAGE onMessage(MESSAGE message) {
    	return message;
    }
    
    @OnClose
    public void onClose(RemoteEndpoint remoteEndpoint, EventBus eventBus) {}
    
    protected abstract String getChannel();
    protected abstract String getReceiver();
    
    
}