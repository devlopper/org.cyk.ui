package org.cyk.ui.web.primefaces.test.showcase;
import javax.faces.application.FacesMessage;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.impl.JSONEncoder;
 
//@PushEndpoint("/notify")
public class NotifyResource {
         
    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }
 
}