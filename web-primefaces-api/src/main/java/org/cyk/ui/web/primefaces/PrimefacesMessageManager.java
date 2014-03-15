package org.cyk.ui.web.primefaces;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import org.cyk.ui.web.api.WebUIMessageManager;
import org.primefaces.context.RequestContext;

@Singleton @Alternative
public class PrimefacesMessageManager extends WebUIMessageManager implements Serializable {

	private static final long serialVersionUID = -2135903644205681102L;
	
	@Override
	public void showDialog() {
        RequestContext.getCurrentInstance().showMessageInDialog(facesMessage);  
	}

}
