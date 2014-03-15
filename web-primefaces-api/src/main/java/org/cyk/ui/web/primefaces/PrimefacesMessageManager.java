package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cyk.ui.web.api.WebUIMessageManager;
import org.primefaces.context.RequestContext;

@Singleton @Named(value="messageManager") @Alternative
public class PrimefacesMessageManager extends WebUIMessageManager implements Serializable {

	private static final long serialVersionUID = -2135903644205681102L;
	
	@Override
	public void showInDialog(SeverityType severityType,Object title, Object object, Boolean isId) {
		String m = format(object.toString());
		FacesMessage message = new FacesMessage(severity(severityType),title.toString(), m);  
        RequestContext.getCurrentInstance().showMessageInDialog(message);  
	}

}
