package org.cyk.ui.web.primefaces.test.form;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.cyk.ui.web.primefaces.PrimefacesManager;
import org.cyk.ui.web.primefaces.page.AbstractPrimefacesPage;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@Named @RequestScoped @Getter @Setter
public class PushTestPage extends AbstractPrimefacesPage implements Serializable {
      
	private static final long serialVersionUID = 1659264103742702189L;
	
	private String summary;
    private String detail;
      
    public void send() {
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish("/pushChannelGlobal", new FacesMessage(StringEscapeUtils.escapeHtml4(summary), StringEscapeUtils.escapeHtml4(detail)));
    }
}