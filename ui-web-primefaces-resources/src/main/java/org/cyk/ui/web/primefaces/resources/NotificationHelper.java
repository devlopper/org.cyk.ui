package org.cyk.ui.web.primefaces.resources;
import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.NotificationHelper.Notification;
import org.primefaces.context.RequestContext;

public class NotificationHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Viewer extends org.cyk.ui.web.api.resources.NotificationHelper.Viewer implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected void __execute__(Notification notification, Type type) {
			if(Type.DIALOG.equals(type))
				RequestContext.getCurrentInstance().showMessageInDialog(createMessage(notification));
			else
				super.__execute__(notification, type);
		}
		
	}
	
}