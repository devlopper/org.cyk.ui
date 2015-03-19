package org.cyk.ui.web.primefaces.test.push;
import lombok.Getter;

import org.cyk.ui.api.UIManager;
import org.cyk.ui.web.primefaces.push.AbstractNotificationPushEndPoint;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
 
//@PushEndpoint("/{"+UIManager.PUSH_CHANNEL_VAR+"}/{"+UIManager.PUSH_RECEIVER_VAR+"}") @Singleton
public class ChatResource2  {
 
	private static final long serialVersionUID = 2396790392001601309L;
    

	@Getter @PathParam(UIManager.PUSH_CHANNEL_VAR) protected String channel;
	@Getter @PathParam(UIManager.PUSH_RECEIVER_VAR) protected String receiver;
    
    //@Inject private ServletContext ctx;
 
    /*
    @OnOpen
    public void onOpen(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
        super.onOpen(remoteEndpoint, eventBus);
        bus.publish(getChannel() + "/*", new Message(String.format("%s has entered the room '%s'",  getChannel(), getReceiver()), true));
    }
    */

    /*
    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
    	System.out.println("ChatResource.onMessage() , "+getChannel()+"|"+getReceiver());
        return super.onMessage(message);
    }    
    */
    
    /*
    @OnClose
    public void onClose(RemoteEndpoint remoteEndpoint, EventBus eventBus) {
    	super.onClose(remoteEndpoint, eventBus);
        //System.out.println("ChatResource.onClose() : "+remoteEndpoint.path()+" --- "+channel+"|"+receiver);
    	ChatUsers users= (ChatUsers) ctx.getAttribute("chatUsers");
        users.remove(getReceiver());
         
        bus.publish(getChannel() + "/*", new Message(String.format("%s has left the room", getReceiver()), true));
    }
    */
}