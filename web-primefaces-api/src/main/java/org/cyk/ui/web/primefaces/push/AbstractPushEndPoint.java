package org.cyk.ui.web.primefaces.push;

import java.io.Serializable;

import lombok.Getter;

import org.cyk.utility.common.cdi.AbstractBean;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.impl.JSONEncoder;

@Getter
public abstract class AbstractPushEndPoint<MESSAGE> extends AbstractBean implements Serializable {
    
	private static final long serialVersionUID = 3545052196460272570L;

	protected final EventBus bus = EventBusFactory.getDefault().eventBus();
	
	//FIXME : Annotation are not found
	/*
	@PathParam(UIManager.PUSH_CHANNEL_VAR) protected String channel;
    @PathParam(UIManager.PUSH_RECEIVER_VAR) protected String receiver;
	*/
	
	@OnOpen
    public void onOpen(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
        //System.out.println("AbstractPushEndPoint.onOpen() : "+remoteEndpoint.path()+" ,  "+getChannel()+"|"+getReceiver());
    }
	
    @OnMessage(encoders = {JSONEncoder.class})
    public MESSAGE onMessage(MESSAGE message) {
    	//System.out.println("AbstractPushEndPoint.onnMessage()"+" ,  "+getChannel()+"|"+getReceiver());
    	return message;
    }
    
    @OnClose
    public void onClose(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
    	//System.out.println("AbstractPushEndPoint.onClose() : "+remoteEndpoint.path()+" ,  "+getChannel()+"|"+getReceiver());
    }
    
    protected abstract String getChannel();
    protected abstract String getReceiver();
    
    
}