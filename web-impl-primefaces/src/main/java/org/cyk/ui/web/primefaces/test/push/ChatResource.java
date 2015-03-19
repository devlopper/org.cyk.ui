package org.cyk.ui.web.primefaces.test.push;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.primefaces.push.AbstractPushEndPoint;
import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
 
//@PushEndpoint("/{"+UIManager.PUSH_CHANNEL_VAR+"}/{"+UIManager.PUSH_RECEIVER_VAR+"}") @Singleton
public class ChatResource extends AbstractPushEndPoint<Message> {
 
	private static final long serialVersionUID = 2396790392001601309L;
    

	@Getter @PathParam(UIManager.PUSH_CHANNEL_VAR) protected String channel;
	@Getter @PathParam(UIManager.PUSH_RECEIVER_VAR) protected String receiver;
    
    @Inject private ServletContext ctx;
 
    @OnOpen
    public void onOpen(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
        super.onOpen(remoteEndpoint, eventBus);
        bus.publish(getChannel() + "/*", new Message(String.format("%s has entered the room '%s'",  getChannel(), getReceiver()), true));
    }

    @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
    public Message onMessage(Message message) {
    	System.out.println("ChatResource.onMessage() , "+getChannel()+"|"+getReceiver());
        return super.onMessage(message);
    }    
    
    @OnClose
    public void onClose(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
    	super.onClose(remoteEndpoint, eventBus);
        //System.out.println("ChatResource.onClose() : "+remoteEndpoint.path()+" --- "+channel+"|"+receiver);
    	ChatUsers users= (ChatUsers) ctx.getAttribute("chatUsers");
        users.remove(getReceiver());
         
        bus.publish(getChannel() + "/*", new Message(String.format("%s has left the room", getReceiver()), true));
    }
}