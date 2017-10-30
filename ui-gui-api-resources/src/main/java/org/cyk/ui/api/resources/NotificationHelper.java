package org.cyk.ui.api.resources;
import java.io.Serializable;

import org.cyk.utility.common.cdi.AbstractBean;

public class NotificationHelper extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	public static class Viewer extends org.cyk.utility.common.helper.NotificationHelper.Notification.Viewer.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
}
